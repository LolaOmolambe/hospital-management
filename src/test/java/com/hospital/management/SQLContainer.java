package com.hospital.management;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = SQLContainer.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public abstract class SQLContainer {
    public static MySQLContainer<?> mySqlDB = new MySQLContainer<>
            ("mysql:latest")
            .withDatabaseName("test")
            .withUsername("root")
            .withPassword("password")
            .withReuse(true);

    static {
        mySqlDB.start();
    }

    public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.username=" + mySqlDB.getUsername(),
                    "spring.datasource.password=" + mySqlDB.getPassword()
            );
        }

    }
}
