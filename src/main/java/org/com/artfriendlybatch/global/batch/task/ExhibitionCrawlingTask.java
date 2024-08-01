package org.com.artfriendlybatch.global.batch.task;

import lombok.RequiredArgsConstructor;
import org.com.artfriendlybatch.domain.exhibition.service.ExhibitionInfoService;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExhibitionCrawlingTask {
    private final ExhibitionInfoService exhibitionInfoService;

    public RepeatStatus exhibitionInfoCrawling() {
        exhibitionInfoService.setDetailHomePageUrl();

        return RepeatStatus.FINISHED;
    }
}
