package consumer.mongo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.net.UnknownHostException;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public class MongoConfig {

    private String host;
    private int port;
    private String db;

    private MongoClient mongoClient;
    private Morphia morphia;
    private Datastore datastore;

    public MongoConfig(String host, int port, String db) {
        this.host = host;
        this.port = port;
        this.db = db;
        init();
    }

    private void init() {
        try {
            mongoClient = new MongoClient(host,port);
            morphia = new Morphia();
            datastore = morphia.createDatastore(mongoClient,db);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Datastore getDatastore(){
        return datastore;
    }


}
