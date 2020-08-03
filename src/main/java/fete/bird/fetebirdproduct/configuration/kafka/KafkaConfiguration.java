package fete.bird.fetebirdproduct.configuration.kafka;
import domain.Product;
import fete.bird.fetebirdproduct.common.ProductTopicConstants;
import fete.bird.fetebirdproduct.configuration.configfile.ApplicationYmlConfiguration;
import fete.bird.fetebirdproduct.viewmodels.ProductViewModel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.BatchLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class KafkaConfiguration {
    private ApplicationYmlConfiguration bootstrapAddress;

    public KafkaConfiguration(ApplicationYmlConfiguration bootstrapAddress) {
        this.bootstrapAddress = bootstrapAddress;
    }

    //region Kafka configuration
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapAddress.getKafka().getBootstrapAddress());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyer(ProducerFactory<String, Object> pf,
                                                                             ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {

        containerFactory.setReplyTemplate(kafkaTemplate(pf));
        ConcurrentMessageListenerContainer<String, Object> container = replyContainer(containerFactory);
        ReplyingKafkaTemplate<String, Object, Object> replyer = new ReplyingKafkaTemplate<>(pf, container);
        return replyer;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, Object> replyContainer(
            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {

        ConcurrentMessageListenerContainer<String, Object> container =
                containerFactory.createContainer(ProductTopicConstants.LISTNER_CONTAINER);
        container.getContainerProperties().setGroupId(ProductTopicConstants.LISTNER_CONTAINER);
        container.setBatchErrorHandler(new BatchLoggingErrorHandler());
        return container;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> pf) {
        return new KafkaTemplate<>(pf);
    }
    //endregion


    //region Kafka Template for consumer factory  get All Products
    @Bean
    public ConsumerFactory<String, List<Product>> consumerFactoryGetAllProducts() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(Object.class));
    }
    @Bean
    public KafkaListenerContainerFactory<?> getAllProductsContainerFactory(ProducerFactory<String, Object> pf) {
        ConcurrentKafkaListenerContainerFactory<String, List<Product>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryGetAllProducts());
        factory.setReplyTemplate(kafkaTemplate(pf));
        return factory;
    }
    //endregion

    //region Kafka Template for consumer factory  get Product
    @Bean
    public ConsumerFactory<String, String> consumerFactoryGetDeleteProduct() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(String.class));
    }

    @Bean
    public KafkaListenerContainerFactory<?> getDeleteProductContainerFactory(ProducerFactory<String, Object> pf) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryGetDeleteProduct());
        factory.setReplyTemplate(kafkaTemplate(pf));
        return factory;
    }

    //endregion

    //region    Kafka Template for consumer factory  Add

    @Bean
    public ConsumerFactory<String, ProductViewModel> consumerFactoryAddUpdateProduct() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProductViewModel.class));
    }

    @Bean
    public KafkaListenerContainerFactory<?> addUpdateProductContainerFactory(ProducerFactory<String, Object> pf) {
        ConcurrentKafkaListenerContainerFactory<String, ProductViewModel> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryAddUpdateProduct());
        factory.setReplyTemplate(kafkaTemplate(pf));
        return factory;
    }
    //endregion

}
