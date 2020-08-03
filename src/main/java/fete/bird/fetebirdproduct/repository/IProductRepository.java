package fete.bird.fetebirdproduct.repository;

import domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends MongoRepository<Product, String> {
    Product findByid(String id);
    @Override
    List<Product> findAll();
}
