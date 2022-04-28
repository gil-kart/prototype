package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Catalog;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

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
		setItems((List<Item>)(msg));
	}

	public boolean isDataReady(){
		return !this.items.isEmpty();
	}

	public static SimpleClient getClient() throws IOException {
		if (client == null) {
			//String ip = InetAddress.getByName("DanyP").getHostAddress();
			String ip = "172.20.10.6";
			client = new SimpleClient(ip, 3000);
		}
		return client;
	}

}
