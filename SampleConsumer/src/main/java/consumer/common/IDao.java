package consumer.common;

import consumer.mongo.TagDO;

/**
 * Created by kunal.gupta on 4/4/14.
 */
public interface IDao {

    TagDO read(String message) throws DaoException;

    int write(TagDO tag) throws DaoException;

    int update(TagDO tag) throws DaoException;


}
