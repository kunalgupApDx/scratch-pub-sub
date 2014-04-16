package producer.service;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by kunal.gupta on 4/7/14.
 */

@XmlRootElement
public class Tag {

    private String name;
    private int count;

    public Tag(){

    }
    public Tag(String name,int count){
        this.name = name;
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
