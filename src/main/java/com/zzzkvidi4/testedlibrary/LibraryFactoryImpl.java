package com.zzzkvidi4.testedlibrary;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * Implementation of {@link LibraryFactory}.
 */
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class LibraryFactoryImpl implements LibraryFactory {
    @NotNull
    private final BookFactory bookFactory;

    /**
     * Method to create library.
     *
     * @param capacity - library capacity
     * @return         - new library
     */
    @NotNull
    @Override
    public Library library(int capacity) {
        return new Library(capacity, bookFactory);
    }
}
