package com.example.schedulefundtransfer.config;//package com.example.usermodule.config.swaggerConfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("SCHEDULE FUND TRANSFER")
                                .version("1.0")
                                .description("A backend service to enable customers to schedule simple fund\n" +
                                        "transfers."));
        }

}
