package com.example.monitoring.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.MDCScopeManager;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;

@Configuration
public class JaegerConfig {

    @Bean
    public Tracer initTracer() {

        MDCScopeManager scopeManager = new MDCScopeManager.Builder().build();

        JaegerTracer tracer = new JaegerTracer.Builder("spring-main")
                                .withScopeManager(scopeManager)
                                .withSampler(new ConstSampler(true))
                                .build();

        return tracer;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder.build();
    }

}
