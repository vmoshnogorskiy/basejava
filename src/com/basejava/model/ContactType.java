package com.basejava.model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Электронная почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    PROFILE_GITHUB("Профиль GitHub"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ":" + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
