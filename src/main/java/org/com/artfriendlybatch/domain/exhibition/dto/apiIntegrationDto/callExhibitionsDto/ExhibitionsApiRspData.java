package org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.ComMsgHeader;

@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "response")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExhibitionsApiRspData {
    @JacksonXmlProperty(localName = "comMsgHeader")
    private ComMsgHeader comMsgHeader;

    @JacksonXmlProperty(localName = "msgBody")
    private ExhibitionsMsgBody exhibitionsMsgBody;

}
