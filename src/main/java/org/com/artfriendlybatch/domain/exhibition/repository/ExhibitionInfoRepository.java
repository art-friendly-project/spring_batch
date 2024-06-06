package org.com.artfriendlybatch.domain.exhibition.repository;

import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExhibitionInfoRepository extends JpaRepository<ExhibitionInfo, Long> {
    @Query("SELECT e FROM ExhibitionInfo e " +
            "WHERE FUNCTION('DATE', e.createTime) = :createTime ")
    List<ExhibitionInfo> findExhibitionInfoByCreateTime(@Param("createTime") LocalDate createTime);
}
