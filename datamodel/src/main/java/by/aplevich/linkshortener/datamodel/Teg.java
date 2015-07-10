package by.aplevich.linkshortener.datamodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Tag details
 */

@Entity
public class Teg extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teg{" +
                "name='" + name + '\'' +
                '}';
    }
}