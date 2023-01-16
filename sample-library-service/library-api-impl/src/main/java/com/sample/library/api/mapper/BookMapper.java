package com.sample.library.api.mapper;

import com.sample.library.datatransferobject.Book;
import com.sample.library.utility.Mapper;

/**
 * @author Esmaeil NikFekr on 18.12.22
 */
public class BookMapper extends Mapper<Book, com.sample.library.api.consumerone.datatransferobject.Book> {
    @Override
    public com.sample.library.api.consumerone.datatransferobject.Book map(Book in) {
        var book = new com.sample.library.api.consumerone.datatransferobject.Book();
        book.setBookName(in.getBookName());
        book.setAuthor(in.getAuthor());
        book.setBookGenre(in.getBookGenre());
        return book;
    }
}
