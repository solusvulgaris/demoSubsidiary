package com.ak.springbootdemo.sub.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring Boot Application Configuration
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages="com.ak.springbootdemo.seb")
public class ApplicationConfig {

}
