package fete.bird.fetebirdproduct.configuration.configfile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring")
public class ApplicationYmlConfiguration {
    private KafkaConfigurationYml kafka;

    public KafkaConfigurationYml getKafka() {
        return kafka;
    }

    public void setKafka(KafkaConfigurationYml kafka) {
        this.kafka = kafka;
    }
}
