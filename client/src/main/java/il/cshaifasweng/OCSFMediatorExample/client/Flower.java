package il.cshaifasweng.OCSFMediatorExample.client;

public class Flower {
    private String name;
    private String price;
    private String imgSrc;
    private String color;
    private final int id;

    public Flower(String name, String price, String imgSrc,int id) {
        this.name = name;
        this.price = price;
        this.imgSrc = imgSrc;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public int getId(){return this.id;}
    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
