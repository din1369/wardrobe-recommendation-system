package com.hnm.userexpecienceservice.repository;

import com.hnm.userexpecienceservice.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
