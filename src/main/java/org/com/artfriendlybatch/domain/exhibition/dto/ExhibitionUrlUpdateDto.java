package org.com.artfriendlybatch.domain.exhibition.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;

@Getter
@Setter
@Builder
public class ExhibitionUrlUpdateDto {
    private ExhibitionInfo exhibitionInfo;
    private String url;
}
