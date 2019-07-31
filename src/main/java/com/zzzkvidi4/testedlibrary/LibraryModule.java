package com.zzzkvidi4.testedlibrary;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Module for application.
 */
@RequiredArgsConstructor
public final class LibraryModule extends AbstractModule {
    @NotNull
    private final String booksFileName;

    /**
     * Method to configure dependencies.
     */
    @Override
    protected void configure() {
        bind(BookFactory.class).toProvider(() -> new FileBookFactory(booksFileName));
        bind(LibraryFactory.class).to(LibraryFactoryImpl.class);
    }
}
