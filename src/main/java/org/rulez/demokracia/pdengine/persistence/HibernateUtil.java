package org.rulez.demokracia.pdengine.persistence;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.rulez.demokracia.pdengine.Choice;
import org.rulez.demokracia.pdengine.vote.Vote;

final public class HibernateUtil {
    private static final Logger LOGGER = Logger.getLogger( HibernateUtil.class.getName() );

	private HibernateUtil() {
	}

    public static SessionFactory getSessionFactory() {
		try {
			SessionFactory sessionFactory;
			Properties properties = new Properties();
			properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
			properties.put("hibernate.hbm2ddl.auto", "update");
			properties.put("hibernate.show_sql", "true");
			properties.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
			properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:test");
			properties.put("hibernate.connection.username", "sa");
			properties.put("hibernate.connection.password", "");
			 
			Configuration configuration = new Configuration()
				.addProperties(properties)
				.addAnnotatedClass(Vote.class)
				.addAnnotatedClass(Choice.class);
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
					.applySettings(properties);
			sessionFactory = configuration.buildSessionFactory(builder.build());
			return sessionFactory;
          } catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
          }
      }
}