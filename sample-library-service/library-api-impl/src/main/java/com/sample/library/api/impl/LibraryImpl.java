package com.sample.library.api.impl;

import com.sample.library.api.consumerone.ConsumerOneApi;
import com.sample.library.api.consumerone.datatransferobject.Book;
import com.sample.library.api.consumerone.datatransferobject.LoginRequest;
import com.sample.library.api.consumerone.datatransferobject.LoginResponse;
import com.sample.library.api.mapper.BookMapper;
import com.sample.library.api.mapper.LoginRequestMapper;
import com.sample.library.api.mapper.LoginResponseMapper;
import com.sample.library.service.LibraryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Esmaeil NikFekr on 3/28/21.
 */
@Tag(name = "Consumer-one API")
@RestController
public class LibraryImpl implements ConsumerOneApi {

    private final LibraryService libraryService;

    private final LoginResponseMapper loginResponseMapper;

    private final LoginRequestMapper loginRequestMapper;

    private final BookMapper bookMapper;


    @Autowired
    public LibraryImpl(LibraryService libraryService,
                       LoginResponseMapper loginResponseMapper,
                       LoginRequestMapper loginRequestMapper,
                       BookMapper bookMapper) {

        this.libraryService = libraryService;
        this.loginResponseMapper = loginResponseMapper;
        this.loginRequestMapper = loginRequestMapper;
        this.bookMapper = bookMapper;
    }


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return loginResponseMapper.map(
                libraryService.login(
                        loginRequestMapper.map(loginRequest)
                )
        );
    }


    @Override
    @SecurityRequirement(name = "security_auth")
    @RolesAllowed({"ADMIN", "library-user"})
    @PostMapping(value = "/library/books", produces = MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return bookMapper.mapList(libraryService.getBooks());
    }


    @Override
    @SecurityRequirement(name = "security_auth")
    @RolesAllowed({"ADMIN", "library-user"})
    @PostMapping(value = "/library/book/{bookName}", produces = MediaType.APPLICATION_JSON)
    public Book deliverBook(@PathVariable("bookName") String bookName) {
        return bookMapper.map(libraryService.getBook(bookName));
    }
}
