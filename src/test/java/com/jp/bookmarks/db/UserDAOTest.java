
package com.jp.bookmarks.db;

import com.jp.bookmarks.core.User;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A unit test of class UserDAO.
 *
 * @author Dmitry Noranovich
 */
public class UserDAOTest {

    private static final SessionFactory SESSION_FACTORY
            = HibernateUtil.getSessionFactory();
    private static Liquibase liquibase = null;
    private Session session;
    private Transaction tx;
    private UserDAO dao;

    @BeforeClass
    public static void setUpClass() throws LiquibaseException, SQLException {

        SessionFactoryImpl sessionFactoryImpl
                = (SessionFactoryImpl) SESSION_FACTORY;
        DriverManagerConnectionProviderImpl provider
                = (DriverManagerConnectionProviderImpl) sessionFactoryImpl
                .getConnectionProvider();
        Connection connection = provider.getConnection();
        Database database = DatabaseFactory
                .getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        liquibase
                = new Liquibase(
                        "migrations.xml",
                        new ClassLoaderResourceAccessor(),
                        database);
        liquibase.dropAll();

    }

    @AfterClass
    public static void tearDownClass() {
        SESSION_FACTORY.close();
    }

    @Before
    public void setUp() throws LiquibaseException {
        liquibase.update("TEST");
        session = SESSION_FACTORY.openSession();
        dao = new UserDAO(SESSION_FACTORY);
        tx = null;
    }

    @After
    public void tearDown() throws DatabaseException, LockException {
        liquibase.dropAll();
    }

    /**
     * Test of findAll method, of class UserDAO.
     */
    @Test
    public void testFindAll() {
        List<User> users = null;
        try {
            ManagedSessionContext.bind(session);
            tx = session.beginTransaction();
            users = dao.findAll();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            ManagedSessionContext.unbind(SESSION_FACTORY);
            session.close();
        }

        assertNotNull(users);
        assertNotNull(users.isEmpty());
        assertEquals(2, users.size());

    }

    /**
     * Test of findByUsernamePassword method, of class UserDAO.
     */
    @Test
    public void testFindByUsernamePassword() {
        String expectedUsername = "user1";
        String expectedPassword = "pwd1";

        Optional<User> user;

        //First
        try {
            ManagedSessionContext.bind(session);
            tx = session.beginTransaction();

            //Do something here with UserDAO

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            ManagedSessionContext.unbind(SESSION_FACTORY);
            session.close();
        }

        //Reopen session
        session = SESSION_FACTORY.openSession();
        tx = null;

        //Second
        try {
            ManagedSessionContext.bind(session);
            tx = session.beginTransaction();

            //Do something here with UserDAO

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            ManagedSessionContext.unbind(SESSION_FACTORY);
            session.close();
        }

    }

}
