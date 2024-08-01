package org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExhibitionsMsgBody {
    @JacksonXmlProperty(localName = "totalCount")
    private int totalCount;

    @JacksonXmlProperty(localName = "cPage")
    private int cPage;

    @JacksonXmlElementWrapper(localName = "perforList", useWrapping = false)
    private List<PerformList> perforList;
}
