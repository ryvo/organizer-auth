package cz.ryvo.organizer.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "cz.ryvo.organizer.auth.repository")
public class RepositoryConfig {
}
