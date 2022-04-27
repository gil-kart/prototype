package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

//todo inherit from abstract client to handle massages to server
public class primaryController implements Initializable {
    final String base_path = "/images/";

    @FXML
    private ImageView FlowerImg;

    @FXML
    private Label FlowerNameLabel;

    @FXML
    private Label FlowerPrice;

    @FXML
    private VBox chosenFlower;

    @FXML
    private Button priceUpdate;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private MyListener myListener;
    private List<Flower> flowerList = new ArrayList<>();

//    private List<Flower> getFlowerList() {
//        List<Flower> flowerList = new ArrayList<>();
//        Flower flower;
//        for (int i = 0; i < 5; i++) {
//            flower = new Flower();
//            flower.setName("סחלב קורל");
//            flower.setColor("סגול");
//            flower.setImgSrc(base_path + "sahlav_coral.jpg");
//            flower.setPrice("100 ש''ח");
//            flowerList.add(flower);
//
//            flower = new Flower();
//            flower.setName("ורד ענבר");
//            flower.setColor("ירוק");
//            flower.setImgSrc(base_path + "vered_inbar.jpg");
//            flower.setPrice("80 ש''ח");
//            flowerList.add(flower);
//
//            flower = new Flower();
//            flower.setName("סחלב לבן");
//            flower.setColor("ירוק");
//            flower.setImgSrc(base_path + "sahlav_lavan.jpg");
//            flower.setPrice("120 ש''ח");
//            flowerList.add(flower);
//
//            flower = new Flower();
//            flower.setName("סחלב סגול");
//            flower.setColor("ירוק");
//            flower.setImgSrc(base_path + "purple.jpg");
//            flower.setPrice("60 ש''ח");
//            flowerList.add(flower);
//
//            flower = new Flower();
//            flower.setName("נרקיס חצוצרה");
//            flower.setColor("ירוק");
//            flower.setImgSrc(base_path + "narkis_hatsostra.jpg");
//            flower.setPrice("40 ש''ח");
//            flowerList.add(flower);
//
//            flower = new Flower();
//            flower.setName("ורד ורוד");
//            flower.setColor("ירוק");
//            flower.setImgSrc(base_path + "vered_varod.jpg");
//            flower.setPrice("140 ש''ח");
//            flowerList.add(flower);
//        }
//        return flowerList;
//    }


    @FXML
    void initialize() {
    }


    public void setChosenItem(Flower flower) {
        FlowerNameLabel.setText(flower.getName());
        FlowerPrice.setText(flower.getPrice());
        try {
            FlowerImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(flower.getImgSrc()))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private SimpleClient client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            client = SimpleClient.getClient();
            client.openConnection();
            client.sendToServer("getCatalog");
            while (!client.isDataReady()) {
                Thread.sleep(300);
            }
            List<Item> items = client.getItems();
            addItemsToFlowerList(items);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // flowerList.addAll(getFlowerList());

        if (flowerList.size() > 0) {
            setChosenItem(flowerList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Flower flower) {
                    setChosenItem(flower);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (Flower flower : flowerList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                ItemController itemController = fxmlLoader.getController();
                itemController.setData(flower, myListener);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);


                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addItemsToFlowerList(List<Item> items) {
        List<Flower> retFlowerList = new ArrayList<>();
        ;
        for (int i = 0; i < items.size(); i++) {
            Item curItem = items.get(i);
            retFlowerList.add(new Flower(curItem.getName(), Integer.toString(curItem.getPrice()) + " ש\"ח ", curItem.getImage(), 1));
        }
        flowerList.addAll(retFlowerList);
    }

    @FXML
    void onPriceUpdate(ActionEvent event) {
        try {
            URL url = getClass().getResource("UpdatePrice.fxml");
            Scene scene = new Scene(FXMLLoader.load(url));
            Stage stage = new Stage();
            System.out.println("jdfkh");
            stage.setTitle("עדכון מחיר מוצר");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
