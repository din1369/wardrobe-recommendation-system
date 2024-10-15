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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RecommendationEngineServiceImplTest {
    @Mock
    InventoryService inventoryService;
    @Mock
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Mock
    EventRepository eventRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RecommendationRepository recommendationRepository;
    @InjectMocks
    RecommendationEngineServiceImpl recommendationEngineServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetRecommendations_UserNotFound() {

        Long userID = 1L;
        RecommendationRequestDTO request = new RecommendationRequestDTO();

        when(userRepository.findById(userID)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> recommendationEngineServiceImpl.getRecommendations(request, userID));
    }

    @Test
    void testGetRecommendations_Success() {

        Long userID = 1L;
        RecommendationRequestDTO request = new RecommendationRequestDTO();
        request.setEventType("wedding");
        request.setBudget(new BigDecimal(500));
        request.setStylePreferences("casual");
        request.setColorPreferences("blue");
        request.setSizePreferences("M");

        User user = new User();
        user.setUserId(userID);

        Event event = new Event();
        event.setUser(user);

        when(userRepository.findById(userID)).thenReturn(java.util.Optional.of(user));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        List<PurchaseHistory> mockHistory = Collections.emptyList();
        when(purchaseHistoryRepository.findRecentPurchasesByUser(userID, 5)).thenReturn(mockHistory);

        List<ItemVariation> mockItems = Collections.emptyList();
        when(inventoryService.getFilteredInventory("wedding", new BigDecimal(500), "casual", "blue", "M"))
                .thenReturn(mockItems);

        RecommendationResponseDTO response = recommendationEngineServiceImpl.getRecommendations(request, userID);

        assertEquals(0, response.getRecommendedItemDTOS().size());
        verify(userRepository, times(1)).findById(userID);
        verify(inventoryService, times(1)).getFilteredInventory("wedding", new BigDecimal(500), "casual", "blue", "M");
    }

    @Test
    void testSaveRecommendation_CallsRepository() {

        Long userID = 1L;
        RecommendationRequestDTO request = new RecommendationRequestDTO();
        request.setEventType("wedding");
        request.setBudget(new BigDecimal(500));
        request.setStylePreferences("casual");
        request.setColorPreferences("blue");
        request.setSizePreferences("M");

        User user = new User();
        user.setUserId(userID);

        Event event = new Event();
        event.setUser(user);

        RecommendationResponseDTO recommendations = new RecommendationResponseDTO();
        RecommendedItemDTO recommendedItem = new RecommendedItemDTO();
        recommendedItem.setItemId(1001L);
        recommendedItem.setItemName("Item 1");
        recommendations.setRecommendedItemDTOS(List.of(recommendedItem));
        recommendations.setTotalPrice(new BigDecimal(100));

        when(userRepository.findById(userID)).thenReturn(java.util.Optional.of(user));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        recommendationEngineServiceImpl.saveRecommendation(recommendations, event, recommendations.getTotalPrice());

        ArgumentCaptor<Recommendation> recommendationCaptor = ArgumentCaptor.forClass(Recommendation.class);
        verify(recommendationRepository, times(1)).save(recommendationCaptor.capture());

        Recommendation savedRecommendation = recommendationCaptor.getValue();
        assertEquals(event, savedRecommendation.getEvent());
        assertEquals(recommendations.getTotalPrice(), savedRecommendation.getBudget());
        assertEquals("1001", savedRecommendation.getRecommendedItems());
    }


}