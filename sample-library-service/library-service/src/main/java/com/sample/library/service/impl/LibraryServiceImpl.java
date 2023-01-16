package com.sample.library.service.impl;

import com.sample.library.authproxy.service.AuthService;
import com.sample.library.datatransferobject.Book;
import com.sample.library.datatransferobject.LoginRequest;
import com.sample.library.datatransferobject.LoginResponse;
import com.sample.library.exception.BookNotFoundExceprtion;
import com.sample.library.mapper.LoginRequestMapper;
import com.sample.library.mapper.LoginResponseMapper;
import com.sample.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Esmaeil NikFekr on 3/28/21.
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    private final AuthService authService;
    private final LoginRequestMapper loginRequestMapper;

    private final LoginResponseMapper loginResponseMapper;

    private List<Book> books;


    @Autowired
    public LibraryServiceImpl(AuthService authService,
                              LoginRequestMapper loginRequestMapper,
                              LoginResponseMapper loginResponseMapper) {
        this.authService = authService;
        this.loginRequestMapper = loginRequestMapper;
        this.loginResponseMapper = loginResponseMapper;
    }


    public LoginResponse login(LoginRequest loginRequest) {

        return loginResponseMapper.map(
                authService.login(loginRequestMapper.map(loginRequest)));
    }


    @PostConstruct
    public void initializeBooks() {
        books = List.of(new Book("Effective Java", "Software", "Joshua Bloch"),
                new Book("Clean Code", "Software", "Robert c.Martin"),
                new Book("Refactoring", "Software", "Martin Fowler"));
    }


    @Override
    public List<Book> getBooks() {
        return books;
    }


    @Override
    public Book getBook(String bookName) {
        return books.stream().
                filter(book -> book.getBookName().contains(bookName))
                .findAny()
                .orElseThrow(() -> new BookNotFoundExceprtion("Book not found."));
    }

}
