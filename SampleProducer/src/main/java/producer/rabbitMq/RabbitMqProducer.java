package producer.rabbitMq;

import producer.common.IProducer;
import producer.common.QueueException;

import java.io.IOException;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public class RabbitMqProducer implements IProducer{

    private RabbitMqConfig config;


    public RabbitMqProducer(RabbitMqConfig config) {
        this.config = config;
    }

    @Override
    public int produce(String message) throws QueueException {
        try {
            config.getChannel().basicPublish("", config.getName(), null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            config.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new QueueException();
        }
        return 1;
    }
}
