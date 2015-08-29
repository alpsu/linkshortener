package by.aplevich.linkshortener.datamodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Tag details
 */

@Entity
@Table(name = "teg", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class Teg extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teg)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Teg teg = (Teg) o;

        if (!getId().equals(teg.getId())) return false;
        return getName().equals(teg.getName());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Teg{"
                + "name='" + name + '\''
                + '}';
    }
}
