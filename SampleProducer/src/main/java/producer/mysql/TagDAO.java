package producer.mysql;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import producer.common.DaoException;
import producer.common.IDao;


/**
 * Created by kunal.gupta on 4/4/14.
 */
public class TagDAO implements IDao{


    @Override
    public TagDO read(String message) throws DaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            TagDO tag = (TagDO)session.get(TagDO.class, message);
            transaction.commit();
            return tag;
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            throw new DaoException();
        } finally {
            session.close();
        }
    }

    @Override
    public int write(TagDO message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
            return 1;
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            throw new DaoException();
        } finally {
            session.close();
        }

    }

    @Override
    public int update(TagDO tag) throws DaoException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(tag);
            transaction.commit();
            return 1;
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            throw new DaoException();
        } finally {
            session.close();
        }


    }
}
