package org.com.artfriendlybatch.domain.festival.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalInfoReqDto {
    private int seq;
    private String title;
    private String description;
    private String organizer;
    private String homepageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private String place;
    private String area;
    private String imageUrl;
    private double gpsX;
    private double gpsY;
    private String phone;
    private String price;
    private String placeAddr;
    private String progressStatus;

    @Builder
    public FestivalInfoReqDto(int seq, String title, String description, String organizer, String homepageUrl, LocalDate startDate, LocalDate endDate, String place, String area, String imageUrl, double gpsX, double gpsY, String phone, String price, String placeAddr, String progressStatus) {
        this.seq = seq;
        this.title = title;
        this.description = description;
        this.organizer = organizer;
        this.homepageUrl = homepageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.area = area;
        this.imageUrl = imageUrl;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.phone = phone;
        this.price = price;
        this.placeAddr = placeAddr;
        this.progressStatus = progressStatus;
    }
}
