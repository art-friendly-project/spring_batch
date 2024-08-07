package org.com.artfriendlybatch.domain.exhibition.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;

import java.util.List;

import static org.com.artfriendlybatch.domain.exhibition.entity.QExhibitionInfo.exhibitionInfo;

public class ExhibitionInfoRepositoryCustomImp implements ExhibitionInfoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ExhibitionInfoRepositoryCustomImp(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public ExhibitionInfo findExhibitionInfoBySeq(int seq) {
        return queryFactory.select(exhibitionInfo)
                .from(exhibitionInfo)
                .where(exhibitionInfo.seq.eq(seq))
                .fetchOne();
    }

    @Override
    public List<ExhibitionInfo> findExhibitionInfoByProgressStatusIn(List<String> progressStatus) {
        return queryFactory.selectFrom(exhibitionInfo)
                .where(exhibitionInfo.progressStatus.in(progressStatus))
                .fetch();
    }

    @Override
    public List<ExhibitionInfo> findExhibitionInfoByProgressStatus(String progressStatus) {
        return queryFactory.selectFrom(exhibitionInfo)
                .where(exhibitionInfo.progressStatus.eq(progressStatus))
                .fetch();
    }
}
