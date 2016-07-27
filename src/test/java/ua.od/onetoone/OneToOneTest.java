package ua.od.onetoone;

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
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * Created by sshumilov on 25.07.16.
 */
public class OneToOneTest {

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
     * 1)select person0_.id as id1_1_0_, person0_.name as name2_1_0_, person0_.salary_id as salary_i3_1_0_,
     salary1_.id as id1_3_1_, salary1_.amounth as amounth2_3_1_ from person person0_ left outer
     join salary salary1_ on person0_.salary_id=salary1_.id where person0_.id=1
     */
    @Test
    public void testSelect() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            Person root = em.find(Person.class, 1L);
            Salary child = root.getSalary();
            Assert.assertNotNull(child);
        } finally {
            em.close();
        }
    }

    /**
     *
     1) select person0_.id as id1_1_, person0_.name as name2_1_, person0_.salary_id as salary_i3_1_
     from person person0_
     2) select salary0_.id as id1_3_0_, salary0_.amounth as amounth2_3_0_, person1_.id as id1_1_1_,
     person1_.name as name2_1_1_, person1_.salary_id as salary_i3_1_1_ from salary salary0_ left
     outer join person person1_ on salary0_.id=person1_.salary_id where salary0_.id=1
     3) select salary0_.id as id1_3_0_, salary0_.amounth as amounth2_3_0_, person1_.id as id1_1_1_,
     person1_.name as name2_1_1_, person1_.salary_id as salary_i3_1_1_ from salary salary0_ left
     outer join person person1_ on salary0_.id=person1_.salary_id where salary0_.id=2
     4) select salary0_.id as id1_3_0_, salary0_.amounth as amounth2_3_0_, person1_.id as id1_1_1_,
     person1_.name as name2_1_1_, person1_.salary_id as salary_i3_1_1_ from salary salary0_ left
     outer join person person1_ on salary0_.id=person1_.salary_id where salary0_.id=3
     5) select salary0_.id as id1_3_0_, salary0_.amounth as amounth2_3_0_, person1_.id as id1_1_1_,
     person1_.name as name2_1_1_, person1_.salary_id as salary_i3_1_1_ from salary salary0_ left
     outer join person person1_ on salary0_.id=person1_.salary_id where salary0_.id=4
     */
    @Test
    public void testSelectHql() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            List<Person> list = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
            Assert.assertNotNull(list);
            list.get(0).getSalary().getAmounth();
        } finally {
            em.close();
        }
    }

    /**
     *
     1) select person0_.id as id1_1_, person0_.name as name2_1_, person0_.salary_id as salary_i3_1_
     from person person0_
     2) select salary0_.id as id1_3_0_, salary0_.amounth as amounth2_3_0_, person1_.id as id1_1_1_,
     person1_.name as name2_1_1_, person1_.salary_id as salary_i3_1_1_ from salary salary0_ left
     outer join person person1_ on salary0_.id=person1_.salary_id where salary0_.id=1
     3) select salary0_.id as id1_3_0_, salary0_.amounth as amounth2_3_0_, person1_.id as id1_1_1_,
     person1_.name as name2_1_1_, person1_.salary_id as salary_i3_1_1_ from salary salary0_ left
     outer join person person1_ on salary0_.id=person1_.salary_id where salary0_.id=2
     4) select salary0_.id as id1_3_0_, salary0_.amounth as amounth2_3_0_, person1_.id as id1_1_1_,
     person1_.name as name2_1_1_, person1_.salary_id as salary_i3_1_1_ from salary salary0_ left
     outer join person person1_ on salary0_.id=person1_.salary_id where salary0_.id=3
     5) select salary0_.id as id1_3_0_, salary0_.amounth as amounth2_3_0_, person1_.id as id1_1_1_,
     person1_.name as name2_1_1_, person1_.salary_id as salary_i3_1_1_ from salary salary0_ left
     outer join person person1_ on salary0_.id=person1_.salary_id where salary0_.id=4
     */
    @Test
    public void testSelectHqlJoin() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            List<Person> list = em.createQuery("SELECT p FROM Person p LEFT JOIN p.salary AS s", Person.class).getResultList();
            Assert.assertNotNull(list);
            list.get(0).getSalary().getAmounth();
        } finally {
            em.close();
        }
    }

    /**
     1) select person0_.id as id1_1_0_, salary1_.id as id1_3_1_, person0_.name as name2_1_0_, person0_.salary_id
     as salary_i3_1_0_, salary1_.amounth as amounth2_3_1_ from person person0_ left outer join salary
     salary1_ on person0_.salary_id=salary1_.id
     */
    @Test
    public void testSelectHqlJoinFetch() throws Exception {
        log.info("selectLazy");
        EntityManager em = emf.createEntityManager();
        try {
            List<Person> list = em.createQuery("SELECT p FROM Person p LEFT JOIN FETCH p.salary AS s", Person.class).getResultList();
            Assert.assertNotNull(list);
            list.get(0).getSalary().getAmounth();
        } finally {
            em.close();
        }
    }
}
