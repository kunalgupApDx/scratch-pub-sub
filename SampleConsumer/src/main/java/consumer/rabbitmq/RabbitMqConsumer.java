package consumer.rabbitmq;

import com.rabbitmq.client.QueueingConsumer;
import consumer.common.IConsumer;
import consumer.common.QueueException;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public class RabbitMqConsumer implements IConsumer {

    private RabbitMqConfig config;


    public RabbitMqConsumer(RabbitMqConfig config) {
        this.config = config;
    }

    @Override
    public String consume() throws QueueException{
        try {
            QueueingConsumer.Delivery delivery = config.getConsumer().nextDelivery();
            assert (delivery != null);
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            return message;
        }
        catch(InterruptedException ie){
            throw new QueueException();
        }
    }
}
