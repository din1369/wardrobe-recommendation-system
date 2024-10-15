package com.hnm.userexpecienceservice.service.impl;

import com.hnm.userexpecienceservice.model.ItemVariation;
import com.hnm.userexpecienceservice.repository.ItemVariationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InventoryServiceImplTest {

    @Mock
    ItemVariationRepository itemVariationRepository;
    @InjectMocks
    InventoryServiceImpl inventoryServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilteredInventory() {
        List<ItemVariation> mockItems = Arrays.asList(
                new ItemVariation(),
                new ItemVariation()
        );

        when(itemVariationRepository.findFilteredItems("wedding", new BigDecimal(500)
                , "casual", "blue", "M"))
                .thenReturn(mockItems);
        List<ItemVariation> filteredItems = inventoryServiceImpl.getFilteredInventory("wedding",
                new BigDecimal(500), "casual", "blue", "M");
        assertEquals(2, filteredItems.size());
    }
}