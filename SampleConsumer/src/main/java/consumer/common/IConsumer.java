package consumer.common;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public interface IConsumer {

    String consume() throws QueueException;

}
