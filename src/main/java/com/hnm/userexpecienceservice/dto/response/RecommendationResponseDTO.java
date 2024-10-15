package com.hnm.userexpecienceservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Schema(description = "Recommendation Response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponseDTO {

    private List<RecommendedItemDTO> recommendedItemDTOS;
    private BigDecimal totalPrice;
}
