package com.zzzkvidi4.testedlibrary;

import net.lamberto.junit.GuiceJUnitRunner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.regex.Pattern;

import static com.zzzkvidi4.testedlibrary.LibraryTestsModule.BOOKS_COUNT;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules(LibraryTestsModule.class)
public final class SecondPartLibraryTests {
    @Nullable
    @Inject
    private LibraryFactory libraryFactory;
    @Nullable
    @Inject
    private BookFactory bookFactory;

    @Test
    public void libraryReturnsTheSameBook() {
        Library library = libraryFactory.library(BOOKS_COUNT);
        Book[] books = bookFactory.books().toArray(new Book[0]);
        for (int i = 0; i < BOOKS_COUNT; ++i) {
            assertEquals(books[i], library.getBook(i + 1));
        }
    }

    @Test
    public void libraryAddsBookToTheFirstEmptyCell() {
        Library library = libraryFactory.library(BOOKS_COUNT + 5);
        Book bookToAdd = new Book("Порог", new Author("Сергей Лукьяненко"));
        library.addBook(bookToAdd);
        assertEquals(bookToAdd, library.getBook(BOOKS_COUNT + 1));
    }

    @Test(expected = IllegalStateException.class)
    public void libraryThrowsExceptionIfAddToFull() {
        Library library = libraryFactory.library(BOOKS_COUNT);
        Book bookToAdd = new Book("Порог", new Author("Сергей Лукьяненко"));
        library.addBook(bookToAdd);
    }

    @Test
    public void libraryPrintsContainingBooks() {
        PrintStream outputStream = System.out;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(byteStream);
        System.setOut(stream);
        libraryFactory.library(BOOKS_COUNT + 3).printAllBooks();
        String text = byteStream.toString();
        assertNotNull(text);
        assertTrue(!text.trim().isEmpty());
        text = text.trim();
        int count = 0;
        for (String part : Arrays.stream(text.split("\\n")).map(String::trim).filter(s -> !s.isEmpty()).collect(toList())) {
            assertTrue(Pattern.compile("[\\d]+:.*Book\\(.*\\)").matcher(part).matches());
            count++;
        }
        assertEquals(BOOKS_COUNT, count);
        System.setOut(outputStream);
    }
}
