package com.zzzkvidi4.testedlibrary;

import com.google.inject.AbstractModule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class LibraryTestsModule extends AbstractModule {
    static final int BOOKS_COUNT = 5;

    @Override
    protected void configure() {
        bind(BookFactory.class).toInstance(LibraryTestsModule::createBooks);
        bind(LibraryFactory.class).to(LibraryFactoryImpl.class);
    }

    @NotNull
    public static List<Book> createBooks() {
        List<Book> books = new ArrayList<>(BOOKS_COUNT);
        for (int i = 0; i < BOOKS_COUNT; ++i) {
            books.add(new Book(Integer.toString(i), new Author(Integer.toString(i))));
        }
        return books;
    }
}
