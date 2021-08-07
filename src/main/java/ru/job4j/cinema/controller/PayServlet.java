package ru.job4j.cinema.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cinema.repository.Account;
import ru.job4j.cinema.repository.Place;
import ru.job4j.cinema.repository.Ticket;
import ru.job4j.cinema.service.AccountService;
import ru.job4j.cinema.service.PlaceService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PayServlet extends HttpServlet {
private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        HttpSession sc = req.getSession();
        Place place = (Place) sc.getAttribute("place");
        int id = Integer.parseInt(place.getName());
        out(PlaceService.placeForJson(id), resp);
    }

    private void out(Object src, HttpServletResponse resp) throws IOException {
        String json = GSON.toJson(src);
        OutputStream output = resp.getOutputStream();
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Account account = GSON.fromJson(req.getReader(), Account.class);
        Place place = (Place) req.getSession().getAttribute("place");
        place = PlaceService.getPlaceById(Integer.parseInt(place.getName()));
        Ticket ticket = new Ticket();
        try {
            account = AccountService.saveAccount(account);
            ticket.setSessionId(0);
            ticket.setRow(place.getRow());
            ticket.setCell(place.getCell());
            ticket.setAccountId(account.getId());
            Ticket ticketWithId = TicketService.saveTicket(ticket);
            out(ticketWithId, resp);
        } catch (ConstraintViolationException e) {
            if (!AccountService.checkAccount(account)) {
                System.out.println("acc check");
                System.out.println(e.getMessage());
                PlaceService.releaseThisPlace(place);
                resp.sendError(400, e.getMessage());
            } else if (!TicketService.isFree(ticket)) {
                System.out.println("tick check");
                System.out.println(e.getMessage());
                PlaceService.releaseThisPlace(place);
                resp.sendError(400, e.getMessage());
            }
        }
    }
}
