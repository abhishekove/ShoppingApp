package com.example.weshoppers;



public class Items {
    public Items() {
    }
   private String id1 ;
   private String title,description,price,offers,itemid;

    public String getOffers() {
        return offers;
    }

    public Items(String id1, String title, String description, String price, String offers, String itemid) {
        this.id1 = id1;
        this.title = title;
        this.description = description;
        this.price = price;
        this.offers = offers;
        this.itemid = itemid;
    }

    public String getId1() {
        return id1;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getItemid() {
        return itemid;
    }
}
