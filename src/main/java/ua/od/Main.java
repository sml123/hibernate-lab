package ua.od;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sshumilov on 22.07.16.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger("APP");

    static  {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        log.info("staring...");
        DbContainer dbContainer = setup();
        dbContainer.close();
        System.out.println("Created");
    }

    public static DbContainer setup() throws IOException {
        Properties hikariProperties = new Properties();
        try (InputStream fis = Main.class.getResourceAsStream("/db.properties")) {
            hikariProperties.load(fis);
        }
        HikariDataSource ds = new HikariDataSource(new HikariConfig(hikariProperties));
        Map<String, Object> emfProperties = new HashMap<>();
        final Log4jdbcProxyDataSource dsProxy = new Log4jdbcProxyDataSource(ds);
        dsProxy.setLogFormatter(new net.sf.log4jdbc.tools.Log4JdbcCustomFormatter());
        emfProperties.put("hibernate.connection.datasource", dsProxy);
        return new DbContainer(Persistence.createEntityManagerFactory("h2PU", emfProperties), ds);
    }

    public static final class DbContainer {
        EntityManagerFactory emf;
        HikariDataSource ds;

        public DbContainer(EntityManagerFactory emf, HikariDataSource ds) {
            this.emf = emf;
            this.ds = ds;
        }

        public EntityManagerFactory getEmf() {
            return emf;
        }

        public HikariDataSource getDs() {
            return ds;
        }

        public void close() {
            emf.close();
            ds.close();
        }
    }
}
