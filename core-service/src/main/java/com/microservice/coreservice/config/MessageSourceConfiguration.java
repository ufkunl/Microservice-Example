package com.microservice.coreservice.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MessageSourceConfiguration implements WebMvcConfigurer {
    @Bean
    public MessageSource messageSource() {
        MultipleMessageSource messageSource = new MultipleMessageSource();
        messageSource.setBasenames("classpath*:messages/messages");
        return messageSource;
    }
}
