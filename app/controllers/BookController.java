package controllers;

import org.apache.solr.client.solrj.SolrServerException;
import play.*;
import play.Logger;
import play.data.validation.Error;
import play.db.jpa.JPA;
import play.mvc.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.*;

import models.*;
import play.db.jpa.Model.*;

import play.data.validation.*;
import search.BookSearch;

public class BookController extends Controller {

    public static void index() {
        List<Book> books = Book.findAll();
        Logger.info("Rendering books: %s",books.size());
        render(books);
    }

    public static void show(Long id) {
        Book book = Book.findById(id);
        render(book);
    }

    public static void add() {
        render();
    }

    public static void create(@Valid Book book) {
        if (validation.hasErrors()) {
            params.flash();      //add http parameters to the flash scope
            validation.keep();   // keep the errors for the next request
            for (Error error : validation.errors()) {
                System.out.println(error.message());
            }
            index();
        }
        book.save();

        try {
            BookSearch.getInstance().add(book);
            flash.success("Successfully added book", book.title);
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
            flash.error("Failed to add to search index.\nDetails:%s" + e.getMessage());
            JPA.setRollbackOnly();
        } catch (SolrServerException e) {
            Logger.error(e.getMessage(), e);
            flash.error("Failed to add to search index.\nDetails:%s" + e.getMessage());
            JPA.setRollbackOnly();
        }
        index();
    }

    public static void update() {

    }

    public static void delete() {

    }

    public static void resetIndex(){
        try {
            Book.deleteAll();
            BookSearch.getInstance().deleteAll();
        } catch (IOException e) {
            flash.error("Error resetting index");
            Logger.error(e.getMessage(),e);
            JPA.setRollbackOnly();
            index();
        } catch (SolrServerException e) {
            flash.error("Error resetting index");
            Logger.error(e.getMessage(),e);
            JPA.setRollbackOnly();
            index();
        }
        flash.success("Sucessfully reset the index");
        index();


    }


    public static void search() {
        render();
    }

    public static void searchResult(List<Book> books) {
        render(books);
    }

    public static void doSearch(String title) {
        System.out.println("Query: " + title);
        if (BookSearch.getInstance() == null) {
            flash.error("Solr server instance not found, check server log");
            search();
        }
        List<Book> books = BookSearch.getInstance().search(title);
        renderTemplate("BookController/searchResult.html", books);
    }
}