package org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformList {
    @JacksonXmlProperty(localName = "seq")
    private String seq;

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

    @JacksonXmlProperty(localName = "thumbnail")
    private String thumbnail;

    @JacksonXmlProperty(localName = "gpsX")
    private String gpsX;

    @JacksonXmlProperty(localName = "gpsY")
    private String gpsY;
}