package producer.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

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
            channel.queueDeclare(getName(), false, false, false, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException{
        channel.close();
        connection.close();
    }

    public Channel getChannel() {
        return channel;
    }

    public String getName() {
        return name;
    }
}
