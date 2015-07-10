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
    private double koefficient;

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
    private Teg tegone;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tegtwo;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tegthree;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tegfour;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teg.class)
    private Teg tegfive;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getKoefficient() {
        return koefficient;
    }

    public void setKoefficient(double koefficient) {
        this.koefficient = koefficient;
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

    public Teg getTegone() {
        return tegone;
    }

    public void setTegone(Teg tegone) {
        this.tegone = tegone;
    }

    public Teg getTegtwo() {
        return tegtwo;
    }

    public void setTegtwo(Teg tegtwo) {
        this.tegtwo = tegtwo;
    }

    public Teg getTegthree() {
        return tegthree;
    }

    public void setTegthree(Teg tegthree) {
        this.tegthree = tegthree;
    }

    public Teg getTegfour() {
        return tegfour;
    }

    public void setTegfour(Teg tegfour) {
        this.tegfour = tegfour;
    }

    public Teg getTegfive() {
        return tegfive;
    }

    public void setTegfive(Teg tegfive) {
        this.tegfive = tegfive;
    }

    @Override
    public String toString() {
        return "Link{" +
                "koefficient=" + koefficient +
                ", url='" + url + '\'' +
                ", code='" + code + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", userAccount=" + userAccount +
                '}';
    }
}