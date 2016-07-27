package ua.od.onetomany;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by sshumilov on 22.07.16.
 */
@Entity
@Table(name = "root")
class Root {
    @Id
    private long id = -1L;
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_id")
    private List<Child> children;

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

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
