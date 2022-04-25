package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SimpleServer extends AbstractServer {

	public SimpleServer(int port) {
		super(port);
		
	}
	private static Session session;

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		//configuration.addAnnotatedClass(Car.class).addAnnotatedClass(Owner.class).addAnnotatedClass(Garage.class).addAnnotatedClass(Image.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();

		return configuration.buildSessionFactory(serviceRegistry);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(msgString.equals("updateProduct")){
			try{
				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				//todo
				// -------- update product in catalog table --------


			}
			catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					try{
						session.close();
					} catch (Exception e){

					}
			}
		}

	}

}
