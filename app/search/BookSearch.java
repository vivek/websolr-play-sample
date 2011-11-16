package search;

import models.Book;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import play.Logger;
import play.Play;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

public final class BookSearch{
    private static final BookSearch bookSearch= new BookSearch();
    private final SolrServer websolrServer;
    
    private BookSearch(){
        String websolrUrl = (String) Play.configuration.get("books.websolr.url");
        Logger.debug("Websolr URL: %s", websolrUrl);
        try {
            this.websolrServer = new CommonsHttpSolrServer(websolrUrl);
        } catch (MalformedURLException e) {
            Logger.fatal("books.websolr.url is malformed: %s", websolrUrl, e);
            throw new RuntimeException(e);
        }
    }
    
    public static BookSearch getInstance(){
        return bookSearch;
    }


    public void add(Book book) throws IOException, SolrServerException {
        websolrServer.addBean(book);
        websolrServer.commit();
    }

    public void add(List<Book> books) throws IOException, SolrServerException {
        websolrServer.addBeans(books);
        websolrServer.commit();
    }


    public void deleteAll() throws IOException, SolrServerException {
        websolrServer.deleteByQuery("*:*");
        websolrServer.commit();
    }


    public List<Book> search(String query){
        List<Book> books = null;
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery( "title:"+query+"*" );
        solrQuery.addSortField("title", SolrQuery.ORDER.asc);
        try {
            QueryResponse resp = websolrServer.query( solrQuery );
            books = resp.getBeans(Book.class);
        } catch (SolrServerException e) {
            Logger.error("Failed to execute query: %s", query, e);
        }
        if(books == null){
            books = Collections.EMPTY_LIST;
        }
        return books;
    }


    public static void main(String[] args) throws IOException, SolrServerException {
        BookSearch bs = BookSearch.getInstance();
        List<Book> books = bs.search("tit*");
        for(Book b:books){
            System.out.println(b);
        }
    }
    
}