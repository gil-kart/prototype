package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Catalog;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import static il.cshaifasweng.OCSFMediatorExample.server.App.generateCatalog;
import static il.cshaifasweng.OCSFMediatorExample.server.Utilities.getSessionFactory;

public class SimpleServer extends AbstractServer {
    private static Session session;
    private static SessionFactory SF;

    public SimpleServer(int port) {
        super(port);
        SF = getSessionFactory();
    }


    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg.getClass().equals(String.class)) {
            String msgString = msg.toString();
            if (msgString.startsWith("#warning")) {
                Warning warning = new Warning("Warning from server!");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (msgString.equals("getCatalog")) {
            try {
                System.out.println("Got massage get catalog!");
                System.out.println("SERVER: BUILDING CATALOG");
                session = SF.openSession();
                session.beginTransaction();
                Catalog catalog = generateCatalog();
                session.save(catalog);
                session.flush();
                System.out.println("SERVER: DONE BUILDING CATALOG");
                session.getTransaction().commit(); // Save everything.
            } catch (Exception exception) {
                if (session != null) {
                    session.getTransaction().rollback();
                }
                System.err.println("An error occured, changes have been rolled back.");
                exception.printStackTrace();
            } finally {
                if (session != null)
                    try {
                        session.close();
                        System.out.println("SERVER: CLOSED SESSION.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
            }
                try {
                    session = SF.openSession();
                    session.beginTransaction();
                    CriteriaBuilder builder = session.getCriteriaBuilder();
                    CriteriaQuery<Item> query = builder.createQuery(Item.class);
                    query.from(Item.class);
                    List<Item> data = session.createQuery(query).getResultList();
                    System.out.format("Sent catalog to client %s\n", client.getInetAddress().getHostAddress());
                    client.sendToClient(new LinkedList<Item>(data));
                } catch (Exception exception) {
                    if (session != null) {
                        session.getTransaction().rollback();
                    }
                    System.err.println("An error occured, changes have been rolled back.");
                    exception.printStackTrace();
                } finally {
                    if (session != null)
                        try {
                            session.close();
                            System.out.println("SERVER: CLOSED SESSION.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                }
            }
        }
        // todo: add request class that contains a string command and data to be transferred
        else { // this means for now we got an item
            try { // right now supports only price update for an item
                //todo
                // -------- update product in catalog table --------
                System.out.println("STARTING TO UPDATE PRODUCT");
                session = SF.openSession();
                session.beginTransaction();

                Item item = getItemById((Item) (msg));
                item.setPrice(((Item) (msg)).getPrice());
                session.save(item);


                session.flush();
                session.getTransaction().commit(); // Save everything.
                System.out.println("product " + ((Item) (msg)).getName() + " was updated in catalog ");

            } catch (Exception exception) {
                if (session != null) {
                    session.getTransaction().rollback();
                }
                System.err.println("An error occured, changes have been rolled back.");
                exception.printStackTrace();
            } finally {
                if (session != null)
                    try {
                        session.close();
                    } catch (Exception e) {

                    }
            }
        }

    }

    private Item getItemById(Item msg) {
        session = SF.openSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> query = builder.createQuery(Item.class);
        query.from(Item.class);
        List<Item> data = session.createQuery(query).getResultList();
        for (Item item : data) {
            if (item.getId() == msg.getId()) {
                return item;
            }
        }
        // needs to be fixed!!!
        return data.get(0);
    }

}