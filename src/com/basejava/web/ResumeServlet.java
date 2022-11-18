package com.basejava.web;

import com.basejava.Config;
import com.basejava.model.Resume;
import com.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        /*String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
         */
        final Storage storage = Config.getInstance().getSqlStorage();

        PrintWriter wr = response.getWriter();
        wr.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Таблица резюме</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<table border=\"1\">\n" +
                "  <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>Фамилия Имя Отчество</th>\n" +
                "  </tr>");
        for (Resume resume : storage.getAllSorted()) {
            wr.write("<tr>\n" +
                    "    <td>" + resume.getUuid() + "</td>\n" +
                    "    <td>" + resume.getFullName() + "</td>\n" +
                    " </tr>");
        }
        wr.write("\t</table>\n" +
                "</body>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
