package com.guidestone.wifi.streamer.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by z013w8c on 11/11/15.
 */
@Entity
@Table(name = "analytics", schema = "", catalog = "wifistreamer")
public class AnalyticsEntity {
    private int id;
    private int mediaId;
    private int userId;
    private int views;
    private Timestamp lastViewed;
    private Long lastViewDurationSeconds;

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

    @Basic
    @Column(name = "views")
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Basic
    @Column(name = "last_viewed")
    public Timestamp getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(Timestamp lastViewed) {
        this.lastViewed = lastViewed;
    }

    @Basic
    @Column(name = "last_view_duration_seconds")
    public Long getLastViewDurationSeconds() {
        return lastViewDurationSeconds;
    }

    public void setLastViewDurationSeconds(Long lastViewDurationSeconds) {
        this.lastViewDurationSeconds = lastViewDurationSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnalyticsEntity that = (AnalyticsEntity) o;

        if (id != that.id) return false;
        if (mediaId != that.mediaId) return false;
        if (userId != that.userId) return false;
        if (views != that.views) return false;
        if (lastViewed != null ? !lastViewed.equals(that.lastViewed) : that.lastViewed != null) return false;
        if (lastViewDurationSeconds != null ? !lastViewDurationSeconds.equals(that.lastViewDurationSeconds) : that.lastViewDurationSeconds != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + mediaId;
        result = 31 * result + userId;
        result = 31 * result + views;
        result = 31 * result + (lastViewed != null ? lastViewed.hashCode() : 0);
        result = 31 * result + (lastViewDurationSeconds != null ? lastViewDurationSeconds.hashCode() : 0);
        return result;
    }
}
