package consumer.mongo;

import consumer.common.DaoException;
import consumer.common.IDao;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public class TagDAO implements IDao {

    public static final String NAME = "name";
    public static final String COUNT = "count";

    private MongoConfig config;

    public TagDAO(MongoConfig config) {
        this.config = config;
    }

    @Override
    public TagDO read(String message) throws DaoException {
        try{
            TagDO tag = config.getDatastore().find(TagDO.class).field(NAME).equal(message).get();
            return tag;
        }
        catch(Exception e){
            throw new DaoException();
        }
    }

    @Override
    public int write(TagDO tag) throws DaoException{
        try {
            config.getDatastore().save(tag);
            return 1;
        }
        catch(Exception e){
            throw new DaoException();
        }

    }

    @Override
    public int update(TagDO tag) throws DaoException {
        try {
            Datastore datastore = config.getDatastore();
            Query<TagDO> updateQuery = datastore.createQuery(TagDO.class).field(NAME).equal(tag.getName());
            UpdateOperations<TagDO> ops = datastore.createUpdateOperations(TagDO.class).set(COUNT, tag.getCount());
            datastore.update(updateQuery, ops);
            return 1;
        }
        catch(Exception e){
            throw new DaoException();
        }
    }



}
