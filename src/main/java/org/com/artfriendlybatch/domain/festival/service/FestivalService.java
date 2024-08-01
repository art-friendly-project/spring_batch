package org.com.artfriendlybatch.domain.festival.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.artfriendlybatch.domain.festival.callable.FestivalInfoCallable;
import org.com.artfriendlybatch.domain.festival.dto.AreaBaseInfoRspDto;
import org.com.artfriendlybatch.domain.festival.dto.CommonInfoRspDto;
import org.com.artfriendlybatch.domain.festival.dto.DetailInfoRspDto;
import org.com.artfriendlybatch.domain.festival.entity.Festival;
import org.com.artfriendlybatch.domain.festival.entity.FestivalInfo;
import org.com.artfriendlybatch.domain.festival.mapper.FestivalMapper;
import org.com.artfriendlybatch.domain.festival.openfeign.FestivalApiClient;
import org.com.artfriendlybatch.domain.festival.repository.FestivalInfoRepository;
import org.com.artfriendlybatch.domain.festival.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FestivalService {
    private final FestivalApiClient festivalApiClient;
    private final FestivalMapper festivalMapper;
    private final FestivalInfoRepository festivalInfoRepository;
    private final FestivalRepository festivalRepository;
    private final String mobileOS = "ETC"; // 해당 없음
    private final String mobileApp = "Artfriendly"; // 어플 이름
    private final String type = "json";
    @Getter
    @Value("${festival.serviceKey}")
    private String serviceKey;
    private final String contentTypeId = "15"; // 축제 행사

    public AreaBaseInfoRspDto getAreaBaseList() throws Exception {
        final int numOfRows = 100;
        final int pageNo = 1;
        final String arrange = "R"; // 생성일 순, 이미지 있는 것 부터 정렬
        final String listYN = "Y"; // 목록으로 받는다
        final String areaCode = ""; // 지역 코드 공백일 시 전국

        ResponseEntity<AreaBaseInfoRspDto> areaBaseListRspDtoResponseEntity = festivalApiClient.getAreaBasedList(numOfRows, pageNo, mobileOS, mobileApp, type, serviceKey, listYN, arrange, contentTypeId, areaCode);

        if(areaBaseListRspDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return areaBaseListRspDtoResponseEntity.getBody();
        }
        else
            throw new RuntimeException("지역 기반 정보 api 호출 오류");

    }

    public CommonInfoRspDto getCommonInfo(String contentId) throws Exception {
        final String defaultYN = "Y";
        final String firstImageYN = "Y";
        final String addrinfoYN = "Y";
        final String mapinfoYN = "Y";
        final String overviewYN = "Y";

        ResponseEntity<CommonInfoRspDto> commonInfoRspDtoResponseEntity = festivalApiClient.getCommonInfo(mobileOS, mobileApp, type, contentId, contentTypeId, defaultYN, firstImageYN, addrinfoYN, mapinfoYN, overviewYN, serviceKey);

        if(commonInfoRspDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return commonInfoRspDtoResponseEntity.getBody();
        }
        else
            throw new RuntimeException("공통 정보 api 호출 오류");
    }

    public DetailInfoRspDto getDetailInfo(String contentId) throws Exception {
        ResponseEntity<DetailInfoRspDto> detailInfoRspDtoResponseEntity = festivalApiClient.getDetailInfo(mobileOS, mobileApp, serviceKey, type, contentId, contentTypeId);

        if(detailInfoRspDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return detailInfoRspDtoResponseEntity.getBody();
        }
        else
            throw new RuntimeException("공통 정보 api 호출 오류");
    }

    @Transactional
    public void integrateFestivalInfo() throws Exception {
        List<FestivalInfo> festivalInfoList = new ArrayList<>();
        ExecutorService excutor = Executors.newFixedThreadPool(10);

        try {
            AreaBaseInfoRspDto areaBaseInfoRspDto = getAreaBaseList();
            List<AreaBaseInfoRspDto.Item> itemList = areaBaseInfoRspDto.getResponse().getBody().getItems().getItem();
            List<Future<FestivalInfo>> festivalInfoFutureList = new ArrayList<>();

            for (AreaBaseInfoRspDto.Item item : itemList) {
                Callable<FestivalInfo> festivalInfoCallable = new FestivalInfoCallable(this, festivalMapper, item.getContentid());
                Future<FestivalInfo> future = excutor.submit(festivalInfoCallable);
                festivalInfoFutureList.add(future);
            }

            for (Future<FestivalInfo> future : festivalInfoFutureList) {

                FestivalInfo festivalInfo = future.get();
                if (festivalInfo != null) {
                    Optional<FestivalInfo> optionalFestivalInfo = festivalInfoRepository.findBySeq(festivalInfo.getSeq());
                    if (optionalFestivalInfo.isEmpty())
                        festivalInfoList.add(festivalInfo);
                }

            }

            createFestivalInfoList(festivalInfoList);

        } catch (Exception e) {
            Thread.currentThread().interrupt();
            log.warn("future.get() Error");
            e.printStackTrace();
        } finally {
            excutor.shutdown();
        }
    }

    @Transactional
    public void createFestivalInfoList(List<FestivalInfo> festivalInfoList) {
        for(FestivalInfo festivalInfo : festivalInfoList) {
            createFestivalInfo(festivalInfo);
        }
    }

    @Transactional
    public void createFestivalInfo(FestivalInfo festivalInfo) {
        Festival festival = Festival.builder()
                .festivalInfo(festivalInfo)
                .temperature(0.0)
                .build();

        festivalInfo.setFestival(festival);

        festivalRepository.save(festival);
    }
}
