package com.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final String title;
    private final List<Property> properties = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homePage = new Link(name, url);
        this.title = title;
        this.properties.add(new Property(startDate, endDate, description));
    }

    private class Property {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String description;

        private Property(LocalDate startDate, LocalDate endDate, String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
        }
    }

    public void addProperty(LocalDate startDate, LocalDate endDate, String description) {
        this.properties.add(new Property(startDate, endDate, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        if (!title.equals(that.title)) return false;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + properties.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", title='" + title + '\'' +
                ", properties=" + properties +
                '}';
    }
}
