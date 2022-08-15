package com.basejava.model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Электронная почта"),
    SKYPE("Skype"),
    PROFILE_GITHUB("Профиль GitHub"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
