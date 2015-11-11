package com.guidestone.wifi.streamer.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by z013w8c on 11/11/15.
 */
@Entity
@Table(name = "media", schema = "", catalog = "wifistreamer")
public class MediaEntity {
    private int id;
    private String title;
    private String mediaLocation;
    private String coverImageLocation;
    private Long mediaDuration;
    private String mediaGenre;
    private String mediaLanguage;
    private String mediaCategory;
    private String rating;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "media_location")
    public String getMediaLocation() {
        return mediaLocation;
    }

    public void setMediaLocation(String mediaLocation) {
        this.mediaLocation = mediaLocation;
    }

    @Basic
    @Column(name = "cover_image_location")
    public String getCoverImageLocation() {
        return coverImageLocation;
    }

    public void setCoverImageLocation(String coverImageLocation) {
        this.coverImageLocation = coverImageLocation;
    }

    @Basic
    @Column(name = "media_duration")
    public Long getMediaDuration() {
        return mediaDuration;
    }

    public void setMediaDuration(Long mediaDuration) {
        this.mediaDuration = mediaDuration;
    }

    @Basic
    @Column(name = "media_genre")
    public String getMediaGenre() {
        return mediaGenre;
    }

    public void setMediaGenre(String mediaGenre) {
        this.mediaGenre = mediaGenre;
    }

    @Basic
    @Column(name = "media_language")
    public String getMediaLanguage() {
        return mediaLanguage;
    }

    public void setMediaLanguage(String mediaLanguage) {
        this.mediaLanguage = mediaLanguage;
    }

    @Basic
    @Column(name = "media_category")
    public String getMediaCategory() {
        return mediaCategory;
    }

    public void setMediaCategory(String mediaCategory) {
        this.mediaCategory = mediaCategory;
    }

    @Basic
    @Column(name = "rating")
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaEntity that = (MediaEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (mediaLocation != null ? !mediaLocation.equals(that.mediaLocation) : that.mediaLocation != null)
            return false;
        if (coverImageLocation != null ? !coverImageLocation.equals(that.coverImageLocation) : that.coverImageLocation != null)
            return false;
        if (mediaDuration != null ? !mediaDuration.equals(that.mediaDuration) : that.mediaDuration != null)
            return false;
        if (mediaGenre != null ? !mediaGenre.equals(that.mediaGenre) : that.mediaGenre != null) return false;
        if (mediaLanguage != null ? !mediaLanguage.equals(that.mediaLanguage) : that.mediaLanguage != null)
            return false;
        if (mediaCategory != null ? !mediaCategory.equals(that.mediaCategory) : that.mediaCategory != null)
            return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (mediaLocation != null ? mediaLocation.hashCode() : 0);
        result = 31 * result + (coverImageLocation != null ? coverImageLocation.hashCode() : 0);
        result = 31 * result + (mediaDuration != null ? mediaDuration.hashCode() : 0);
        result = 31 * result + (mediaGenre != null ? mediaGenre.hashCode() : 0);
        result = 31 * result + (mediaLanguage != null ? mediaLanguage.hashCode() : 0);
        result = 31 * result + (mediaCategory != null ? mediaCategory.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
