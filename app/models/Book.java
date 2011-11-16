package models;

import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.GenericGenerator;
import play.data.validation.Required;
import play.db.jpa.GenericModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
 
@Entity
public class Book extends GenericModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Field
    public String id;

    @Required
    @Field
    public String title;
    
    @Required
    @Field
    public String author;
    
    @Required
    @Field
    public String isbn;
    
    @Required
    @Field
    public String publisher;



    public void User(String title, String author, String isbn, String publisher) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        return sb.append("\n").append("id: ").append(id).append("\n").append("Title: ").append(title).append("\n")
                .append("Author: ").append(author).append("\n")
                .append("Publisher: ").append(publisher).append("\n")
                .append("ISBN: ").append(isbn).toString();
    }
}