package com.zzzkvidi4.testedlibrary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of {@link BookFactory} to get books from file in json format.
 */
@RequiredArgsConstructor
public final class FileBookFactory implements BookFactory {
    @NotNull
    private static final Type listBooksType = new TypeToken<ArrayList<Book>>() {}.getType();
    @NotNull
    private final String fileName;

    /**
     * Method to get collection of books.
     *
     * @return - collection of books
     */
    @NotNull
    @Override
    public Collection<Book> books() {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listBooksType);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
