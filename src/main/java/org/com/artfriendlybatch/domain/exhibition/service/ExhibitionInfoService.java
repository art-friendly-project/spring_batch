package org.com.artfriendlybatch.domain.exhibition.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.artfriendlybatch.domain.exhibition.callable.ExhibitionUrlCallable;
import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionUrlUpdateDto;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.ApiRequestBuilder;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.DetailPerformInfo;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.ExhibitionApiRspDto;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto.ExhibitionsApiRspData;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto.PerformList;
import org.com.artfriendlybatch.domain.exhibition.entity.Exhibition;
import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.com.artfriendlybatch.domain.exhibition.openfeign.ExhibitionApiClient;
import org.com.artfriendlybatch.domain.exhibition.repository.ExhibitionInfoRepository;
import org.com.artfriendlybatch.domain.exhibition.repository.ExhibitionRepository;
import org.com.artfriendlybatch.global.utils.LocalDateFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExhibitionInfoService {
    @Getter
    @Value("${exhibition.serviceKey}")
    String serviceKey;

    private final ExhibitionInfoRepository exhibitionInfoRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionApiClient exhibitionApiClient;

    @Transactional
    public void createExhibition(ExhibitionInfo exhibitionInfo) {
        Exhibition exhibition = Exhibition.builder()
                .exhibitionInfo(exhibitionInfo)
                .build();

        exhibitionInfo.setExhibition(exhibition);

        exhibitionRepository.save(exhibition);
    }

    @Transactional
    public void setDetailHomePageUrl() {
        List<ExhibitionUrlUpdateDto> urls = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10); // 10개의 스레드를 가진 스레드 풀 생성

        try {
            List<ExhibitionInfo> list = getExhibitionInfoByProgressStatusIn(List.of("inProgress", "scheduled"));
            List<Future<ExhibitionUrlUpdateDto>> futures = new ArrayList<>();

            for (ExhibitionInfo exhibitionInfo : list) {
                Callable<ExhibitionUrlUpdateDto> callable = new ExhibitionUrlCallable(exhibitionInfo);
                Future<ExhibitionUrlUpdateDto> future = executor.submit(callable);
                futures.add(future);
            }

            for (Future<ExhibitionUrlUpdateDto> future : futures) {
                try {
                    ExhibitionUrlUpdateDto result = future.get();
                    if (result != null) {
                        urls.add(result);
                    }
                } catch (ExecutionException e) {
                    log.error("연동 오류"+e.getMessage());
                }
            }

            for (ExhibitionUrlUpdateDto updateDto : urls) {
                updateDto.getExhibitionInfo().updateForm(updateDto);
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
            log.warn("future.get() Error");
            e.printStackTrace();
        } finally {
            executor.shutdown(); // 모든 작업이 완료되면 스레드 풀 종료
        }
    }

    public List<PerformList> getPerformInfo() {
        List<PerformList> allPerforLists = new ArrayList<>();

        LocalDate now = LocalDate.now();
        int duration = 1;
        String realmCode = "D000";
        String startDate = LocalDateFormatter.convertToString(now, "yyyyMMdd");
        String endDate = LocalDateFormatter.convertToString(now.plusMonths(duration), "yyyyMMdd");
        int cPage = 1;
        int rows = 100;
        int sortStdr = 1;

        while(true) {
            ApiRequestBuilder apiRequestBuilder = ApiRequestBuilder.builder()
                    .realmCode(realmCode)
                    .startDate(startDate)
                    .endDate(endDate)
                    .rows(rows)
                    .cPage(cPage)
                    .sortStdr(sortStdr)
                    .build();

            log.info("전체 전시 리스트 호출");
            ExhibitionsApiRspData responseData = callExhibitionApi(apiRequestBuilder);
            List<PerformList> perforLists = responseData.getExhibitionsMsgBody().getPerforList();

            // perforLists가 null 이면 api 호출 중지
            if(perforLists == null)
                break;
            log.info("{}", perforLists);
            log.info(String.valueOf(perforLists.isEmpty()));
            allPerforLists.addAll(perforLists);
            cPage = responseData.getExhibitionsMsgBody().getCPage() + 1;
        }

        return allPerforLists;
    }

    public DetailPerformInfo getDetailPerformInfo(int seq) {
        return callExhibitionDetailsApi(seq).getMsgBody().getPerforInfo();
    }

    public List<ExhibitionInfo> getExhibitionInfoByProgressStatusIn(List<String> progressStatusList) {
        return exhibitionInfoRepository.findExhibitionInfoByProgressStatusIn(progressStatusList);
    }

    private ExhibitionApiRspDto callExhibitionDetailsApi(int seq) {

        ExhibitionApiRspDto exhibitionApiRspDto = exhibitionApiClient.getDetail(String.valueOf(seq), serviceKey);

        if(exhibitionApiRspDto.getComMsgHeader().getSuccessYN().equals("N"))
            throw new RuntimeException("전시 정보 api 호출 오류");

        return exhibitionApiRspDto;
    }

    private ExhibitionsApiRspData callExhibitionApi(ApiRequestBuilder apiRequestBuilder) {
        ExhibitionsApiRspData responseData = exhibitionApiClient.getRealm(
                apiRequestBuilder.getRealmCode(),
                apiRequestBuilder.getCPage(),
                apiRequestBuilder.getRows(),
                apiRequestBuilder.getStartDate(),
                apiRequestBuilder.getEndDate(),
                apiRequestBuilder.getSido(),
                apiRequestBuilder.getGugun(),
                apiRequestBuilder.getPlace(),
                apiRequestBuilder.getGpsxfrom(),
                apiRequestBuilder.getGpsxfrom(),
                apiRequestBuilder.getGpsxto(),
                apiRequestBuilder.getGpsyto(),
                apiRequestBuilder.getKeyword(),
                apiRequestBuilder.getSortStdr(),
                serviceKey
        );

        if(responseData.getComMsgHeader().getSuccessYN().equals("N")) {
            throw new RuntimeException("전시 정보 api 호출 오류");
        }

        return responseData;
    }
}
