package org.com.artfriendlybatch.domain.exhibition.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.artfriendlybatch.domain.exhibition.callable.ExhibitionDescriptionCallable;
import org.com.artfriendlybatch.domain.exhibition.callable.ExhibitionUrlCallable;
import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionUpdateDto;
import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.com.artfriendlybatch.domain.exhibition.repository.ExhibitionInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ExhibitionInfoService {
    private final ExhibitionInfoRepository exhibitionInfoRepository;

    public List<ExhibitionInfo> findExhibitionInfoByCreateTime() {
        return exhibitionInfoRepository.findExhibitionInfoByCreateTime(LocalDate.now());
    }

    @Transactional
    public List<ExhibitionUpdateDto> getStringList() {
        List<ExhibitionUpdateDto> urls = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10); // 10개의 스레드를 가진 스레드 풀 생성

        try {
            List<ExhibitionInfo> list = findExhibitionInfoByCreateTime();
            List<Future<ExhibitionUpdateDto>> futures = new ArrayList<>();

            for (ExhibitionInfo exhibitionInfo : list) {
                Callable<ExhibitionUpdateDto> callable = new ExhibitionUrlCallable(exhibitionInfo);
                Future<ExhibitionUpdateDto> future = executor.submit(callable);
                futures.add(future);
            }

            for (Future<ExhibitionUpdateDto> future : futures) {
                try {
                    ExhibitionUpdateDto result = future.get();
                    if (result != null) {
                        urls.add(result);
                    }
                } catch (ExecutionException e) {
                    log.error("연동 오류"+e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // 모든 작업이 완료되면 스레드 풀 종료
        }

        return urls;
    }

    public void extracted(List<ExhibitionUpdateDto> exhibitionUpdateDtos) {
        List<ExhibitionUpdateDto> exhibitionUpdateDtoList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<ExhibitionUpdateDto>> futures = new ArrayList<>();

        try {
            for (ExhibitionUpdateDto updateDto : exhibitionUpdateDtos) {
                Callable<ExhibitionUpdateDto> callable = new ExhibitionDescriptionCallable(updateDto);
                Future<ExhibitionUpdateDto> future = executor.submit(callable);
                futures.add(future);
            }

            // 모든 Future 결과를 처리
            for (Future<ExhibitionUpdateDto> future : futures) {
                try {
                    ExhibitionUpdateDto result = future.get();
                    if (result != null) {
                        exhibitionUpdateDtoList.add(result);
                    }
                } catch (ExecutionException e) {
                    log.error("연동 오류 "+e.getMessage());
                }
            }

            for (ExhibitionUpdateDto updateDto : exhibitionUpdateDtoList) {
                updateDto.getExhibitionInfo().updateForm(updateDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // 스레드 풀 종료
        }
    }

}
