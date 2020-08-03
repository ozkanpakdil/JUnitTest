package fete.bird.fetebirdproduct.service.consumer;

import domain.Product;
import fete.bird.fetebirdproduct.common.ProductTopicConstants;
import fete.bird.fetebirdproduct.repository.IProductRepository;
import fete.bird.fetebirdproduct.viewmodels.ProductViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProductListener {
    private final Logger logger = LoggerFactory.getLogger(ProductListener.class);
    private final IProductRepository _productRepository;

    public ProductListener(IProductRepository iproductRepository) {
        this._productRepository = iproductRepository;
    }

    //region Kafka listener for Get/Update/Delete Operation
    @KafkaListener(id = ProductTopicConstants.GET_PRODUCT, topics = ProductTopicConstants.GET_PRODUCT,
            containerFactory = "getDeleteProductContainerFactory")
    @SendTo
    public Product GetProduct(String id) {
        return _productRepository.findByid(id);
    }

    @KafkaListener(id = ProductTopicConstants.GET_PRODUCTS, topics = ProductTopicConstants.GET_PRODUCTS,splitIterables = false,
            containerFactory = "getAllProductsContainerFactory")
    @SendTo
    public List<Product> GetProducts(@Payload(required = false) String payload) {
        return _productRepository.findAll();
    }

    @KafkaListener(id = ProductTopicConstants.ADD_PRODUCT, topics = ProductTopicConstants.ADD_PRODUCT,
            containerFactory = "addUpdateProductContainerFactory")
    public void AddProduct(ProductViewModel product) {
        _productRepository.save(new Product(product.name(), product.price(), product.description()));
    }

    @KafkaListener(id = ProductTopicConstants.UPDATE_PRODUCT, topics = ProductTopicConstants.UPDATE_PRODUCT,
            containerFactory = "addUpdateProductContainerFactory")
    public void UpdateProduct(ProductViewModel productViewModel, String id) {
        Product product = _productRepository.findByid(id);
        product.setName(productViewModel.name());
        product.setPrice(productViewModel.price());
        product.setDescription(productViewModel.description());
        _productRepository.save(product);
    }

    @KafkaListener(id = ProductTopicConstants.DELETE_PRODUCT, topics = ProductTopicConstants.DELETE_PRODUCT,
            containerFactory = "getDeleteProductContainerFactory")
    public void DeleteProduct(String id) {
        Product product = _productRepository.findByid(id);
        _productRepository.delete(product);
    }
    //endregion


    /*
     * Boot will autowire this into the container factory.
     */
    @Bean
    public SeekToCurrentErrorHandler errorHandler() {
        return new SeekToCurrentErrorHandler();
    }

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }
}
