package ru.job4j.cinema.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cinema.repository.Account;
import ru.job4j.cinema.repository.Place;
import ru.job4j.cinema.service.PlaceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class PayServlet extends HttpServlet {
private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        HttpSession sc = req.getSession();
        System.out.println("payServlet");
        Enumeration<String> e = sc.getAttributeNames();
        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }
        Place place = (Place) sc.getAttribute("place");
        int id = Integer.parseInt(place.getName());
        System.out.println(id);
        String json = GSON.toJson(PlaceService.placeForJson(id));
        System.out.println(json);
        OutputStream output = resp.getOutputStream();
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Account account = GSON.fromJson(req.getReader(), Account.class);
        System.out.println(account.getName());
        System.out.println(account.getEmail());
        System.out.println(account.getPhone());
        System.out.println(req.getParameter("ticket"));
    }
}
