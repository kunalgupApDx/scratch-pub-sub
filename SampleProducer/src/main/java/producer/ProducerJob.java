package producer;

import producer.common.DaoException;
import producer.common.IDao;
import producer.common.IProducer;
import producer.common.QueueException;
import producer.mysql.TagDAO;
import producer.mysql.TagDO;
import producer.rabbitMq.RabbitMqConfig;
import producer.rabbitMq.RabbitMqConstants;
import producer.rabbitMq.RabbitMqProducer;
import producer.service.Tag;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public class ProducerJob {

    private final IProducer producer;
    private final IDao dao;

    public ProducerJob(IProducer producer, IDao dao) {
        this.producer = producer;
        this.dao = dao;
    }




    public Tag work(String message) {
        Tag tag;
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
            producer.produce(message);
            tag = new Tag(tagDO.getName(),tagDO.getCount());
            return tag;
        }catch (QueueException e) {
            e.printStackTrace();
        }catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] argv) {
        System.out.println("Starting SampleProducer");
        RabbitMqProducer producer = new RabbitMqProducer(new RabbitMqConfig(RabbitMqConstants.QUEUE_NAME, RabbitMqConstants.QUEUE_HOST, RabbitMqConstants.QUEUE_PORT));
        TagDAO tagDao = new TagDAO();
        ProducerJob job = new ProducerJob(producer, tagDao);
        String message = "Hello World";
        job.work(message);
        System.out.println("Ending SampleProducer");
    }

}
