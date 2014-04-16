package consumer;

import com.google.common.base.Strings;
import consumer.common.DaoException;
import consumer.common.IConsumer;
import consumer.common.IDao;
import consumer.common.QueueException;
import consumer.mongo.MongoConfig;
import consumer.mongo.MongoConstants;
import consumer.mongo.TagDAO;
import consumer.mongo.TagDO;
import consumer.rabbitmq.RabbitMqConfig;
import consumer.rabbitmq.RabbitMqConstants;
import consumer.rabbitmq.RabbitMqConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public class ConsumerJob {

    private final IConsumer consumer;
    private final IDao dao;
    public static final String FILEPATH = "big.txt";

    public ConsumerJob(IConsumer consumer, IDao dao) {
        this.consumer = consumer;
        this.dao = dao;
    }


    public static void main(String[] argv) {
        System.out.println("Starting SampleConsumer");
        RabbitMqConsumer consumer = new RabbitMqConsumer(new RabbitMqConfig(RabbitMqConstants.QUEUE_NAME, RabbitMqConstants.QUEUE_HOST, RabbitMqConstants.QUEUE_PORT));
        TagDAO tagDAO = new TagDAO(new MongoConfig(MongoConstants.MONGO_HOST, MongoConstants.MONGO_PORT, MongoConstants.MONGO_DB));
        ConsumerJob job = new ConsumerJob(consumer, tagDAO);

//        Scanner sc = null;
//        try {
//            sc = new Scanner(new File(ConsumerJob.class.getClassLoader().getResource(FILEPATH).getFile()));
//            while(sc.hasNextLine()){
//                String line = sc.nextLine();
//                String[] tokens = line.split(" ");
//                for(String token : tokens){
//                    token = token.replaceAll("[^a-zA-Z0-9\\s]","").replaceAll("\\s+","").toLowerCase();
//                    if(!Strings.isNullOrEmpty(token)) {
//                        job.work(token);
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        job.work();
        System.out.println("Ending SampleConsumer");
    }

    public int work() {
        while (true) {
            try {
                String message = consumer.consume();
                TagDO tagDO = dao.read(message);
                if(tagDO != null){
                    tagDO.setCount(tagDO.getCount() + 1);
                    dao.update(tagDO);
                }
                else{
                    tagDO = new TagDO();
                    tagDO.setName(message);
                    tagDO.setCount(1);
                    dao.write(tagDO);
                }
            } catch (QueueException e) {
                e.printStackTrace();
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
    }




    public int work(String message){
        try {
            TagDO tagDO = dao.read(message);
            if(tagDO != null){
                tagDO.setCount(tagDO.getCount() + 1);
                dao.update(tagDO);
            }
            else{
                tagDO = new TagDO();
                tagDO.setName(message);
                tagDO.setCount(1);
                dao.write(tagDO);
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
