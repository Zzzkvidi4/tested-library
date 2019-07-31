package com.zzzkvidi4.testedlibrary;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Pattern;

import static com.zzzkvidi4.testedlibrary.LibraryTestsModule.BOOKS_COUNT;
import static org.junit.Assert.*;

public final class FirstPartLibraryTests {
    @Nullable
    private LibraryFactory libraryFactory;

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new LibraryTestsModule());
        libraryFactory = injector.getInstance(LibraryFactory.class);
    }

    @Test(expected = IllegalStateException.class)
    public void libraryThrowsExceptionWhenFactoryReturnsMoreThanCapacity() {
        Library library = libraryFactory.library(BOOKS_COUNT - 2);
    }

    @Test
    public void booksOrderIsSameAsInBookFactory() {
        int libraryCapacity = BOOKS_COUNT + 3;
        BookFactory bookFactory = Mockito.mock(BookFactory.class);
        Mockito.when(bookFactory.books()).thenReturn(createBooks(1));
        Library library = new Library(libraryCapacity, bookFactory);
        Collection<Book> booksFromFactory = bookFactory.books();
        int index = 0;
        for (Book bookFromFactory : booksFromFactory) {
            Book bookFromLibrary = library.getBook(++index);
            assertEquals(bookFromFactory, bookFromLibrary);
        }
        for (int i = BOOKS_COUNT; i < libraryCapacity; ++i) {
            try {
                Book bookFromLibrary = library.getBook(i + 1);
                Assert.fail();
            } catch (IllegalArgumentException e) {
                //nothing to check
            }
        }
    }

    @Test
    public void afterGetBookInfoWrittenToConsole() {
        PrintStream outputStream = System.out;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(byteStream);
        System.setOut(stream);
        BookFactory mock = Mockito.spy(new FileBookFactory("some-file-name"));
        Mockito.doReturn(createBooks(2)).when(mock).books();
        new Library(BOOKS_COUNT + 3, mock).getBook(1);
        String text = byteStream.toString();
        assertNotNull(text);
        assertTrue(!text.trim().isEmpty());
        text = text.trim();
        assertTrue(Pattern.compile("[\\d]+:.*Book\\(.*\\)").matcher(text).matches());
        System.setOut(outputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void libraryThrowsExceptionIfTryGetAccessToEmpty() {
        libraryFactory.library(BOOKS_COUNT + 2).getBook(BOOKS_COUNT + 1);
    }

    @NotNull
    static List<Book> createBooks(int index) {
        List<Book> books = new ArrayList<>(BOOKS_COUNT);
        for (int i = 0; i < BOOKS_COUNT; ++i) {
            books.add(new Book(Integer.toString(BOOKS_COUNT * index + i), new Author(Integer.toString(BOOKS_COUNT * index + i))));
        }
        return books;
    }
}
