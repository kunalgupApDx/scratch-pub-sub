package producer.common;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public interface IProducer {

    int produce(String message) throws QueueException;

}
