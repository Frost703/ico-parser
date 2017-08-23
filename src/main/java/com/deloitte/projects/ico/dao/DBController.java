package com.deloitte.projects.ico.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBController {
    //make it create a new instance of tables every run.

    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public static int save(Object o){
        Session session = factory.openSession();
        session.beginTransaction();

        session.save(o);
        session.getTransaction().commit();

        session.close();

        return 1;
    }
}
