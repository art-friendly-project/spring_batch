package org.com.artfriendlybatch.domain.exhibition.mapper;

import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.DetailPerformInfo;
import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.com.artfriendlybatch.global.utils.LocalDateFormatter;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface ExhibitionInfoMapper {
    default ExhibitionInfo perforInfoToExhibitionInfo(DetailPerformInfo perforInfo) {
        if ( perforInfo == null ) {
            return null;
        }

        LocalDate startDate = LocalDateFormatter.converToLocalDate(perforInfo.getStartDate(), "yyyyMMdd");
        LocalDate endDate =  LocalDateFormatter.converToLocalDate(perforInfo.getEndDate(), "yyyyMMdd");

        ExhibitionInfo.ExhibitionInfoBuilder exhibitionInfo = ExhibitionInfo.builder();

        exhibitionInfo.seq( perforInfo.getSeq() );
        exhibitionInfo.title( perforInfo.getTitle() );

        if ( perforInfo.getStartDate() != null ) {
            exhibitionInfo.startDate( startDate );
        }
        if ( perforInfo.getEndDate() != null ) {
            exhibitionInfo.endDate( endDate );
        }
        exhibitionInfo.place( perforInfo.getPlace() );
        exhibitionInfo.realmName( perforInfo.getRealmName() );
        exhibitionInfo.area(perforInfo.getArea());
        exhibitionInfo.imageUrl( perforInfo.getImgUrl() );
        exhibitionInfo.gpsX( perforInfo.getGpsX() );
        exhibitionInfo.gpsY( perforInfo.getGpsY() );
        exhibitionInfo.ticketingUrl( perforInfo.getUrl() );
        exhibitionInfo.phone( perforInfo.getPhone() );
        exhibitionInfo.price( perforInfo.getPrice() );
        exhibitionInfo.placeAddr( perforInfo.getPlaceAddr() );

        LocalDate now = LocalDate.now();

        if(now.isBefore(startDate))
            exhibitionInfo.progressStatus("scheduled");
        else
            exhibitionInfo.progressStatus("inProgress");

        return exhibitionInfo.build();
    }
}
