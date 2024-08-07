package org.com.artfriendlybatch.global.config.batch;

import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionInfoIntegrateDto;
import org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto.PerformList;
import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.com.artfriendlybatch.domain.exhibition.mapper.ExhibitionInfoMapper;
import org.com.artfriendlybatch.domain.exhibition.chunk.ExhibitionInfoChunk;
import org.com.artfriendlybatch.global.batch.policy.CustomSkipPolicy;
import org.com.artfriendlybatch.global.batch.task.ExhibitionCrawlingTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager") // 해당 설정이 들어가면 Spring Boot는 이를 사용자가 커스텀 설정을 제공한 것으로 간주하고, 일부 자동 설정을 비활성화힌디
public class ExhibitionBatchConfiguration {
    private final ExhibitionCrawlingTask exhibitionCrawlingTask;
    private final ExhibitionInfoChunk exhibitionInfoChunk;

    public ExhibitionBatchConfiguration(ExhibitionCrawlingTask exhibitionCrawlingTask, ExhibitionInfoChunk exhibitionInfoChunk) {
        this.exhibitionCrawlingTask = exhibitionCrawlingTask;
        this.exhibitionInfoChunk = exhibitionInfoChunk;
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    @Primary
    @Bean
    public Job exhibitionInfoIntegrateJob(JobRepository jobRepository, Step crawlingStep, @Qualifier("integrateStep") Step exhibitionIntegrateStep, @Qualifier("progressUpdateStep") Step progressUpdateStep) {
        return new JobBuilder("ExhibitionInfoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(exhibitionIntegrateStep)
                .next(crawlingStep)
                .next(progressUpdateStep)
                .build();
    }

    // 다중 db를 이용할 때는 tx를 직접 지정해줘야한다.
    @Bean
    public Step crawlingStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("exhibitionInfoCrawlingStep", jobRepository)
                .tasklet(exhibitionInfoCrawlingTasklet(), tx)
                .build();
    }

    @Bean(name = "integrateStep")
    @JobScope
    Step exhibitionIntegrateStep(JobRepository jobRepository, PlatformTransactionManager tx, ExhibitionInfoMapper exhibitionInfoMapper) {
        return new StepBuilder("exhibitionInfoIntegrateStep", jobRepository)
                .<PerformList, ExhibitionInfoIntegrateDto>chunk(10, tx)
                .reader(exhibitionInfoChunk.exhibitionInfoIntegrateReader())
                .processor(exhibitionInfoChunk.exhibitionInfoIntegrateProcessor(exhibitionInfoMapper))
                .writer(exhibitionInfoChunk.exhibitionInfoIntegrateWriter())
                .faultTolerant()
                .skipLimit(10)
                .skipPolicy(new CustomSkipPolicy())
                .build();
    }

    @Bean(name = "progressUpdateStep")
    Step exhibitionProgressUpdateStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("exhibitionInfoProgressUpdateStep", jobRepository)
                .<ExhibitionInfo, ExhibitionInfo>chunk(10, tx)
                .reader(exhibitionInfoChunk.exhibitionInfoReader())
                .processor(exhibitionInfoChunk.exhibitionInfoProcessor())
                .writer(exhibitionInfoChunk.exhibitionInfoWriter())
                .build();
    }

    public Tasklet exhibitionInfoCrawlingTasklet() {
        return ((contribution, chunkContext) -> exhibitionCrawlingTask.exhibitionInfoCrawling());
    }
}
