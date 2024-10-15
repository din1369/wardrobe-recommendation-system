package com.hnm.userexpecienceservice.repository;

import com.hnm.userexpecienceservice.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long>{

    @Query(value = "SELECT * FROM purchase_history ph WHERE ph.user_id = :userId ORDER BY ph.purchase_date DESC LIMIT :limit", nativeQuery = true)
    List<PurchaseHistory> findRecentPurchasesByUser(@Param("userId") Long userId, @Param("limit") int limit);

}
