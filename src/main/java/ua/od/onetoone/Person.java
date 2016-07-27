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
@Table(name = "person")
class Person {
    @Id
    long id = -1L;
    String name;

    @OneToOne()
            @JoinColumn(name = "salary_id")
    Salary salary;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
}

