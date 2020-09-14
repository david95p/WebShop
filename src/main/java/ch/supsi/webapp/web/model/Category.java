package ch.supsi.webapp.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Category {
    @Id
    private String catName;

    public Category() {
    }

    public Category(String name) {
        this.catName = name;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return catName.equalsIgnoreCase(category.catName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catName);
    }
}
