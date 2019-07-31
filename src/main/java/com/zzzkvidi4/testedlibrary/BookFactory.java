package com.zzzkvidi4.testedlibrary;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Factory to create books.
 */
public interface BookFactory {
    /**
     * Method to get books.
     *
     * @return - collection of books
     */
    @NotNull
    Collection<Book> books();
}
