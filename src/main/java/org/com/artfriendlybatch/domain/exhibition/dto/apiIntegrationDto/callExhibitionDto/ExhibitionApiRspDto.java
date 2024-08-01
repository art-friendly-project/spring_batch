package org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.ComMsgHeader;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JacksonXmlRootElement(localName = "response")
public class ExhibitionApiRspDto {
    @JacksonXmlProperty(localName = "comMsgHeader")
    private ComMsgHeader comMsgHeader;

    @JacksonXmlProperty(localName = "msgBody")
    private ExhibitionMsgBody msgBody;
}
