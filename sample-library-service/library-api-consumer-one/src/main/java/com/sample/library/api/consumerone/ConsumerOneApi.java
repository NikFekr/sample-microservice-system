package com.sample.library.api.consumerone;

import com.sample.library.api.consumerone.datatransferobject.*;

import java.util.List;

/**
 * @author Esmaeil NikFekr on 3/27/21.
 */
public interface ConsumerOneApi {

    LoginResponse login(LoginRequest loginRequest);

    List<Book> getBooks();

    Book deliverBook(String bookId);
}
