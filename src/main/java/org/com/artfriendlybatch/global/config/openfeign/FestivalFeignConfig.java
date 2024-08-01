package org.com.artfriendlybatch.global.config.openfeign;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FestivalFeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // 요청 보낼 때 포함되는 헤더 설정
//    @Bean
//    public RequestInterceptor festivalRequestInterceptor() {
//        return requestTemplate -> {
//
//        }
//    }
}
