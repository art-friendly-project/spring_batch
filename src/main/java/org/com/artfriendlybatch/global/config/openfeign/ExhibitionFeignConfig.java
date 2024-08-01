package org.com.artfriendlybatch.global.config.openfeign;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;

public class ExhibitionFeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }

    @Bean
    public feign.codec.Decoder feignDecoder(XmlMapper xmlMapper) {
        return new JacksonDecoder(xmlMapper);
    }
}
