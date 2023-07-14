public class Ticket{
    private int ticketNumber;
    private String ticketSecret;
    private long timestamp;
    private int kgToStore;

    public int getTicketNumber() {
        return ticketNumber;
    }

    public int getKgToStore() {
        return kgToStore;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTicketSecret() {
        return ticketSecret;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setKgToStore(int kgToStore) {
        this.kgToStore = kgToStore;
    }

    public void setTicketSecret(String ticketSecret) {
        this.ticketSecret = ticketSecret;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public boolean equals(Object o){
        if(o instanceof Ticket){
            Ticket object=(Ticket)o; 
            return (object.getTicketNumber() == this.getTicketNumber());
        }
        return false;
    }
}