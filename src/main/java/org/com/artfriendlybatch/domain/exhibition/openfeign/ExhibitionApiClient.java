package org.com.artfriendlybatch.domain.exhibition.openfeign;

import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.ExhibitionApiRspDto;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto.ExhibitionsApiRspData;
import org.com.artfriendlybatch.global.config.openfeign.ExhibitionFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ExhibitionApiClient", url = "${exhibition.api}", configuration = ExhibitionFeignConfig.class)
public interface ExhibitionApiClient {
    @GetMapping(value = "/realm")
    ExhibitionsApiRspData getRealm(@RequestParam(value = "realmCode") String realmCode,
                                                   @RequestParam(value = "cPage") int cPage,
                                                   @RequestParam(value = "rows") int rows,
                                                   @RequestParam(value = "from") String startDate,
                                                   @RequestParam(value = "to") String endDate,
                                                   @RequestParam(value = "sido", defaultValue = "") String sido,
                                                   @RequestParam(value = "gugun", defaultValue = "") String gugun,
                                                   @RequestParam(value = "place", defaultValue = "") String place,
                                                   @RequestParam(value = "gpsxfrom", defaultValue = "" ) String gpsxfrom,
                                                   @RequestParam(value = "gpsyfrom", defaultValue = "" ) String gpsyfrom,
                                                   @RequestParam(value = "gpsxto", defaultValue = "" ) String gpsxto,
                                                   @RequestParam(value = "gpsyto", defaultValue = "" ) String gpsyto,
                                                   @RequestParam(value = "keyword", defaultValue = "" ) String keyword,
                                                   @RequestParam(value = "sortStdr") int sortStdr,
                                                   @RequestParam(value = "serviceKey") String serviceKey);

    @GetMapping(value = "/d/")
    ExhibitionApiRspDto getDetail(@RequestParam(value = "seq") String seq,
                                                  @RequestParam(value = "serviceKey") String serviceKey);
}
