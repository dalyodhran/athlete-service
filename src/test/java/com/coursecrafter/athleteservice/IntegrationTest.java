package com.coursecrafter.athleteservice;

import com.coursecrafter.athleteservice.config.AsyncSyncConfiguration;
import com.coursecrafter.athleteservice.config.EmbeddedKafka;
import com.coursecrafter.athleteservice.config.EmbeddedMongo;
import com.coursecrafter.athleteservice.config.EmbeddedRedis;
import com.coursecrafter.athleteservice.config.JacksonConfiguration;
import com.coursecrafter.athleteservice.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = { AthleteServiceApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class }
)
@EmbeddedRedis
@EmbeddedMongo
@EmbeddedKafka
public @interface IntegrationTest {
}
