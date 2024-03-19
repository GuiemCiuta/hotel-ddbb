package db;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Booking {
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private double amount;
    private boolean canceled;

    public Booking(Customer customer, LocalDate startDate, LocalDate endDate, double amount, boolean canceled) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.canceled = canceled;
    }

    public Booking(Customer customer, String startDate, String endDate, double amount, boolean canceled) {
        this(customer, Utils.convertStringToLocalDate(startDate), Utils.convertStringToLocalDate(endDate), amount,
                canceled);
    }

    public int getNightsNum() {
        return (int) ChronoUnit.DAYS.between(this.startDate, this.endDate);
    }

    /**
     * @return Customer return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return LocalDate return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return LocalDate return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return double return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return boolean return the canceled
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * @param canceled the canceled to set
     */
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
