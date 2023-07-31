package com.codecool.elproyectegrande1.util;

import java.util.Properties;

import com.codecool.elproyectegrande1.entity.Role;
import com.codecool.elproyectegrande1.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.hsqldb.jdbcDriver");
                settings.put(Environment.URL, "jdbc:hsqldb:mem:userrole");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "sa");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Role.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
