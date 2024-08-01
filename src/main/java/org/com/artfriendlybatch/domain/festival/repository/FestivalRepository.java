package org.com.artfriendlybatch.domain.festival.repository;

import org.com.artfriendlybatch.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalRepository extends JpaRepository<Festival, Long> {

}