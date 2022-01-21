package com.ractoc.cookbook.exception;

public class DuplicateEntryException extends Exception {

    public DuplicateEntryException(String message) {
        super("Entry with " + message + " already exists");
    }
}
