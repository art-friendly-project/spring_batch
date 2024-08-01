package org.com.artfriendlybatch.domain.exhibition.chunk;

import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.com.artfriendlybatch.domain.exhibition.service.ExhibitionInfoService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

public class ExhibitionInfoReader implements ItemReader<ExhibitionInfo> {
    private final ExhibitionInfoService exhibitionInfoService;
    private final List<ExhibitionInfo> details;
    private int currentIndex = 0;

    public ExhibitionInfoReader(ExhibitionInfoService exhibitionInfoService) {
        this.exhibitionInfoService = exhibitionInfoService;
        this.details = this.fetchDataFromDb();
    }


    @Override
    public ExhibitionInfo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (currentIndex < details.size()) {
            return details.get(currentIndex++); // 현재 인덱스의 항목 반환 후 인덱스 증가
        }
        return null; // 더 이상 읽을 항목이 없으면 null 반환
    }

    private List<ExhibitionInfo> fetchDataFromDb() {
        return exhibitionInfoService.getExhibitionInfoByProgressStatusIn(List.of("inProgress", "scheduled"));
    }
}
