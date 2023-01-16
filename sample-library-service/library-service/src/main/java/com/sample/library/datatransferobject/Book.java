package com.sample.library.datatransferobject;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Esmaeil NikFekr on 18.12.22
 */
@Data
@AllArgsConstructor
public class Book {

    String bookName;
    String bookGenre;
    String author;
}
