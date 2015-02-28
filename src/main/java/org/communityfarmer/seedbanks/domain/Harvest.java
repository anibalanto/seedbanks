package org.communityfarmer.seedbanks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.communityfarmer.seedbanks.domain.util.CustomDateTimeDeserializer;
import org.communityfarmer.seedbanks.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Harvest.
 */
@Entity
@Table(name = "T_HARVEST")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Harvest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code_validator")
    private String codeValidator;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date", nullable = false)
    private DateTime date;

    @Column(name = "shared")
    private Boolean shared;

    @ManyToOne
    private User farmer;

    @ManyToOne
    private Harvest mother;

    @OneToMany(mappedBy = "harvest")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interchange> interchanges = new HashSet<>();

    @ManyToOne
    private Variety variety;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeValidator() {
        return codeValidator;
    }

    public void setCodeValidator(String codeValidator) {
        this.codeValidator = codeValidator;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public User getFarmer() {
        return farmer;
    }

    public void setFarmer(User user) {
        this.farmer = user;
    }

    public Harvest getMother() {
        return mother;
    }

    public void setMother(Harvest harvest) {
        this.mother = harvest;
    }

    public Set<Interchange> getInterchanges() {
        return interchanges;
    }

    public void setInterchanges(Set<Interchange> interchanges) {
        this.interchanges = interchanges;
    }

    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Harvest harvest = (Harvest) o;

        if (id != null ? !id.equals(harvest.id) : harvest.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Harvest{" +
                "id=" + id +
                ", codeValidator='" + codeValidator + "'" +
                ", date='" + date + "'" +
                ", shared='" + shared + "'" +
                '}';
    }
}
