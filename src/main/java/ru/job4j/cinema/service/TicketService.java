package ru.job4j.cinema.service;

import ru.job4j.cinema.repository.Storage;
import ru.job4j.cinema.repository.Ticket;

public class TicketService {

    public static Ticket saveTicket(Ticket ticket) {
        return Storage.instOf().saveTicket(ticket);
    }

    public static boolean isFree(Ticket ticket) {
        return Storage.instOf().isFree(ticket);
    }
}
