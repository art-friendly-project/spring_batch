package org.com.artfriendlybatch.domain.exhibition.chunk;

import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionInfoIntegrateDto;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.DetailPerformInfo;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto.PerformList;
import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.com.artfriendlybatch.domain.exhibition.mapper.ExhibitionInfoMapper;
import org.com.artfriendlybatch.domain.exhibition.repository.ExhibitionInfoRepository;
import org.com.artfriendlybatch.domain.exhibition.service.ExhibitionInfoService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ExhibitionInfoChunk {
    private final ExhibitionInfoService exhibitionInfoService;
    private final ExhibitionInfoRepository exhibitionInfoRepository;

    public ExhibitionInfoChunk(ExhibitionInfoService exhibitionInfoService, ExhibitionInfoRepository exhibitionInfoRepository) {
        this.exhibitionInfoService = exhibitionInfoService;
        this.exhibitionInfoRepository = exhibitionInfoRepository;
    }

    @Bean
    public ItemReader<PerformList> exhibitionInfoIntegrateReader() {
        return new PerformReader(exhibitionInfoService); // API를 호출하여 데이터 읽기
    }

    @Bean
    public ItemProcessor<PerformList, ExhibitionInfoIntegrateDto> exhibitionInfoIntegrateProcessor(ExhibitionInfoMapper exhibitionInfoMapper) {
        return item -> {
            // API 호출 및 ExhibitionInfo 생성 로직(이미 있는 값이면 업데이트로 수정)
            int seq = Integer.parseInt(item.getSeq());
            DetailPerformInfo detailPerformInfo = exhibitionInfoService.getDetailPerformInfo(seq);
            Optional<ExhibitionInfo> optionalExhibitionInfo = Optional.ofNullable(exhibitionInfoRepository.findExhibitionInfoBySeq(seq));

            // 새로운 dto 하나 더 만들어서 저장 할 때 업뎃 or 새로 생성 나눠서 할 수 있도록!!
            return optionalExhibitionInfo
                    .map(existingInfo -> {
                        // 업데이트
                        existingInfo.updateForm(exhibitionInfoMapper.perforInfoToExhibitionInfo(detailPerformInfo));
                        return ExhibitionInfoIntegrateDto.builder()
                                .exhibitionInfo(existingInfo)
                                .updateAble(true)
                                .build();
                    })
                    .orElseGet(() -> {
                        // 생성
                        ExhibitionInfo newExhibitionInfo = exhibitionInfoMapper.perforInfoToExhibitionInfo(detailPerformInfo);
                        return ExhibitionInfoIntegrateDto.builder()
                                .exhibitionInfo(newExhibitionInfo)
                                .updateAble(false)
                                .build();
                    });
        };
    }

    @Bean
    public ItemWriter<ExhibitionInfoIntegrateDto> exhibitionInfoIntegrateWriter() {
        return items -> {
            for (ExhibitionInfoIntegrateDto dto : items) {
                if (dto.isUpdateAble()) {
                    // 업데이트 로직
                    exhibitionInfoRepository.save(dto.getExhibitionInfo());
                } else {
                    // 새로 생성하는 로직
                    exhibitionInfoService.createExhibition(dto.getExhibitionInfo());
                }
            }
        };
    }

    @Bean
    public ItemReader<ExhibitionInfo> exhibitionInfoReader() {
        return new ExhibitionInfoReader(exhibitionInfoService);
    }

    @Bean
    public ItemProcessor<ExhibitionInfo, ExhibitionInfo> exhibitionInfoProcessor() {
        return ExhibitionInfo::updateProgressStatus;
    }

    @Bean
    public ItemWriter<ExhibitionInfo> exhibitionInfoWriter() {
        return exhibitionInfoRepository::saveAll;
    }
}
