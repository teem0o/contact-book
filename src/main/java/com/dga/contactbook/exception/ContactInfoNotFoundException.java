package com.dga.contactbook.exception;

public class ContactInfoNotFoundException extends RuntimeException {
    public ContactInfoNotFoundException(Long id) {
        super("Contact info with id '" + id + "' not found.");
    }
}
