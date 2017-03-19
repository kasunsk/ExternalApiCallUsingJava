package com.crossover.techtrial.java.se.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Abstract Entity class for tracking
 */
@MappedSuperclass
public abstract class AbstractTrackableEntity {

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", insertable = false, updatable = false)
    protected Date createdDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE", insertable = false, updatable = false)
    protected Date lastModifiedDate;

    @Version
    @Column(name = "VERSION")
    protected Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
