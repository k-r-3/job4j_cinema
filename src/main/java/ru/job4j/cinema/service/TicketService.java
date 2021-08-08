package ru.job4j.cinema.service;

import ru.job4j.cinema.repository.Storage;
import ru.job4j.cinema.models.Ticket;
import ru.job4j.cinema.models.TicketWithName;
import java.util.Collection;

public class TicketService {

    public static Ticket saveTicket(Ticket ticket) {
        return Storage.instOf().saveTicket(ticket);
    }

    public static boolean isFree(Ticket ticket) {
        return Storage.instOf().isFree(ticket);
    }

    public static Collection<TicketWithName> showTicket(int ticketId) {
        return Storage.instOf().showTicket(ticketId);
    }
}
