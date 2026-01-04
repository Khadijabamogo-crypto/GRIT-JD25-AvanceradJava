package se.gritacademy;

public class Dijas {

    private int id;
    private String title;
    private String seller;
    private String description;
    private int price;


    public Dijas(){}

    public Dijas(int id, String title, String seller, String description, int price) {
        this.id = id;
        this.title = title;
        this.seller = seller;
        this.description = description;
        this.price = price;

    }

    //Getters & setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Seller: " + seller +
                ", Description: " + description;
    }

}
