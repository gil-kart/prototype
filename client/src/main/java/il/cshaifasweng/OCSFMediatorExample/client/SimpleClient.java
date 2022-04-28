package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Catalog;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class SimpleClient extends AbstractClient {

    private static SimpleClient client = null;

    public List<Item> getItems() {
        List<Item> temp = List.copyOf(items);
        items.clear();
        return temp;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    List<Item> items = new ArrayList<>();

    private SimpleClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
//		if (msg.getClass().equals(Warning.class)) {
//			EventBus.getDefault().post(new WarningEvent((Warning) msg));
//		}
//		else (msg.getClass().equals(List<Item>.class)){
//			System.out.println("SDAFLKJSADF");
//		}
        setItems((List<Item>) (msg));
    }

    public boolean isDataReady() {
        return !this.items.isEmpty();
    }

    public static SimpleClient getClient() throws IOException {
        if (client == null) {
            //String ip = InetAddress.getByName("DESKTOP-F8L3FP7").getHostAddress();
            String testIp;
            int port;
            try {
                String[] testArgList = App.getArguments();
                String testComputerName = testArgList[0];
                int testPort = 3000;
                try {
                    testPort = Integer.parseInt(testArgList[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid port, using port 3000.");
                }
                try {
                    testIp = InetAddress.getByName(testComputerName).getHostAddress();
                } catch (UnknownHostException e) {
                    System.out.println("Hostname could not be resolved, using localhost.");
                    testIp = InetAddress.getByName("localhost").getHostAddress();
                }
                port = testPort;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No socket was provided, using localhost:3000 .");
                testIp = "localhost";
                port = 3000;
            }
            System.out.println("creating client connection to socket " + testIp + ":" + port);
            client = new SimpleClient(testIp, port);
        }
        return client;
    }

}
