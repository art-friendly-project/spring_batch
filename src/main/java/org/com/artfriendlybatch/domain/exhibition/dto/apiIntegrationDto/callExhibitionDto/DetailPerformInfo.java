package org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailPerformInfo {
    @JacksonXmlProperty(localName = "seq")
    private int seq;

    @JacksonXmlProperty(localName = "title")
    private String title;

    @JacksonXmlProperty(localName = "startDate")
    private String startDate;

    @JacksonXmlProperty(localName = "endDate")
    private String endDate;

    @JacksonXmlProperty(localName = "place")
    private String place;

    @JacksonXmlProperty(localName = "realmName")
    private String realmName;

    @JacksonXmlProperty(localName = "area")
    private String area;

    @JacksonXmlProperty(localName = "subTitle")
    private String subTitle;

    @JacksonXmlProperty(localName = "price")
    private String price;

    @JacksonXmlProperty(localName = "contents1")
    private String contents1;

    @JacksonXmlProperty(localName = "contents2")
    private String contents2;

    @JacksonXmlProperty(localName = "url")
    private String url;

    @JacksonXmlProperty(localName = "phone")
    private String phone;

    @JacksonXmlProperty(localName = "imgUrl")
    private String imgUrl;

    @JacksonXmlProperty(localName = "gpsX")
    private double gpsX;

    @JacksonXmlProperty(localName = "gpsY")
    private double gpsY;

    @JacksonXmlProperty(localName = "placeUrl")
    private String placeUrl;

    @JacksonXmlProperty(localName = "placeAddr")
    private String placeAddr;

    @JacksonXmlProperty(localName = "placeSeq")
    private int placeSeq;

}
