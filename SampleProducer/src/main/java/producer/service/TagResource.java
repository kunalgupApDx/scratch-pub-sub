package producer.service;

import producer.ProducerJob;
import producer.mysql.TagDAO;
import producer.rabbitMq.RabbitMqConfig;
import producer.rabbitMq.RabbitMqConstants;
import producer.rabbitMq.RabbitMqProducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by kunal.gupta on 4/7/14.
 */

@Path("/tag")
public class TagResource {

    private final ProducerJob job = new ProducerJob(
            new RabbitMqProducer(new RabbitMqConfig(RabbitMqConstants.QUEUE_NAME, RabbitMqConstants.QUEUE_HOST, RabbitMqConstants.QUEUE_PORT)),
            new TagDAO()
    );



    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Tag getTag(@QueryParam("name") String tag){
        Tag tagObj = job.work(tag);
        return tagObj;
    }

}
