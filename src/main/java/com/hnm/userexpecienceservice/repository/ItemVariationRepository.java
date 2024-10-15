package com.hnm.userexpecienceservice.repository;

import com.hnm.userexpecienceservice.model.ItemVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemVariationRepository extends JpaRepository<ItemVariation, Long> {

    @Query("SELECT iv FROM ItemVariation iv " +
            "JOIN iv.item i " +
            "WHERE iv.stockQuantity > 0 " +
            "AND ((i.tags LIKE CONCAT('%', :eventType, '%')) " +
            "OR (:stylePreferences IS NULL OR i.tags LIKE CONCAT('%', :stylePreferences, '%')) )" +
            "AND (i.basePrice + iv.additionalPrice <= :budget) " +
            "AND (:colorPreferences IS NULL OR iv.color = :colorPreferences) " +
            "AND (:sizePreferences IS NULL OR iv.size = :sizePreferences)")
    List<ItemVariation> findFilteredItems(@Param("eventType") String eventType,
                                          @Param("budget") BigDecimal budget,
                                          @Param("stylePreferences") String stylePreferences,
                                          @Param("colorPreferences") String colorPreferences,
                                          @Param("sizePreferences") String sizePreferences);
}
