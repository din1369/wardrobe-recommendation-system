package com.hnm.userexpecienceservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendedItemDTO {

    private Long itemId;
    private String itemName;
    private String category;
    private BigDecimal price;
    private String size;
    private String color;
}
