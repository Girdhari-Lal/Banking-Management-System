package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class FactoryProvider {
    private static SessionFactory FACTORY;
    private static Session SESSION;

    public static Session getSession() {;
        if (FACTORY == null) {
            FACTORY = new Configuration().configure().buildSessionFactory();
            SESSION = FACTORY.openSession();
        }
        return SESSION;
    }
    public static void closeSession() {
        if (FACTORY.isOpen()) {
            SESSION.close();
            FACTORY.close();
        }
    }
}
