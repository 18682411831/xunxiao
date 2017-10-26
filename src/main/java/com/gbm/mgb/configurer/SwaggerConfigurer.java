package com.gbm.mgb.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;


/**
 * Created by Waylon on 2017/9/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)

          .select()
          .apis(RequestHandlerSelectors.basePackage("com.gbm.mgb.web"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo())
          .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("萌哥吧Swagger2 Restful API接口")
                .description("")
                .termsOfServiceUrl("http://www.google.com/")
                .contact("mgb团队")
                .version("1.0")
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey("authkey", "x-authorization", "header");
    }

}
