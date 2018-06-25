package com.jic.tnw.web.api.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket simpleDiffServiceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("jic")
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.any())
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JicELearning")
                .description("建投在线教育平台-JicELearning(CI) http://xgit.jic.com/JIC/JicElearning")
                .termsOfServiceUrl("http://xgit.jic.com/JIC/JicElearning")
                .version("1.0")
                .build();
    }
}