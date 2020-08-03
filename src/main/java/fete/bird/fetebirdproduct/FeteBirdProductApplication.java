package fete.bird.fetebirdproduct;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FeteBirdProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeteBirdProductApplication.class, args);
    }
}
