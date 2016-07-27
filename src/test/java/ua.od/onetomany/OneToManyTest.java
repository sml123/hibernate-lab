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
public class OneToManyTest {

    private static final Logger log = LoggerFactory.getLogger("SQL");
    static EntityManagerFactory emf;
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
     * 1) select root0_.id as id1_1_0_, root0_.name as name2_1_0_ from root root0_ where root0_.id=1
     * 2) select children0_.root_id as root_id3_0_0_, children0_.id as id1_0_0_, children0_.id as id1_0_1_,
        children0_.name as name2_0_1_, children0_.root_id as root_id3_0_1_ from child children0_ where
        children0_.root_id=1
     */
    @Test
    public void selectLazy() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            Root root = em.find(Root.class, 1L);
            Child child = root.getChildren().get(0);
            Assert.assertNotNull(child);
        } finally {
            em.close();
        }
    }

    /**
     * 1) select root0_.id as id1_1_0_, children1_.id as id1_0_1_, root0_.name as name2_1_0_, children1_.name
     as name2_0_1_, children1_.root_id as root_id3_0_1_, children1_.root_id as root_id3_0_0__, children1_.id
     as id1_0_0__ from root root0_ inner join child children1_ on root0_.id=children1_.root_id
     */
    @Test
    public void selectHql() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            List<Root> list = em.createQuery("SELECT r FROM Root r", Root.class)
                    .getResultList();
            Assert.assertNotNull(list);
        } finally {
            em.close();
        }
    }
    /**
     * 1) select root0_.id as id1_1_0_, children1_.id as id1_0_1_, root0_.name as name2_1_0_, children1_.name
     as name2_0_1_, children1_.root_id as root_id3_0_1_, children1_.root_id as root_id3_0_0__, children1_.id
     as id1_0_0__ from root root0_ inner join child children1_ on root0_.id=children1_.root_id
     */
    @Test
    public void selectHqlJoinFetch() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            List<Root> list = em.createQuery("SELECT r FROM Root r LEFT JOIN FETCH r.children", Root.class)
                    .getResultList();
            Assert.assertNotNull(list);
        } finally {
            em.close();
        }
    }
    /**
     * 1) select root0_.id as id1_1_0_, children1_.id as id1_0_1_, root0_.name as name2_1_0_, children1_.name
     as name2_0_1_, children1_.root_id as root_id3_0_1_, children1_.root_id as root_id3_0_0__, children1_.id
     as id1_0_0__ from root root0_ inner join child children1_ on root0_.id=children1_.root_id
     !!!! NO LIMIT IN GENERATED SQL !!!!
     */
    @Test
    public void selectHqlJoinFetchMaxResults() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            List<Root> list = em.createQuery("SELECT r FROM Root r LEFT JOIN FETCH r.children", Root.class)
                    .setMaxResults(1)
                    .getResultList();
            Assert.assertNotNull(list);
            Assert.assertEquals(list.size(), 1);
        } finally {
            em.close();
        }
    }


}
