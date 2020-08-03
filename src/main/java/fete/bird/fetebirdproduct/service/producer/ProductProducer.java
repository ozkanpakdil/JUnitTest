package fete.bird.fetebirdproduct.service.producer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Product;
import fete.bird.fetebirdproduct.common.ProductTopicConstants;
import fete.bird.fetebirdproduct.viewmodels.ProductViewModel;
import net.minidev.json.JSONObject;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public record ProductProducer(ReplyingKafkaTemplate<String, Object, Object> _replyTemplate,
                              ObjectMapper mapper) implements IProductProducer {
    private static final Logger LOG = LoggerFactory.getLogger(ProductProducer.class);
    @Override
    public ProductViewModel GetProduct(String id) throws InterruptedException, ExecutionException, TimeoutException {
        RequestReplyFuture<String, Object, Object> future =
                this._replyTemplate.sendAndReceive(new ProducerRecord<>(ProductTopicConstants.GET_PRODUCT, 0, null,id));
        LOG.info(future.getSendFuture().get(10, TimeUnit.SECONDS).getRecordMetadata().toString());
        var products = (Product)future.get(10, TimeUnit.SECONDS).value();
        var mappedProducts = mapper.convertValue(products, new TypeReference<Product>() { });
        return new ProductViewModel(mappedProducts.getId(), mappedProducts.getName(), mappedProducts.getPrice(), mappedProducts.getDescription());
    }

    @Override
    public List<ProductViewModel> GetProducts() throws InterruptedException, ExecutionException, TimeoutException {
        RequestReplyFuture<String, Object, Object> future =
                this._replyTemplate.sendAndReceive(new ProducerRecord<>(ProductTopicConstants.GET_PRODUCTS, 0, null, null));
        LOG.info(future.getSendFuture().get(10, TimeUnit.SECONDS).getRecordMetadata().toString());
        var products = (ArrayList<Product>)future.get(10, TimeUnit.SECONDS).value();
        var mappedProducts = mapper.convertValue(products, new TypeReference<List<Product>>() { });
        var productViewModels = mappedProducts.stream().map(item -> new ProductViewModel(item.getId(),item.getName(),item.getPrice(), item.getDescription())).collect(Collectors.toList());
        return productViewModels;
    }

    @Override
    public void AddProduct(ProductViewModel product) {
        this._replyTemplate.send(ProductTopicConstants.ADD_PRODUCT, product);
    }

    @Override
    public void UpdateProduct(ProductViewModel product, String id) {
        this._replyTemplate.send(ProductTopicConstants.UPDATE_PRODUCT, product);
    }

    @Override
    public void DeleteProduct(String id) {
        this._replyTemplate.send(ProductTopicConstants.UPDATE_PRODUCT, id);
    }
}
