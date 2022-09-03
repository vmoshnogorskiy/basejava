package com.basejava.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATION("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;
    private static final long serialVersionUID = 1L;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
