package ua.od.onetoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by sshumilov on 25.07.16.
 */
@Entity
@Table(name = "salary")
class Salary {

    @Id
    long id = -1L;

    double amounth = 0.0d;
    @OneToOne(mappedBy = "salary")
    Person person;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmounth() {
        return amounth;
    }

    public void setAmounth(double amounth) {
        this.amounth = amounth;
    }
}
