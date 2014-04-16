package integration_test.consumer;

import com.google.common.base.Strings;
import consumer.ConsumerJob;
import consumer.mongo.MongoConfig;
import consumer.mongo.MongoConstants;
import consumer.mongo.TagDAO;
import consumer.rabbitmq.RabbitMqConfig;
import consumer.rabbitmq.RabbitMqConstants;
import consumer.rabbitmq.RabbitMqConsumer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by kunal.gupta on 4/9/14.
 */
public class ConsumerJobTest {

    public static final String FILEPATH = "/big.txt";

    ConsumerJob job = new ConsumerJob(
            new RabbitMqConsumer(new RabbitMqConfig(RabbitMqConstants.QUEUE_NAME, RabbitMqConstants.QUEUE_HOST, RabbitMqConstants.QUEUE_PORT)),
            new TagDAO(new MongoConfig(MongoConstants.MONGO_HOST, MongoConstants.MONGO_PORT, MongoConstants.MONGO_DB)));

    @Test(enabled = true)
    public void canUpdateDatabase() throws IOException {
        Scanner sc = new Scanner(new File(this.getClass().getResource(FILEPATH).getFile()));
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            for(String token : tokens){
                token = token.replaceAll("[^a-zA-Z0-9\\s]","").replaceAll("\\s+","").toLowerCase();
                if(!Strings.isNullOrEmpty(token)) {
                    job.work(token);

                }
            }
        }
        Assert.assertTrue(true);
    }

}
