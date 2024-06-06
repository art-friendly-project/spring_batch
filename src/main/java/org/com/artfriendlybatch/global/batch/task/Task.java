package org.com.artfriendlybatch.global.batch.task;

import lombok.RequiredArgsConstructor;
import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionUpdateDto;;
import org.com.artfriendlybatch.domain.exhibition.service.ExhibitionInfoService;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Task {
    private final ExhibitionInfoService exhibitionInfoService;

    public RepeatStatus exhibitionInfoCrawling() {
        List<ExhibitionUpdateDto> urls = exhibitionInfoService.getStringList();
        exhibitionInfoService.extracted(urls);
        return RepeatStatus.FINISHED;
    }


}
