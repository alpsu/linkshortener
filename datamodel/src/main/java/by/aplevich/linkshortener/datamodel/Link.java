package by.aplevich.linkshortener.datamodel;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Details of link
 */
@Entity
@Table(name = "link", uniqueConstraints = {
        @UniqueConstraint(columnNames = "code")})
public class Link extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "url", nullable = false, length = 500)
    @NotNull
    @Min(value = 0)
    @Max(value = 500)
    private String url;

    @Column(name = "code", unique = true, nullable = false, length = 20)
    @NotNull
    @Min(value = 0)
    @Max(value = 20)
    private String code;

    @Column(name = "quantity", nullable = false)
    @NotNull
    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private Integer quantity;

    @Column(name = "description", nullable = true, length = 500)
    @Min(value = 0)
    @Max(value = 500)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = UserAccount.class)
    @NotNull
    private UserAccount userAccount;

    @ManyToMany(targetEntity = Teg.class, fetch = FetchType.EAGER)
    @JoinTable(name = "link_2_teg", joinColumns = {@JoinColumn(name = "link_id", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "teg_id", referencedColumnName = "ID")})
    private Set<Teg> tegs = new HashSet<>();

    public Set<Teg> getTegs() {
        return tegs;
    }

    public void setTegs(Set<Teg> tegs) {
        this.tegs = tegs;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(final UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "Link{"
                + ", url='" + url + '\''
                + ", code='" + code + '\''
                + ", quantity=" + quantity
                + ", description='" + description + '\'' + '}';
    }
}
