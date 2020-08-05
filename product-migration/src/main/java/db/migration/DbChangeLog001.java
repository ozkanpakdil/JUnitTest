package db.migration;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@ChangeLog(order = "001")
public class DbChangeLog001 {
    @ChangeSet(order = "001", id = "seedProduct", author = "San")
    public void seedProduct(MongoDatabase mongoDatabase) {
        mongoDatabase.createCollection("Product");
    }
}
