package db;

import java.time.LocalDate;

public class Invoice {
    private int id;
    private Booking booking;
    private LocalDate date;
    private boolean paid;

    public Invoice(int id, Booking booking, LocalDate date, boolean paid) {
        this.id = id;
        this.booking = booking;
        this.date = date;
        this.paid = paid;
    }

    public Invoice(int id, Booking booking, LocalDate date) {
        this.id = id;
        this.booking = booking;
        this.date = date;
        this.paid = false;
    }

    public Invoice(int id, Booking booking) {
        this.id = id;
        this.booking = booking;
        this.date = booking.getStartDate();
        this.paid = false;
    }


    /**
     * @return int return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Booking return the booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * @param booking the booking to set
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * @return LocalDate return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return boolean return the paid
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * @param paid the paid to set
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
