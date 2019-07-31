package com.zzzkvidi4.testedlibrary;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Class to work with books.
 */
public final class Library {
    @NotNull
    private static final String LOG_FORMAT = "%s: %s";
    @NotNull
    private final Book[] books;
    private final int capacity;
    private int freeSpace;

    /**
     * Constructor.
     *
     * @param capacity    - capacity of library
     * @param bookFactory - factory to fill library with books
     */
    public Library(int capacity, @NotNull BookFactory bookFactory) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity less than zero!");
        }
        this.books = new Book[capacity];
        this.capacity = capacity;
        Collection<Book> books = bookFactory.books();
        int actualBooksSize = books.size();
        if (actualBooksSize > capacity) {
            throw new IllegalStateException(
                    "Books in factory more than library capacity! " + capacity + " against " + actualBooksSize
            );
        }
        this.freeSpace = capacity - actualBooksSize;
        int i = 0;
        for (Book book : books) {
            this.books[i++] = book;
        }
    }

    /**
     * Method to get book by index.
     *
     * @param index - index of book
     * @return      - book if it is not null or exception will be thrown
     */
    @NotNull
    public Book getBook(int index) {
        if (index > capacity || index < 1) {
            throw new IndexOutOfBoundsException("Index is out of range!");
        }
        if (freeSpace == capacity) {
            throw new IllegalStateException("Library is empty!");
        }
        Book book = books[index - 1];
        if (book == null) {
            throw new IllegalArgumentException("No book with that index!");
        }
        printBook(index, book);
        return book;
    }

    /**
     * Method to add book into library.
     *
     * @param book - book to add
     */
    public void addBook(@NotNull Book book) {
        if (freeSpace == 0) {
            throw new IllegalStateException("Library is full!");
        }
        int index = 0;
        while (index < capacity && books[index] != null) {
            index++;
        }
        books[index] = book;
        freeSpace--;
    }

    /**
     * Method to print all books from library.
     */
    public void printAllBooks() {
        for (int i = 0; i < capacity; ++i) {
            Book book = books[i];
            if (book != null) {
                printBook(i + 1, book);
            }
        }
    }

    private void printBook(int index, @NotNull Book book) {
        System.out.println(String.format(LOG_FORMAT, index, book));
    }
}
