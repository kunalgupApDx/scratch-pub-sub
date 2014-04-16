package producer.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kunal.gupta on 4/4/14.
 */


@Entity
@Table(name="TAGS")
public class TagDO {

    private String name;
    private int count;

    @Id
    @Column(name="NAME",nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="COUNT")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
