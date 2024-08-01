package org.com.artfriendlybatch.domain.festival.callable;

import org.com.artfriendlybatch.domain.festival.dto.CommonInfoRspDto;
import org.com.artfriendlybatch.domain.festival.dto.DetailInfoRspDto;
import org.com.artfriendlybatch.domain.festival.entity.FestivalInfo;
import org.com.artfriendlybatch.domain.festival.mapper.FestivalMapper;
import org.com.artfriendlybatch.domain.festival.service.FestivalService;

import java.util.concurrent.Callable;

public class FestivalInfoCallable implements Callable<FestivalInfo> {
    private final FestivalService festivalService;
    private final FestivalMapper festivalMapper;
    private final String contentId;

    public FestivalInfoCallable(FestivalService festivalService, FestivalMapper festivalMapper, String contentId) {
        this.festivalService = festivalService;
        this.festivalMapper = festivalMapper;
        this.contentId = contentId;
    }

    @Override
    public FestivalInfo call() throws Exception {
        return getFestivalInfo();
    }

    private FestivalInfo getFestivalInfo() throws Exception {
        CommonInfoRspDto commonInfoRspDto = festivalService.getCommonInfo(contentId);
        DetailInfoRspDto detailInfoRspDto = festivalService.getDetailInfo(contentId);

        return festivalMapper.apiRspsToFestivalInfo(commonInfoRspDto, detailInfoRspDto);
    }
}
