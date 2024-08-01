package org.com.artfriendlybatch.domain.festival.openfeign;

import org.com.artfriendlybatch.domain.festival.dto.AreaBaseInfoRspDto;
import org.com.artfriendlybatch.domain.festival.dto.CommonInfoRspDto;
import org.com.artfriendlybatch.domain.festival.dto.DetailInfoRspDto;
import org.com.artfriendlybatch.global.config.openfeign.FestivalFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "FestivalApiClient", url = "${festival.api}", configuration = FestivalFeignConfig.class)
public interface FestivalApiClient {
    @GetMapping(value = "/areaBasedList1")
     ResponseEntity<AreaBaseInfoRspDto> getAreaBasedList(@RequestParam("numOfRows") int numOfRows,
                                                         @RequestParam("pageNo") int pageNo,
                                                         @RequestParam("MobileOS") String mobileOS,
                                                         @RequestParam("MobileApp") String mobileApp,
                                                         @RequestParam("_type") String type,
                                                         @RequestParam("ServiceKey") String serviceKey,
                                                         @RequestParam("listYN") String listYN,
                                                         @RequestParam("arrange") String arrange,
                                                         @RequestParam("contentTypeId") String contentTypeId,
                                                         @RequestParam("areaCode") String areaCode
                                                        );

    @GetMapping(value = "/detailCommon1")
    ResponseEntity<CommonInfoRspDto> getCommonInfo(@RequestParam("MobileOS") String mobileOs,
                                                @RequestParam("MobileApp") String mobileApp,
                                                @RequestParam("_type") String type,
                                                @RequestParam("contentId") String contentId,
                                                @RequestParam("contentTypeId") String contentTypeId,
                                                @RequestParam("defaultYN") String defaultYN,
                                                @RequestParam("firstImageYN") String firstImageYN,
                                                @RequestParam("addrinfoYN") String addrinfoYN,
                                                @RequestParam("mapinfoYN") String mapinfoYN,
                                                @RequestParam("overviewYN") String overviewYN,
                                                @RequestParam("ServiceKey") String serviceKey);

    @GetMapping(value = "detailIntro1")
    ResponseEntity<DetailInfoRspDto> getDetailInfo(@RequestParam("MobileOS") String mobileOS,
                                                @RequestParam("MobileApp") String mobileApp,
                                                @RequestParam("ServiceKey") String serviceKey,
                                                @RequestParam("_type") String type,
                                                @RequestParam("contentId") String contentId,
                                                @RequestParam("contentTypeId") String contentTypeId);

}
