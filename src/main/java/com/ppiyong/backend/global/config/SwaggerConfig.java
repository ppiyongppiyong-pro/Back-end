package com.ppiyong.backend.global.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("삐용삐용 API")
                .description("삐용삐용 백엔드 API 입니다.")
                .version("1.0");

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .servers(List.of(
                        new Server().url(serverUrl)
                ))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components().addSecuritySchemes(securitySchemeName,
                                new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(info);
    }
}
