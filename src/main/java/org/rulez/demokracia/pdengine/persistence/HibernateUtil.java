package org.rulez.demokracia.pdengine.persistence;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.rulez.demokracia.pdengine.Choice;
import org.rulez.demokracia.pdengine.Vote;

final public class HibernateUtil {

  private HibernateUtil() {
  }

  public static SessionFactory getSessionFactory() {
    SessionFactory sessionFactory;
    final Properties properties = new Properties();
    properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
    properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.show_sql", "true");
    properties
        .put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
    properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:test");
    properties.put("hibernate.connection.username", "sa");
    properties.put("hibernate.connection.password", "");

    final Configuration configuration = new Configuration()
        .addProperties(properties)
        .addAnnotatedClass(Vote.class)
        .addAnnotatedClass(Choice.class);
    final StandardServiceRegistryBuilder builder =
        new StandardServiceRegistryBuilder()
            .applySettings(properties);
    sessionFactory = configuration.buildSessionFactory(builder.build());
    return sessionFactory;
    //      throw new ExceptionInInitializerError(ex);
  }
}
