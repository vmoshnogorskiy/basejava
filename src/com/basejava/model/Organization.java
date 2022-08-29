package com.basejava.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final List<Property> properties;

    public Organization(String name, String url, Property... properties) {
        this(new Link(name, url), Arrays.asList(properties));
    }

    public Organization(Link homePage, List<Property> properties) {
        this.homePage = homePage;
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + properties.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", properties=" + properties +
                '}';
    }

    public static class Property {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String description;
        private final String title;

        public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

        public Property(LocalDate startDate, String description, String title) {
            this(startDate, NOW, title, description);
        }

        public Property(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Property property = (Property) o;

            if (!startDate.equals(property.startDate)) return false;
            if (!endDate.equals(property.endDate)) return false;
            if (!Objects.equals(description, property.description))
                return false;
            return title.equals(property.title);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + title.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Property{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
