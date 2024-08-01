package org.com.artfriendlybatch.domain.exhibition.repository;

import org.com.artfriendlybatch.domain.exhibition.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
}
