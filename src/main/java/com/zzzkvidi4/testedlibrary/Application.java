package com.zzzkvidi4.testedlibrary;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

/**
 * Main entry point of application.
 */
public final class Application {
    @NotNull
    private static final String FILE_OPTION_NAME = "f";
    @NotNull
    private static final String CAPACITY_OPTION_NAME = "c";

    /**
     * Main method of application.
     *
     * @param args - command line arguments
     */
    public static void main(@NotNull String[] args) {
        Options options = new Options()
                .addRequiredOption(FILE_OPTION_NAME, "file", true, "file with books")
                .addRequiredOption(CAPACITY_OPTION_NAME, "capacity", true, "capacity of library");
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("Usage: <executableName> ", options);
            return;
        }
        String fileName = getFileName(commandLine);
        int capacity = getCapacity(commandLine);
        Injector injector = Guice.createInjector(new LibraryModule(fileName));
        Library library = injector.getInstance(LibraryFactory.class).library(capacity);
        library.printAllBooks();
    }

    /**
     * Method to get and check file name parameter.
     *
     * @param cmd - command line
     * @return    - file name
     */
    @NotNull
    private static String getFileName(@NotNull CommandLine cmd) {
        String fileName = cmd.getOptionValue(FILE_OPTION_NAME).trim();
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("File name is empty!");
        }
        return fileName;
    }

    /**
     * Method to get and check capacity parameter.
     *
     * @param cmd - command line
     * @return    - capacity
     */
    private static int getCapacity(@NotNull CommandLine cmd) {
        String capacityOption = cmd.getOptionValue(CAPACITY_OPTION_NAME);
        int capacity;
        try {
            capacity = Integer.parseInt(capacityOption);
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity should be positive!");
            }
            return capacity;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Capacity should be numeric!");
        }
    }
}
