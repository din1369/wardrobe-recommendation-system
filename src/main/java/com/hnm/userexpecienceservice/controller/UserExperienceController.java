package com.hnm.userexpecienceservice.controller;

import com.hnm.userexpecienceservice.dto.request.RecommendationRequestDTO;
import com.hnm.userexpecienceservice.dto.response.RecommendationResponseDTO;
import com.hnm.userexpecienceservice.service.RecommendationEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Recommendation API", description = "API for generate recommendations")
public class UserExperienceController {

    private final RecommendationEngineService recommendationEngineService;

    public UserExperienceController(RecommendationEngineService recommendationEngineService) {
        this.recommendationEngineService = recommendationEngineService;
    }

    @Operation(summary = "Get the recommendations based on user input")
    @PostMapping("/recommendations/{userID}")
    public ResponseEntity<RecommendationResponseDTO> getRecommendations(@Valid
                                                                      @RequestBody RecommendationRequestDTO recommendationRequestDTO,
                                                                        @PathVariable Long userID) {
        return ResponseEntity.ok(recommendationEngineService.getRecommendations(recommendationRequestDTO, userID));
    }
}

