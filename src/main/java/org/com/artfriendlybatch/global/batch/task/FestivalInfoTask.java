package org.com.artfriendlybatch.global.batch.task;

import lombok.RequiredArgsConstructor;
import org.com.artfriendlybatch.domain.festival.service.FestivalService;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FestivalInfoTask {
    private final FestivalService festivalService;

    public RepeatStatus FestivalInfoIntegrateTask() throws Exception {
        festivalService.integrateFestivalInfo();

        return RepeatStatus.FINISHED;
    }
}
