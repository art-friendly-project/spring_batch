package org.com.artfriendlybatch.domain.exhibition.repository;

import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.com.artfriendlybatch.domain.exhibition.repository.querydsl.ExhibitionInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionInfoRepository extends JpaRepository<ExhibitionInfo, Long>, ExhibitionInfoRepositoryCustom {
}
