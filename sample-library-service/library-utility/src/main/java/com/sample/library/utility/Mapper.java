package com.sample.library.utility;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Esmaeil NikFekr on 3/28/21.
 */
public abstract class Mapper<I, O> {

    public abstract O map(I in);

    public <X extends List<I>, Y extends List<O>> Y mapList(X inputList) {
        return (Y) inputList.stream().map(in -> map(in)).collect(Collectors.toList());
    }
}
