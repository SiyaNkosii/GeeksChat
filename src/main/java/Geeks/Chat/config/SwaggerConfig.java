
package Geeks.Chat.config;

import Geeks.Chat.utils.ApiDocConstantUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT = new Contact("", ApiDocConstantUtil.URL, ApiDocConstantUtil.EMAIL);

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("geeks.chat"))
                .build()
                .apiInfo(metaData())
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json"));
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                ApiDocConstantUtil.TITLE,
                ApiDocConstantUtil.DESCRIPTION,
                ApiDocConstantUtil.VERSION,
                ApiDocConstantUtil.TERMS_OF_SERVICE,
                DEFAULT_CONTACT,
                ApiDocConstantUtil.LICENSE,
                ApiDocConstantUtil.LICENSE_URL,
                new ArrayList<>());
        return apiInfo;
    }
}
