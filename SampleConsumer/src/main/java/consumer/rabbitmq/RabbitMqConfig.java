package consumer.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public class RabbitMqConfig {

    private String name;
    private String host;
    private int port;

    private Connection connection;
    private Channel channel;
    private QueueingConsumer consumer;

    public RabbitMqConfig(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
        init();
    }

    private void init() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(name,false,false,false,null);
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(name,true,getConsumer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close(){
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueueingConsumer getConsumer() {
        return consumer;
    }
}
