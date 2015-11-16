package cz.ryvo.organizer.auth.config;

import com.google.common.collect.Lists;
import cz.ryvo.organizer.auth.converter.OAuth2AuthenticationReadConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "cz.ryvo.organizer.auth.repository")
public class RepositoryConfig {

    private static final Logger log = LoggerFactory.getLogger(RepositoryConfig.class);

    @Autowired
    private OAuth2AuthenticationReadConverter oAuth2AuthenticationReadConverter;

    @Bean
    public CustomConversions customConversions() {
        final List<Converter<?, ?>> converters = Lists.newArrayList(oAuth2AuthenticationReadConverter);
        log.info("Register converters: " + converters);
        return new CustomConversions(converters);
    }
}