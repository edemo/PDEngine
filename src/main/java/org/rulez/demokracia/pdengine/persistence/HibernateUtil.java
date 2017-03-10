package org.rulez.demokracia.pdengine.persistence;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.rulez.demokracia.pdengine.Choice;
import org.rulez.demokracia.pdengine.Vote;

public class HibernateUtil {

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory;
		try {
        	    Properties properties = new Properties();
        	    properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        	    properties.put("hibernate.hbm2ddl.auto", "update");
        	    properties.put("hibernate.show_sql", "true");
        	    properties.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
        	    properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:test");
        	    properties.put("hibernate.connection.username", "sa");
        	    properties.put("hibernate.connection.password", "");
        	 
        	    Configuration configuration = new Configuration();
				configuration = configuration.addProperties(properties);
				configuration = configuration.addAnnotatedClass(Vote.class);
				configuration = configuration.addAnnotatedClass(Choice.class);
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
				builder = builder.applySettings(properties);
				sessionFactory = configuration.buildSessionFactory(builder.build());
          } catch (Exception ex) {
              System.err.println("Initial SessionFactory creation failed." + ex);
              throw new ExceptionInInitializerError(ex);
          }
          return sessionFactory;
      }

}