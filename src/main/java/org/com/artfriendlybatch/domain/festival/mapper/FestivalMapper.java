package org.com.artfriendlybatch.domain.festival.mapper;

import org.com.artfriendlybatch.domain.festival.dto.CommonInfoRspDto;
import org.com.artfriendlybatch.domain.festival.dto.DetailInfoRspDto;
import org.com.artfriendlybatch.domain.festival.entity.FestivalInfo;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface FestivalMapper {
    default FestivalInfo apiRspsToFestivalInfo(CommonInfoRspDto commonInfoRspDto, DetailInfoRspDto detailInfoRspDto) {
        String progressStatus = "";
        String area="";

        CommonInfoRspDto.Item commonItem = commonInfoRspDto.getResponse().getBody().getItems().getItem().get(0);
        DetailInfoRspDto.Item detailItem = detailInfoRspDto.getResponse().getBody().getItems().getItem().get(0);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate startDate = LocalDate.parse(detailItem.getEventstartdate(), dateFormat);
        LocalDate endDate = LocalDate.parse(detailItem.getEventenddate(), dateFormat);

        LocalDate now = LocalDate.now();

        // 종료 날짜가 지난 경우
        if (now.isAfter(endDate)) {
            progressStatus = "ended";
        }
        // 현재 날짜가 시작 날짜와 같거나 이후이며, 종료 날짜 이전인 경우
        else if (!now.isBefore(startDate) && now.isBefore(endDate)) {
            progressStatus = "inProgress";
        }
        // 시작 날짜가 아직 오지 않은 경우
        else if (now.isBefore(startDate)) {
            progressStatus = "scheduled";
        }

        if( commonItem.getAreacode() != null ) {
            switch (commonItem.getAreacode()) {
                case "1" -> area = "서울";
                case "31", "2" -> area = "경기/인천";
                case "33", "34", "3", "8" -> area = "충청/대전";
                case "35", "4" -> area = "경북/대구";
                case "37", "38", "5" -> area = "전라/광주";
                case "36", "6", "7" -> area = "경남/부산";
                case "32" -> area = "강원";
                case "39" -> area = "제주도";

                default -> area = "기타";
            }
        }

        return FestivalInfo.builder()
                .title(commonItem.getTitle())
                .seq(Integer.parseInt(commonItem.getContentid()))
                .phone(commonItem.getTel())
                .homepageUrl(commonItem.getHomepage())
                .imageUrl(commonItem.getFirstimage())
                .placeAddr(commonItem.getAddr1())
                .gpsX(Double.parseDouble(commonItem.getMapx()))
                .gpsY(Double.parseDouble(commonItem.getMapy()))
                .description(commonItem.getOverview().length() >= 1000 ? "" : commonItem.getOverview())
                .place(detailItem.getEventplace())
                .startDate(startDate)
                .endDate(endDate)
                .organizer(detailItem.getSponsor1())
                .price(detailItem.getUsetimefestival())
                .progressStatus(progressStatus)
                .area(area)
                .build();
    }
}
