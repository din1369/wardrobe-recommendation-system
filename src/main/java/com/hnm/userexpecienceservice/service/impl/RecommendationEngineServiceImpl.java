package com.hnm.userexpecienceservice.service.impl;

import com.hnm.userexpecienceservice.dto.request.RecommendationRequestDTO;
import com.hnm.userexpecienceservice.dto.response.RecommendationResponseDTO;
import com.hnm.userexpecienceservice.dto.response.RecommendedItemDTO;
import com.hnm.userexpecienceservice.exception.UserNotFoundException;
import com.hnm.userexpecienceservice.model.Event;
import com.hnm.userexpecienceservice.model.ItemVariation;
import com.hnm.userexpecienceservice.model.PurchaseHistory;
import com.hnm.userexpecienceservice.model.Recommendation;
import com.hnm.userexpecienceservice.model.User;
import com.hnm.userexpecienceservice.repository.EventRepository;
import com.hnm.userexpecienceservice.repository.PurchaseHistoryRepository;
import com.hnm.userexpecienceservice.repository.RecommendationRepository;
import com.hnm.userexpecienceservice.repository.UserRepository;
import com.hnm.userexpecienceservice.service.InventoryService;
import com.hnm.userexpecienceservice.service.RecommendationEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Value("${app.purchase-history.limit}")
    private int historyLimit;

    @Value("${app.purchase-history.category-limit}")
    private int categoryLimit;

    @Value("${app.max-recommend-count}")
    private int maxItemCount;

    @Override
    public RecommendationResponseDTO getRecommendations(RecommendationRequestDTO request, Long userID) {
        log.info("Start recommendation engine");
        User user = userRepository.findById(userID).orElseThrow(
                () -> new UserNotFoundException(userID));
        Event event = saveEvent(request,user);
        // Get user purchase history
        List<PurchaseHistory> userHistory = purchaseHistoryRepository.findRecentPurchasesByUser(userID, historyLimit);
        // Get top 5 categories from purchase history
        List<String> topCategories = getTopCategoriesFromHistory(userHistory, categoryLimit);
        // Filter available inventory based on the event type, budget, and user preferences
        List<ItemVariation> filteredItems = inventoryService.getFilteredInventory(
                request.getEventType(),
                request.getBudget(),
                request.getStylePreferences(),
                request.getColorPreferences(),
                request.getSizePreferences()
        );
        // Exclude items that have already been purchased by the user
        filteredItems = excludePreviouslyPurchasedItems(filteredItems, userHistory);
        // Generate outfit recommendations based on top categories
        return generateRecommendations(filteredItems, topCategories, request, event);
    }

    // Helper method to get the top N categories from the user's purchase history
    private List<String> getTopCategoriesFromHistory(List<PurchaseHistory> userHistory, int limit) {
        log.info("get top categories from history");
        return userHistory.stream()
                .collect(Collectors.groupingBy(history -> history.getVariation().getItem().getCategory().name(), Collectors.counting()))
                .entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Sort by frequency in descending order
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<ItemVariation> excludePreviouslyPurchasedItems(List<ItemVariation> items, List<PurchaseHistory> purchaseHistory) {
        Set<Long> purchasedItemIds = purchaseHistory.stream()
                .map(history -> history.getVariation().getItem().getItemId())
                .collect(Collectors.toSet());

        return items.stream()
                .filter(item -> !purchasedItemIds.contains(item.getItem().getItemId()))
                .toList();
    }

    private RecommendationResponseDTO generateRecommendations(List<ItemVariation> filteredItems,
                                                              List<String> topCategories, RecommendationRequestDTO request, Event event) {
        log.info("start to generate recommendations");
        RecommendationResponseDTO recommendations = new RecommendationResponseDTO();
        List<RecommendedItemDTO> recommendedItemDTOList = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        Map<String, List<ItemVariation>> categorizedItems = categorizeItems(filteredItems);

        // Loop through categories and select items until constraints are met
        while (shouldContinueAddingItems(recommendedItemDTOList, totalPrice, request)) {
            List<ItemVariation> selectedItems = selectItemsFromTopCategories(categorizedItems, topCategories);

            if (selectedItems.isEmpty()) break;

            totalPrice = addItemsToRecommendationList(selectedItems, recommendedItemDTOList, totalPrice, request);

            if (recommendedItemDTOList.size() >= maxItemCount || totalPrice.compareTo(request.getBudget()) > 0) {
                break;
            }
        }

        recommendations.setRecommendedItemDTOS(recommendedItemDTOList);
        recommendations.setTotalPrice(totalPrice);
        saveRecommendation(recommendations, event, totalPrice);
        return recommendations;
    }

    private Map<String, List<ItemVariation>> categorizeItems(List<ItemVariation> filteredItems) {
        return filteredItems.stream()
                .collect(Collectors.groupingBy(item -> item.getItem().getCategory().name()));
    }

    private boolean shouldContinueAddingItems(List<RecommendedItemDTO> recommendedItemDTOList, BigDecimal totalPrice, RecommendationRequestDTO request) {
        return recommendedItemDTOList.size() < maxItemCount && totalPrice.compareTo(request.getBudget()) <= 0;
    }

    private List<ItemVariation> selectItemsFromTopCategories(Map<String, List<ItemVariation>> categorizedItems, List<String> topCategories) {
        List<ItemVariation> selectedItems = new ArrayList<>();
        for (String category : topCategories) {
            ItemVariation selectedItem = getRandomItem(categorizedItems.get(category));
            if (selectedItem != null) {
                categorizedItems.get(category).remove(selectedItem);
                selectedItems.add(selectedItem);
            }
        }
        return selectedItems;
    }

    private BigDecimal addItemsToRecommendationList(List<ItemVariation> selectedItems,
                                                    List<RecommendedItemDTO> recommendedItemDTOList,
                                                    BigDecimal totalPrice, RecommendationRequestDTO request) {
        for (ItemVariation item : selectedItems) {
            BigDecimal itemPrice = item.getItem().getBasePrice().add(item.getAdditionalPrice());

            if (totalPrice.add(itemPrice).compareTo(request.getBudget()) <= 0) {
                RecommendedItemDTO recommendedItemDTO = convertToRecommendedItemDTO(item);
                recommendedItemDTOList.add(recommendedItemDTO);
                totalPrice = totalPrice.add(itemPrice);
            } else {
                break;  // Stop adding items if the budget is exceeded
            }

            if (recommendedItemDTOList.size() >= maxItemCount) {
                break;
            }
        }
        return totalPrice;
    }

    private ItemVariation getRandomItem(List<ItemVariation> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        return items.get(new Random().nextInt(items.size()));
    }

    private RecommendedItemDTO convertToRecommendedItemDTO(ItemVariation item) {
        RecommendedItemDTO dto = new RecommendedItemDTO();
        dto.setItemId(item.getItem().getItemId());
        dto.setItemName(item.getItem().getItemName());
        dto.setCategory(item.getItem().getCategory().name());
        dto.setPrice(item.getItem().getBasePrice().add(item.getAdditionalPrice()));
        dto.setSize(item.getSize());
        dto.setColor(item.getColor());
        return dto;
    }

    private Event saveEvent(RecommendationRequestDTO recommendationRequestDTO,
                           User user) {
        log.info("Save event, userId: {}", user.getUserId() );
        Event event = new Event();
        event.setUser(user);
        event.setEventType(recommendationRequestDTO.getEventType());
        event.setColorPreferences(recommendationRequestDTO.getColorPreferences());
        event.setSizePreferences(recommendationRequestDTO.getSizePreferences());
        event.setBudget(recommendationRequestDTO.getBudget());
        return eventRepository.save(event);
    }

    public void saveRecommendation(RecommendationResponseDTO recommendationResponse, Event event, BigDecimal budget) {
        Recommendation recommendation = new Recommendation();

        // Set event reference
        recommendation.setEvent(event);

        // Serialize the recommended items into a string (could be JSON or CSV format)
        String recommendedItems = serializeRecommendedItems(recommendationResponse.getRecommendedItemDTOS());
        recommendation.setRecommendedItems(recommendedItems);

        // Set budget and save
        recommendation.setBudget(budget);
        recommendationRepository.save(recommendation);
    }

    private String serializeRecommendedItems(List<RecommendedItemDTO> recommendedItemDTOList) {
        // Convert list of recommended items to a comma-separated string of SKUs, or use a JSON format
        return recommendedItemDTOList.stream()
                .map(RecommendedItemDTO::getItemId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

}
