package org.com.artfriendlybatch.global.config.batch;

import org.com.artfriendlybatch.global.batch.task.FestivalInfoTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager") // 해당 설정이 들어가면 Spring Boot는 이를 사용자가 커스텀 설정을 제공한 것으로 간주하고, 일부 자동 설정을 비활성화힌디
public class FestivalInfoBatchConfiguration {
    private final FestivalInfoTask festivalInfoTask;

    public FestivalInfoBatchConfiguration(FestivalInfoTask festivalInfoTask) {
        this.festivalInfoTask = festivalInfoTask;
    }

    @Bean(name = "festivalInfoJob")
    public Job festivalInfoJob(JobRepository jobRepository, Step festivalInfoStep) {
        return new JobBuilder("festivalIntegrateJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(festivalInfoStep)
                .build();
    }

    @Bean
    public Step festivalInfoStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("festivalInfoStep", jobRepository)
                .tasklet(festivalInfoTasklet(), tx)
                .build();
    }

    public Tasklet festivalInfoTasklet() {
        return ((contribution, chunkContext) -> festivalInfoTask.FestivalInfoIntegrateTask());
    }
}
