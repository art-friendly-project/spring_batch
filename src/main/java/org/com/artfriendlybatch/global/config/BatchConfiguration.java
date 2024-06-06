package org.com.artfriendlybatch.global.config;

import org.com.artfriendlybatch.global.batch.task.Task;
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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager") // 해당 설정이 들어가면 Spring Boot는 이를 사용자가 커스텀 설정을 제공한 것으로 간주하고, 일부 자동 설정을 비활성화힌디
public class BatchConfiguration {
    private final Task task;

    public BatchConfiguration(Task task) {
        this.task = task;
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(5);
        return asyncTaskExecutor;
    }

    @Bean
    public Job webCrawlingJob(JobRepository jobRepository, Step crawlingStep) {
        return new JobBuilder("webCrawlingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(crawlingStep)
                .build();
    }

    // 다중 db를 이용할 때는 tx를 직접 지정해줘야한다.
    @Bean
    public Step crawlingStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("exhibitionInfoCrawlingStep", jobRepository)
                .tasklet(exhibitionInfoCrawlingTasklet(), tx)
                .build();
    }

    public Tasklet exhibitionInfoCrawlingTasklet() {
        return ((contribution, chunkContext) -> {
            return task.exhibitionInfoCrawling();
        });
    }
}
