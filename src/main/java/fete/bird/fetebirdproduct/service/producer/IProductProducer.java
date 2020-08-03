package fete.bird.fetebirdproduct.service.producer;

import fete.bird.fetebirdproduct.viewmodels.ProductViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface IProductProducer {
    ProductViewModel GetProduct(String id) throws InterruptedException, ExecutionException, TimeoutException;
    List<ProductViewModel> GetProducts() throws InterruptedException, ExecutionException, TimeoutException;
    void AddProduct(ProductViewModel product);
    void UpdateProduct(ProductViewModel product, String id);
    void DeleteProduct(String id);
}
