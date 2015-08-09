package by.aplevich.linkshortener.datamodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Contains user data
 */
@Entity
public class UserAccount extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Size(max = 100)
    private String name;

    @Column
    @NotNull
    @Size(max = 60, min = 6)
    private String login;

    @Column
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
        result = 31 * result + login.hashCode();
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
