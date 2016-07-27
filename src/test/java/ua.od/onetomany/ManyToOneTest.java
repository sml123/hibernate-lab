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
     1) select child0_.id as id1_0_0_, child0_.name as name2_0_0_, child0_.root_id as root_id3_0_0_,
     root1_.id as id1_2_1_, root1_.name as name2_2_1_ from child child0_ left outer join root root1_
     on child0_.root_id=root1_.id where child0_.id=1
     */
    @Test
    public void simpleSelect() throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            Child child = em.find(Child.class, 1L);
            Assert.assertNotNull(child.getRoot().getName());
        } finally {
            em.close();
        }

    }

    /**
     1) select child0_.id as id1_0_, child0_.name as name2_0_, child0_.root_id as root_id3_0_ from
     child child0_ where child0_.id=1
     2) select root0_.id as id1_2_0_, root0_.name as name2_2_0_ from root root0_ where root0_.id=1
     */
    @Test
    public void simpleSelectHql() throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            Child child = em.createQuery("SELECT c FROM Child c WHERE c.id = :id", Child.class)
                    .setParameter("id", 1L).getSingleResult();
            Assert.assertNotNull(child.getRoot().getName());
        } finally {
            em.close();
        }

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
