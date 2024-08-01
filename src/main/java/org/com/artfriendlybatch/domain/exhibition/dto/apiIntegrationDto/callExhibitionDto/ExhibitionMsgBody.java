package org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExhibitionMsgBody {
    @JacksonXmlProperty(localName = "seq")
    private int seq;

    @JacksonXmlProperty(localName ="perforInfo")
    private DetailPerformInfo perforInfo;
}
