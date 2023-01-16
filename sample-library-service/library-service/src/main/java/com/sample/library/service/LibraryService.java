package com.sample.library.service;

import com.sample.library.datatransferobject.Book;
import com.sample.library.datatransferobject.LoginRequest;
import com.sample.library.datatransferobject.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Esmaeil NikFekr on 5/25/21.
 */
@Service
public interface LibraryService {

    LoginResponse login(LoginRequest loginRequest);

    List<Book> getBooks();

    Book getBook(String bookName);

//    List<PersonInfoResult> getPersonInquiry(PersonInfoRequest personInfo);
//
//    ImageResult getPersonImage(ImageRequest imageRequest);
}
