package com.hnm.userexpecienceservice.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Schema(description = "Recommendation Request")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequestDTO {

    @NotEmpty(message = "eventType cannot be null")
    private String eventType;

    @NotNull(message = "eventType cannot be null")
    private BigDecimal budget;

    private String stylePreferences;

    private String colorPreferences;

    private String sizePreferences;

}
