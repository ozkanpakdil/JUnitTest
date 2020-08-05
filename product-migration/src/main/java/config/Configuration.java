package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configuration {
    @Value("${spring.data.mongodb.uri}")
    private static String MongoUri;

    public static  String getMongoUri() {
        return MongoUri;
    }
}
