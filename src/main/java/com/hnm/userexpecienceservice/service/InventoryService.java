package com.hnm.userexpecienceservice.service;

import com.hnm.userexpecienceservice.model.ItemVariation;

import java.math.BigDecimal;
import java.util.List;

public interface InventoryService {

    List<ItemVariation> getFilteredInventory(String eventType,
                                             BigDecimal budget,
                                             String stylePreferences,
                                             String colorPreferences,
                                             String sizePreferences);
}
