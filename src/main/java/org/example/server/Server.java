package org.example.server;

import org.example.entities.Client;
import org.example.entities.Item;
import org.example.entities.Message;
import org.example.entities.User;
import org.example.ocsf.server.AbstractServer;
import org.example.ocsf.server.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SocketHandler;

public class Server  extends AbstractServer {
    SessionFactory sessionFactory = getSessionFactory();
    private static Session session;
    public static  List<Item> getAllItems(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> query = builder.createQuery(Item.class);
        query.from(Item.class);
        List<Item> data = session.createQuery(query).getResultList();
        return data;
    }

    public static void updateItem(Item item) {
        try {
            item.setPrice(item.getPrice());
            session.update(item);
            session.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    static void intitializeDataBase(){
        Item item1 = new Item("Festive Gladioli bouquet",349,"Bouquet","https://florabloom.co.il/wp-content/uploads/2020/07/MG_1293-2-Edit_websize-1.jpg");
        session.save(item1);
        session.flush();
        Item item2= new Item("Miss Lizzie Bouquet",318,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_1123-2-Edit_websize-1.jpg");
        session.save(item2);
        session.flush();
        Item item3= new Item("Red Sun Bouquet",268,"Bouquet","https://florabloom.co.il/wp-content/uploads/2020/08/MG_1205-2-Edit_websize.jpg");
        session.save(item3);
        session.flush();
        Item item4=new Item("Pure White Bouquet",295,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/08/MG_1238-2-Edit_websize-1.jpg");
        session.save(item4);
        session.flush();
        Item item5=new Item("Lady Lillia Bouquet",315,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_1167-2-Edit_websize-1.jpg");
        session.save(item5);
        session.flush();
        Item item6=new Item("Red Roses",213,"Bouquet","https://florabloom.co.il/wp-content/uploads/2020/07/MG_4288-Edit_websize-1-1.jpg");
        session.save(item6);
        session.flush();
        Item item7=new Item("Autumn Bouquet",347,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_1114-2-Edit_websize-3.jpg");
        session.save(item7);
        session.flush();
        Item item8=new Item("Romantic Chrysanthemum Bouquet",299,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_4327-1_websize.jpg");
        session.save(item8);
        session.flush();
        Item item9=new Item("Sunflowers Bouquet",255,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_4308-1_websize.jpg");
        session.save(item9);
        session.flush();
        Item item10=new Item("Lizi Boxt",279,"Bouquet","https://florabloom.co.il/wp-content/uploads/2020/08/MG_1095-2-Edit_websize-1.jpg");
        session.save(item10);
        session.flush();
        Item item11=new Item("Calthea",63,"tree","https://florabloom.co.il/wp-content/uploads/2020/11/MG_1248-2-Edit_websize.jpg");
        session.save(item11);
        session.flush();
        Item item12=new Item("Fiddle-leaf fig",60,"tree","https://florabloom.co.il/wp-content/uploads/2020/11/WhatsApp-Image-2020-11-05-at-10.59.56-1.jpeg");
        session.save(item12);
        session.flush();
        Item item13=new Item("Red Antorium",79,"tree","https://florabloom.co.il/wp-content/uploads/2020/07/WhatsApp-Image-2020-11-05-at-10.24.25.jpeg");
        session.save(item13);
        session.flush();
        Item item14=new Item("Caltheaa",65,"tree","https://florabloom.co.il/wp-content/uploads/2021/08/B8A1FC70-DF9C-4734-AA2A-A3594AB96C98.jpg");
        session.save(item14);
        session.flush();

    }
    public static Session getSession() {
        return session;
    }


    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(Message.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }



    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public Server(int port) {
        super(port);
        SessionFactory sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        intitializeDataBase();
        session.getTransaction().commit();
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
        Message message = (Message) msg;
        System.out.println("Message received");
        switch (message.getMsg()) {
            case Message.getAllItems:
                List<Item> items = getAllItems();
                Message response = new Message(Message.recieveAllItems, items);
                try{
                    client.sendToClient(response);

                } catch(IOException e) {
                    e.printStackTrace();
                }
//                session.close(); // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ DELETE WHEN ERROR @@@@@@@@@@@@@@@@@
                break;

            case Message.updateItem:
                session = sessionFactory.openSession();
                session.beginTransaction();
                Item item = (Item)message.getObject();
                updateItem(item);
                session.getTransaction().commit();
                items = getAllItems();
                Message response1 = new Message(Message.recieveAllItems, items);
                client.sendToClient(response1);
//                session.close();
                break;
            case Message.deleteProduct:
                Message delResponse = new Message(Message.deleteProductResponse);
                try{
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    Item m = (Item) message.getObject();
                    session.delete(m);
                    session.flush();
                    session.getTransaction().commit(); // Save everything
                    client.sendToClient(delResponse);
//                    session.close();
                } catch (Exception ex) {
                    if (session != null) {
                        session.getTransaction().rollback();
                    }
                    System.err.println("An error occurred, changes have been rolled back.");

                    ex.printStackTrace();

                }
                break;
            case Message.addProduct:
                Message addResponse = new Message(Message.addProductResponse);
                try {
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    Item m = (Item) message.getObject();
                    session.save(m);
                    session.flush();
                    session.getTransaction().commit();
                    client.sendToClient(addResponse);
                } catch (Exception ex) {
                    if (session != null) {
                        session.getTransaction().rollback();
                    }
                    System.err.println("An error occurred, changes have been rolled back.");

                    ex.printStackTrace();

                }
//                session.close();//When triggered back it will cause problem in saving the item properly in the table
                break;


        }
    }
    private void sendRefreshcatlogevent() {
        System.out.println("test test new function");
        try {
            Message msg = new Message(Message.getAllItems);
            this.sendToAllClients(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
