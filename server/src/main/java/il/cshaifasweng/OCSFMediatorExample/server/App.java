package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Catalog;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static il.cshaifasweng.OCSFMediatorExample.server.Utilities.getSessionFactory;


/**
 * Hello world!
 *
 */
public class App {
    private static Session session;
    private static SimpleServer server;

    public static void main(String[] args) throws IOException {

        System.out.println("SERVER: INITIALIZING SERVER");
        server = new SimpleServer(3000);
        System.out.println("SERVER: LISTENING");
        server.listen();
    }

    static Catalog generateCatalog() {

        Catalog catalog = new Catalog();
        catalog.setItems(createItemList());
        return catalog;

    }

    private static List<Item> createItemList() {
        Item item;
        List<Item> itemList = new ArrayList<>();
    //    for (int i = 0; i < 5; i++) {
            String base_path = "/images/";
            item = new Item("סחלב קורל", 160, base_path + "sahlav_coral.jpg");
            itemList.add(item);
            item = new Item("ורד ענבר", 120, base_path + "vered_inbar.jpg");
            itemList.add(item);
            item = new Item("סחלב לבן", 140, base_path + "sahlav_lavan.jpg");
            itemList.add(item);
            item = new Item("נרקיס חצוצרה", 110, base_path + "narkis_hatsostra.jpg");
            itemList.add(item);

       // }
        return itemList;
    }
}


