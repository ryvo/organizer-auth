package cz.ryvo.organizer.auth.config;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .produces(ImmutableSet.of(MediaType.APPLICATION_JSON_VALUE))
                .consumes(ImmutableSet.of(MediaType.APPLICATION_JSON_VALUE))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/oauth/.*"))
                .build();
    }

    private Predicate<RequestHandler> organizerApis() {
        return not(withClassAnnotation(FrameworkEndpoint.class));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
          "Organizer authentication and authorization server",
          null,
          null,
          null,
          null,
          null,
          null
        );

        return apiInfo;
    }
}
