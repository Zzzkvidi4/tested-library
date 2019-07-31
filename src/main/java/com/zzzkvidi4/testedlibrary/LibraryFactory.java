package com.zzzkvidi4.testedlibrary;

import org.jetbrains.annotations.NotNull;

/**
 * Interface of library factory.
 */
public interface LibraryFactory {
    /**
     * Method to create library.
     *
     * @param capacity - library capacity
     * @return         - new library with specified capacity
     */
    @NotNull
    Library library(int capacity);
}
