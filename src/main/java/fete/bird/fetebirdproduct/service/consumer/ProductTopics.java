package fete.bird.fetebirdproduct.service.consumer;

import fete.bird.fetebirdproduct.common.ProductTopicConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ProductTopics {
    //region Creating new Topics for Product in Kafka
    @Bean
    public static NewTopic GetProduct() {
        return new NewTopic(ProductTopicConstants.GET_PRODUCT, 1, (short) 1);
    }

    @Bean
    public NewTopic GetProducts() {
        return TopicBuilder.name(ProductTopicConstants.GET_PRODUCTS).partitions(1).replicas(1).build();
    }
    @Bean
    public NewTopic GetProductsContainer() {
        return TopicBuilder.name(ProductTopicConstants.LISTNER_CONTAINER).partitions(1).replicas(1).build();
    }

    @Bean
    public static NewTopic AddProduct() {
        return new NewTopic(ProductTopicConstants.ADD_PRODUCT, 1, (short) 1);
    }

    @Bean
    public static NewTopic UpdateProduct() {
        return new NewTopic(ProductTopicConstants.UPDATE_PRODUCT, 1, (short) 1);
    }

    @Bean
    public static NewTopic DeleteProduct() {
        return new NewTopic(ProductTopicConstants.DELETE_PRODUCT, 1, (short) 1);
    }
    //endregion
}
