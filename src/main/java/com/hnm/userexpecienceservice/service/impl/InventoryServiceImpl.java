package com.hnm.userexpecienceservice.service.impl;

import com.hnm.userexpecienceservice.model.ItemVariation;
import com.hnm.userexpecienceservice.repository.ItemVariationRepository;
import com.hnm.userexpecienceservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ItemVariationRepository itemVariationRepository;


    @Override
    public List<ItemVariation> getFilteredInventory(String eventType,BigDecimal budget, String stylePreferences, String colorPreferences, String sizePreferences) {
        log.info("Get filtered inventory");
        return itemVariationRepository.findFilteredItems(eventType, budget, stylePreferences, colorPreferences, sizePreferences);
    }
}
