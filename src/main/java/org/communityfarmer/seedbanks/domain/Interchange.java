package org.communityfarmer.seedbanks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Interchange.
 */
@Entity
@Table(name = "T_INTERCHANGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Interchange implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "state")
    private String state;

    @ManyToOne
    private User farmerReciever;

    @ManyToOne
    private Harvest harvest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getFarmerReciever() {
        return farmerReciever;
    }

    public void setFarmerReciever(User user) {
        this.farmerReciever = user;
    }

    public Harvest getHarvest() {
        return harvest;
    }

    public void setHarvest(Harvest harvest) {
        this.harvest = harvest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Interchange interchange = (Interchange) o;

        if (id != null ? !id.equals(interchange.id) : interchange.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Interchange{" +
                "id=" + id +
                ", score='" + score + "'" +
                ", state='" + state + "'" +
                '}';
    }
}
