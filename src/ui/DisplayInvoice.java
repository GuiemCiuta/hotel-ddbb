package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.Booking;
import db.Customer;
import db.Invoice;
import db.Setup;
import ui.components.Components;

public class DisplayInvoice extends JFrame {
    Invoice invoice;

    public DisplayInvoice(Invoice invoice) {
        // this.invoice = invoice;
        Customer customer = new Customer("41747470D", "Guiem", "Bagur Moll", "ES", 0, "Camí Capelletes s/n",
                "guillembagurmoll@gmail.com", "682572005");

        this.invoice = new Invoice(10, new Booking(customer, "2024-03-24", "2024-03-28", 100.23, false));

        // Create a main layout to place all the elements
        this.setSize(1000, 700);

        this.setLayout(null);

        JButton goBackButton = new JButton("<--");
        goBackButton.setBounds(10, 10, 100, 50);
        goBackButton.addActionListener(
                e -> ClientUtilities.gotoCustomerArea(this, this.invoice.getBooking().getCustomer()));
        this.add(goBackButton);

        // Add title
        JLabel pageH1 = Components.createH1("Hotelsa Booking Invoice #" + this.invoice.getId());
        pageH1.setBounds(120, 10, 700, 50);
        this.getContentPane().add(pageH1);

        this.addFiscalData();
        this.addCommercialData();

        // Display everything
        this.setSize(1200, 850);
        this.setVisible(true);
    }

    private void addFiscalData() {
        JPanel fiscalData = new JPanel();
        fiscalData.setBounds(10, 60, 700, 300);
        fiscalData.setLayout(null);

        JPanel companyData = new JPanel();
        companyData.setBounds(10, 60, 320, 300);
        companyData.setLayout(null);

        JLabel companyName = new JLabel(Setup.company.getName());
        companyName.setBounds(0, 0, 320, 25);
        companyData.add(companyName);

        JLabel companyVatId = new JLabel(Setup.company.getVatId());
        companyVatId.setBounds(0, 25, 320, 25);
        companyData.add(companyVatId);

        JLabel companyAddress = new JLabel(Setup.company.getAddress());
        companyAddress.setBounds(0, 50, 320, 25);
        companyData.add(companyAddress);

        JPanel customerData = new JPanel();
        customerData.setBounds(340, 60, 320, 300);
        customerData.setLayout(null);

        JLabel customerName = new JLabel(this.invoice.getBooking().getCustomer().getFullName());
        customerName.setBounds(0, 0, 320, 25);
        customerData.add(customerName);

        JLabel customerId = new JLabel(this.invoice.getBooking().getCustomer().getNationalId());
        customerId.setBounds(0, 25, 320, 25);
        customerData.add(customerId);

        JLabel customerAddress = new JLabel(this.invoice.getBooking().getCustomer().getAddress());
        customerAddress.setBounds(0, 50, 320, 25);
        customerData.add(customerAddress);

        fiscalData.add(companyData);
        fiscalData.add(customerData);
        this.add(fiscalData);
    }

    private void addCommercialData() {
        JPanel commercialData = new JPanel();
        commercialData.setBounds(10, 400, 700, 300);
        commercialData.setLayout(null);

        JLabel productNameLabel = new JLabel("Product");
        productNameLabel.setBounds(0, 0, 100, 25);
        commercialData.add(productNameLabel);

        JLabel daysAmountLabel = new JLabel("Num. nights");
        daysAmountLabel.setBounds(130, 0, 100, 25);
        commercialData.add(daysAmountLabel);

        JLabel amountPerDayLabel = new JLabel("Amount p. n.");
        amountPerDayLabel.setBounds(260, 0, 100, 25);
        commercialData.add(amountPerDayLabel);

        JLabel subtotalAmountLabel = new JLabel("Subtotal");
        subtotalAmountLabel.setBounds(390, 0, 100, 25);
        commercialData.add(subtotalAmountLabel);

        JLabel taxLabel = new JLabel("VAT(21%)");
        taxLabel.setBounds(260, 200, 200, 25);
        commercialData.add(taxLabel);

        JLabel totalAmountLabel = new JLabel("TOTAL");
        totalAmountLabel.setBounds(260, 230, 100, 25);
        commercialData.add(totalAmountLabel);

        JLabel productName = new JLabel("Room");
        productName.setBounds(0, 30, 320, 25);
        commercialData.add(productName);

        JLabel numNights = new JLabel();
        numNights.setText(Integer.toString(this.invoice.getBooking().getNightsNum()));
        numNights.setBounds(130, 30, 320, 25);
        commercialData.add(numNights);

        JLabel amountPerDay = new JLabel();
        amountPerDay.setText(
                Double.toString(this.getAmountPerNight()));
        amountPerDay.setBounds(260, 30, 320, 25);
        commercialData.add(amountPerDay);

        JLabel vat = new JLabel();
        vat.setText(
                Double.toString(this.calculateVat()));
        vat.setBounds(390, 200, 320, 25);
        commercialData.add(vat);

        JLabel totalAmount = new JLabel();
        totalAmount.setText(
                Double.toString(this.getFinalAmount()));
        vat.setBounds(390, 230, 320, 25);
        commercialData.add(totalAmount);

        this.add(commercialData);
    }

    private double getAmountPerNight() {
        return this.invoice.getBooking().getAmount() / this.invoice.getBooking().getNightsNum();
    }

    private double calculateVat() {
        return this.invoice.getBooking().getAmount() * .21;
    }

    private double getFinalAmount() {
        return this.invoice.getBooking().getAmount() * 1.21;

    }
}
