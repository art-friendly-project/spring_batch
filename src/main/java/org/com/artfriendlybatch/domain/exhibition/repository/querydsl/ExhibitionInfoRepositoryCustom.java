package org.com.artfriendlybatch.domain.exhibition.repository.querydsl;

import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;

import java.util.List;


public interface ExhibitionInfoRepositoryCustom {
    ExhibitionInfo findExhibitionInfoBySeq(int seq);
    List<ExhibitionInfo> findExhibitionInfoByProgressStatusIn(List<String> progressStatus);
    List<ExhibitionInfo> findExhibitionInfoByProgressStatus(String progressStatus);
}

