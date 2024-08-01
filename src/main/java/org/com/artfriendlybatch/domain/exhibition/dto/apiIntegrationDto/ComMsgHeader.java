package org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComMsgHeader {
    @JacksonXmlProperty(localName = "RequestMsgID")
    private String requestMsgID;

    @JacksonXmlProperty(localName = "ResponseTime")
    private String responseTime;

    @JacksonXmlProperty(localName = "ResponseMsgID")
    private String responseMsgID;

    @JacksonXmlProperty(localName = "SuccessYN")
    private String successYN;

    @JacksonXmlProperty(localName = "ReturnCode")
    private String returnCode;

    @JacksonXmlProperty(localName = "ErrMsg")
    private String errMsg;
}
