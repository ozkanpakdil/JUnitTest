import com.github.cloudyrock.mongock.driver.mongodb.sync.v4.driver.MongoSync4Driver;
import com.github.cloudyrock.standalone.MongockStandalone;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import config.Configuration;
import db.migration.DbChangeLog001;

public class main {
    public static void main(String[] args) {
        MongockStandalone();
    }

    private static void MongockStandalone(){
        var mongoUri =  Configuration.getMongoUri();
        MongoClient mongoClient = MongoClients.create("mongodb://127.0.1:27017/");
        MongockStandalone.builder()
                .setDriver(MongoSync4Driver.withDefaultLock(mongoClient.getDatabase("FeteBird-Product")))
                         .addChangeLogsScanPackage("db.migration")
                        .buildRunner().execute();
    }
}
