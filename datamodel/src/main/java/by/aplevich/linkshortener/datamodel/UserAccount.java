package by.aplevich.linkshortener.datamodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Contains user data
 */
@Entity
@Table(name = "user_account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "login")})
public class UserAccount extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "login", unique = true, nullable = false, length = 60)
    @NotNull
    @Size(max = 60, min = 6)
    private String login;

    @Column(name = "password", nullable = false, length = 35)
    @NotNull
    @Size(max = 35, min = 8)
    private String password;

    @Transient
    private String cpassword;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccount)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        UserAccount that = (UserAccount) o;

        return login.equals(that.login);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getLogin().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserAccount{"
                + "name='" + name + '\''
                + ", login='" + login + '\''
                + ", password='" + password + '\''
                + ", cpassword='" + cpassword + '\'' + '}';
    }
}
