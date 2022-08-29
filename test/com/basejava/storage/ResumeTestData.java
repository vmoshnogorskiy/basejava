package com.basejava.storage;

import com.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.basejava.model.ContactType.*;
import static com.basejava.model.SectionType.*;

public class ResumeTestData {

    protected static Resume getTestResume(String uuid, String fullName) {
        Resume r = new Resume(uuid, fullName);

        r.setContact(PHONE, "+79xxxxxxxxx");
        r.setContact(EMAIL, "test@testmail.com");
        r.setContact(SKYPE, "test_skype");
        r.setContact(PROFILE_GITHUB, "test_profile_github");
        r.setContact(HOMEPAGE, "www.test_homepage.com");

        r.setSection(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по" +
                        " Java Web и Enterprise технологиям"
                )
        );
        r.setSection(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры."));

        List<String> achievementList = new ArrayList<>();
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike");
        achievementList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM");
        r.setSection(ACHIEVEMENT, new ListSection(achievementList));

        List<String> qualificationList = new ArrayList<>();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("DB: PostgreSQL(наследование, plpgsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        r.setSection(QUALIFICATION, new ListSection(qualificationList));

        List<Organization> experienceList = new ArrayList<>();
        experienceList.add(new Organization("Java Online Projects",
                        "http://javaops.ru/",
                        LocalDate.of(2013, 10, 1),
                        Organization.Property.NOW,
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."
                )
        );
        experienceList.add(new Organization("Wrike",
                        "https://www.wrike.com/",
                        LocalDate.of(2014, 10, 1),
                        LocalDate.of(2016, 1, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                                " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."
                )
        );
        experienceList.add(new Organization("RIT Center",
                        null,
                        LocalDate.of(2012, 4, 1),
                        LocalDate.of(2014, 10, 1),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: " +
                                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
                                "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO." +
                                " Архитектура БД и серверной части системы. Разработка интергационных сервисов:" +
                                " CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                                "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online " +
                                "редактирование из браузера документов MS Office. Maven + plugin development, Ant, " +
                                "Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, " +
                                "Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"
                )
        );
        r.setSection(EXPERIENCE, new OrganizationSection(experienceList));

        List<Organization> educationList = new ArrayList<>();
        Organization university = new Organization("Санкт-Петербургский национальный исследовательский университет" +
                " информационных технологий, механики и оптики",
                "https://itmo.ru/ru/",
                LocalDate.of(1993, 9, 1),
                LocalDate.of(1996, 7, 1),
                "Аспирантура (программист С, С++)",
                null
        );
        university.addProperty(LocalDate.of(1987, 9, 1),
                LocalDate.of(1993, 7, 1),
                "Инженер (программист Fortran, C)",
                null);
        educationList.add(university);

        Organization school = new Organization(
                "Заочная физико-техническая школа при МФТИ",
                "https://school.mipt.ru/",
                LocalDate.of(1984, 9, 1),
                LocalDate.of(1987, 6, 1),
                "Закончил с отличием",
                null
        );
        educationList.add(school);
        r.setSection(EDUCATION, new OrganizationSection(educationList));

        return r;
    }
}
