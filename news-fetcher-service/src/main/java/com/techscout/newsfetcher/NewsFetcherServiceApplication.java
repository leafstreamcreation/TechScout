package com.techscout.newsfetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
public class NewsFetcherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsFetcherServiceApplication.class, args);
    }

}
