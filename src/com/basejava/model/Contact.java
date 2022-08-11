package com.basejava.model;

import java.util.HashMap;

public class Contact {

    protected final HashMap<ContactType, String> contacts = new HashMap<>();

    protected String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    protected void setContact(ContactType contactType, String describe) {
        contacts.put(contactType, describe);
    }

}
