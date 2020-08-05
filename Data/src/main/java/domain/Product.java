package domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("Product")
public class Product {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("price")
    private float price;
    @Field("description")
    private String description;

    public Product(String name, float price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
