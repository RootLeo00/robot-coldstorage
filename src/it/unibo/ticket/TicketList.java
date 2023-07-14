import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TicketList {
    private List<Ticket> tickets;
    private int lastNumber;

    public TicketList() {
        tickets = new ArrayList<>();
        lastNumber = 1;
    }

    public Ticket createTicket(int kgToStore) {
        Ticket ticket = new Ticket();
        ticket.setKgToStore(kgToStore);
        lastNumber++;
        ticket.setTicketNumber(lastNumber);
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        ticket.setTicketSecret(new String(array, Charset.forName("UTF-8")));
        ticket.setTimestamp(Instant.now().toEpochMilli());
        tickets.add(ticket);
        return ticket;
    }

    public Ticket getTicket(int ticketNumber) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketNumber() == ticketNumber) {
                return ticket;
            }
        }
        return null;
    }

    public removeTicket(int ticketNumber) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketNumber() == ticketNumber) {
                tickets.remove(ticket);
            }
        }
    }

}