package db;

public class Company {
    private String vatId;
    private String name;
    private String address;

    public Company(String vatId, String name, String address) {
        this.vatId = vatId;
        this.name = name;
        this.address = address;
    }

    /**
     * @return String return the vatId
     */
    public String getVatId() {
        return vatId;
    }

    /**
     * @param vatId the vatId to set
     */
    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

}
