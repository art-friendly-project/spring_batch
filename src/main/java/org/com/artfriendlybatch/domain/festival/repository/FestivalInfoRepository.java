package org.com.artfriendlybatch.domain.festival.repository;

import org.com.artfriendlybatch.domain.festival.entity.FestivalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FestivalInfoRepository extends JpaRepository<FestivalInfo, Long> {
    Optional<FestivalInfo> findBySeq(int seq);
}
