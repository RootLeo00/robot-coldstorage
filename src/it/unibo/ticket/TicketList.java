package it.unibo.ticket;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketList {
    private List<Ticket> tickets;
    private int lastNumber;
    private long expirationTime;

    public TicketList(long expirationTime) {
        tickets = new ArrayList<>();
        lastNumber = 0;
        this.expirationTime = expirationTime;
    }

    public synchronized Ticket createTicket(int kgToStore) {
        Ticket ticket = new Ticket();
        ticket.setKgToStore(kgToStore);
        lastNumber++;
        ticket.setTicketNumber(lastNumber);
        ticket.setTicketSecret(generateSecret(7));
        ticket.setTimestamp(Instant.now().toEpochMilli());
        tickets.add(ticket);
        return ticket;
    }

    private String generateSecret(int n) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (letters.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(letters
                    .charAt(index));
        }

        return sb.toString();

    }

    public synchronized Ticket getTicket(int ticketNumber) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketNumber() == ticketNumber) {
                return ticket;
            }
        }
        return null;
    }

    public synchronized void  removeTicket(int ticketNumber) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketNumber() == ticketNumber) {
                tickets.remove(ticket);
            }
        }
    }

    public synchronized int getTotalKgToStore() {
        int result = 0;
        for (Ticket ticket : tickets) {
            result += ticket.getKgToStore();
        }
        return result;
    }

    public synchronized boolean isExpired(Ticket ticket) {
        return Instant.now().toEpochMilli() - ticket.getTimestamp() >= expirationTime;
    }

}