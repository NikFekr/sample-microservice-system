package com.sample.library.api.consumerone.datatransferobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Esmaeil NikFekr on 06.09.22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    String bookName;
    String bookGenre;
    String author;

}
