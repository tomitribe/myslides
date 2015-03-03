package com.github.rmannibucau.myslides.service.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@NamedQueries({
    @NamedQuery(name = "Slide.findByUser", query = "select s from Slide s where s.user = :user")
})
public class Slide {
    @Id
    @GeneratedValue
    private long id;

    private String user;
    private String name;

    @Lob
    private String content;

    @Temporal(TIMESTAMP)
    private Date created;

    @Temporal(TIMESTAMP)
    private Date updated;

    @PreUpdate
    public void update() {
        updated = new Date();        
    }

    @PrePersist
    public void created() {
        created = new Date();
        updated = created;        
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String raw) {
        this.content = raw;
    }
}
