package ua.od.onetomany;

import com.zaxxer.hikari.HikariDataSource;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.od.Main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

/**
 * Created by sshumilov on 25.07.16.
 */
public class ManyToOneTest {

    private static final Logger log = LoggerFactory.getLogger("SQL");

    private static EntityManagerFactory emf;
    private static HikariDataSource ds;

    @BeforeClass
    public static void before() throws IOException {
        Main.DbContainer container = Main.setup();
        emf = container.getEmf();
        ds = container.getDs();
    }

    @AfterClass
    public static void after() {
        emf.close();
        ds.close();
    }


    /**
     * select root1_.id as id1_2_, root1_.name as name2_2_ from child child0_ left outer join root
     root1_ on child0_.root_id=root1_.id
     |---|-------|
     |ID |NAME   |
     |---|-------|
     |1  |Name 1 |
     |1  |Name 1 |
     |1  |Name 1 |
     |1  |Name 1 |
     |1  |Name 1 |
     |2  |Name 2 |
     |2  |Name 2 |
     |2  |Name 2 |
     |2  |Name 2 |
     |2  |Name 2 |
     |---|-------|
     */
    @Test
    public void selectHqlJoin() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            List<Root> list = em.createQuery("SELECT r FROM Child c LEFT JOIN c.root as r", Root.class)
                    .getResultList();
            Assert.assertNotNull(list);
            Assert.assertEquals(10, list.size());
        } finally {
            em.close();
        }

    }

    /**
     * select distinct root1_.id as id1_2_, root1_.name as name2_2_ from child child0_ left outer
     join root root1_ on child0_.root_id=root1_.id
     |---|-------|
     |ID |NAME   |
     |---|-------|
     |1  |Name 1 |
     |2  |Name 2 |
     |---|-------|
     */
    @Test
    public void selectDistinctHqlJoin() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            List<Root> list = em.createQuery("SELECT DISTINCT r FROM Child c LEFT JOIN c.root as r", Root.class)
                    .getResultList();
            Assert.assertNotNull(list);
            Assert.assertEquals(2, list.size());
        } finally {
            em.close();
        }

    }
}
