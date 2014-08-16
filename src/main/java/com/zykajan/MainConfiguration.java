package com.zykajan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.zykajan")
public class MainConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(MainConfiguration.class, args);
    }
}
