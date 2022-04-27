package il.cshaifasweng.OCSFMediatorExample.client;

public class Flower {
    public Flower(){}

    public Flower(String name, String price, String imgSrc) {
        this.name = name;
        this.price = price;
        this.imgSrc = imgSrc;
    }

    private String name;
    private String price;
    private String imgSrc;
    private String color;


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
