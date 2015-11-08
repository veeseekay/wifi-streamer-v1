package com.guidestone.wifi.streamer.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by z013w8c on 11/1/15.
 */
@Entity
@Table(name = "analytics", schema = "", catalog = "wifistreamer")
public class AnalyticsEntity {
    private int id;
    private int mediaId;
    private int userId;
    private Integer views;
    private Timestamp lastViewedOn;
    private Double lastViewedDuration;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "media_id")
    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnalyticsEntity that = (AnalyticsEntity) o;

        if (id != that.id) return false;
        if (mediaId != that.mediaId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + mediaId;
        result = 31 * result + userId;
        return result;
    }

    @Basic
    @Column(name = "views")
    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Basic
    @Column(name = "last_viewed_on")
    public Timestamp getLastViewedOn() {
        return lastViewedOn;
    }

    public void setLastViewedOn(Timestamp lastViewedOn) {
        this.lastViewedOn = lastViewedOn;
    }

    @Basic
    @Column(name = "last_viewed_duration")
    public Double getLastViewedDuration() {
        return lastViewedDuration;
    }

    public void setLastViewedDuration(Double lastViewedDuration) {
        this.lastViewedDuration = lastViewedDuration;
    }
}
