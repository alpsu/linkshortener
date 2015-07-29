package by.aplevich.linkshortener.datamodel;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Details of link
 */
@Entity
public class Link extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Min(value = 0)
    @Max(value = 500)
    private String url;

    @Column
    @NotNull
    @Min(value = 0)
    @Max(value = 500)
    private String code;

    @Column
    @NotNull
    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private Integer quantity;

    @Column
    @Min(value = 0)
    @Max(value = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
    @NotNull
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tagone;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tagtwo;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tagthree;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tagfour;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tagfive;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Teg getTagone() {
        return tagone;
    }

    public void setTagone(Teg tagone) {
        this.tagone = tagone;
    }

    public Teg getTagtwo() {
        return tagtwo;
    }

    public void setTagtwo(Teg tagtwo) {
        this.tagtwo = tagtwo;
    }

    public Teg getTagthree() {
        return tagthree;
    }

    public void setTagthree(Teg tagthree) {
        this.tagthree = tagthree;
    }

    public Teg getTagfour() {
        return tagfour;
    }

    public void setTagfour(Teg tagfour) {
        this.tagfour = tagfour;
    }

    public Teg getTagfive() {
        return tagfive;
    }

    public void setTagfive(Teg tagfive) {
        this.tagfive = tagfive;
    }

    @Override
    public String toString() {
        return "Link{" +
                ", url='" + url + '\'' +
                ", code='" + code + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' + '}';
    }
}