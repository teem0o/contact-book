package com.dga.contactbook.exception;

public class ContactInfoPermissionException extends RuntimeException{
    public ContactInfoPermissionException(String actionType,Long id) {
        super("You are not authorized to"+actionType+ "this contact info. With id: " + id);
    }
}
