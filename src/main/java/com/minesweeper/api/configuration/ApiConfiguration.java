package com.minesweeper.api.configuration;


import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableAsync
@EnableMongoRepositories(basePackages = "com.minesweeper.api.repository")
public class ApiConfiguration implements WebMvcConfigurer {

  @Value("${spring.data.mongodb.uri}")
  private String mongoUri;

  @Bean
  public MongoDbFactory mongoDbFactory() {
    return new SimpleMongoClientDbFactory(this.mongoUri);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoDbFactory());
  }

  @Bean
  public ObjectMapper springMvcObjectMapper() {
    return new ObjectMapper()
        .setPropertyNamingStrategy(SNAKE_CASE)
        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setSerializationInclusion(NON_NULL)
        .configure(FAIL_ON_EMPTY_BEANS, false)
        .registerModule(new Jdk8Module());
  }
}
