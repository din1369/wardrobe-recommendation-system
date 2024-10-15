package com.hnm.userexpecienceservice.service;

import com.hnm.userexpecienceservice.dto.request.RecommendationRequestDTO;
import com.hnm.userexpecienceservice.dto.response.RecommendationResponseDTO;

import java.util.List;

public interface RecommendationEngineService {

    RecommendationResponseDTO getRecommendations(RecommendationRequestDTO recommendationRequestDTO, Long userID);
}
