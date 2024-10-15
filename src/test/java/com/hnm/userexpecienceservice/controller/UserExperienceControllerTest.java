package com.hnm.userexpecienceservice.controller;

import com.hnm.userexpecienceservice.dto.request.RecommendationRequestDTO;
import com.hnm.userexpecienceservice.dto.response.RecommendationResponseDTO;
import com.hnm.userexpecienceservice.dto.response.RecommendedItemDTO;
import com.hnm.userexpecienceservice.service.RecommendationEngineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserExperienceControllerTest {
    @Mock
    RecommendationEngineService recommendationEngineService;

    @InjectMocks
    UserExperienceController userExperienceController;

    private RecommendationRequestDTO recommendationRequestDTO;

    private RecommendationResponseDTO recommendationResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recommendationRequestDTO =RecommendationRequestDTO
                .builder()
                .budget(BigDecimal.valueOf(1000))
                .eventType("party").build();

        RecommendedItemDTO recommendedItemDTO = RecommendedItemDTO
                .builder()
                .itemName("Jacket")
                .itemId(1L)
                .build();

        List<RecommendedItemDTO> recommendedItemDTOS = new ArrayList<>();
        recommendedItemDTOS.add(recommendedItemDTO);
        recommendationResponseDTO = RecommendationResponseDTO
                .builder()
                .recommendedItemDTOS(recommendedItemDTOS)
                .totalPrice(BigDecimal.valueOf(1000))
                .build();

    }

    @Test
    void testGetRecommendations() {
        when(recommendationEngineService.getRecommendations(recommendationRequestDTO, 1L)).thenReturn(recommendationResponseDTO);
        ResponseEntity<RecommendationResponseDTO> result = userExperienceController.getRecommendations(recommendationRequestDTO, 1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(recommendationResponseDTO, result.getBody());
    }
}