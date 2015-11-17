package com.guidestone.wifi.streamer.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.guidestone.wifi.streamer.entities.MediaEntity;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "version",
        "last_updated_on",
        "s3bucket",
        "categories",
        "checkdays",
        "videos",
        "books",
        "audios",
        "movies",
        "tv"
})
public class MediaUpdates {

    @JsonProperty("version")
    private String version;
    @JsonProperty("last_updated_on")
    private String lastUpdatedOn;
    @JsonProperty("s3bucket")
    private String s3bucket;
    @JsonProperty("categories")
    private List<String> categories = new ArrayList<String>();
    @JsonProperty("checkdays")
    private String checkdays;
    @JsonProperty("videos")
    private List<MediaEntity> videos = new ArrayList<>();
    @JsonProperty("audios")
    private List<MediaEntity> audios = new ArrayList<>();
    @JsonProperty("movies")
    private List<MediaEntity> movies = new ArrayList<>();
    @JsonProperty("tv")
    private List<MediaEntity> tv = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The version
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     * The version
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return
     * The lastUpdatedOn
     */
    @JsonProperty("last_updated_on")
    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    /**
     *
     * @param lastUpdatedOn
     * The last_updated_on
     */
    @JsonProperty("last_updated_on")
    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    /**
     *
     * @return
     * The s3bucket
     */
    @JsonProperty("s3bucket")
    public String getS3bucket() {
        return s3bucket;
    }

    /**
     *
     * @param s3bucket
     * The s3bucket
     */
    @JsonProperty("s3bucket")
    public void setS3bucket(String s3bucket) {
        this.s3bucket = s3bucket;
    }

    /**
     *
     * @return
     * The categories
     */
    @JsonProperty("categories")
    public List<String> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    @JsonProperty("categories")
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     *
     * @return
     * The checkdays
     */
    @JsonProperty("checkdays")
    public String getCheckdays() {
        return checkdays;
    }

    /**
     *
     * @param checkdays
     * The checkdays
     */
    @JsonProperty("checkdays")
    public void setCheckdays(String checkdays) {
        this.checkdays = checkdays;
    }

    /**
     *
     * @return
     * The videos
     */
    @JsonProperty("videos")
    public List<MediaEntity> getVideos() {
        return videos;
    }

    /**
     *
     * @param videos
     * The videos
     */
    @JsonProperty("videos")
    public void setVideos(List<MediaEntity> videos) {
        this.videos = videos;
    }

    /**
     *
     * @return
     * The audios
     */
    @JsonProperty("audios")
    public List<MediaEntity> getAudios() {
        return audios;
    }

    /**
     *
     * @param audios
     * The audios
     */
    @JsonProperty("audios")
    public void setAudios(List<MediaEntity> audios) {
        this.audios = audios;
    }

    /**
     *
     * @return
     * The movies
     */
    @JsonProperty("movies")
    public List<MediaEntity> getMovies() {
        return movies;
    }

    /**
     *
     * @param movies
     * The movies
     */
    @JsonProperty("movies")
    public void setMovies(List<MediaEntity> movies) {
        this.movies = movies;
    }

    /**
     *
     * @return
     * The tv
     */
    @JsonProperty("tv")
    public List<MediaEntity> getTv() {
        return tv;
    }

    /**
     *
     * @param tv
     * The tv
     */
    @JsonProperty("tv")
    public void setTv(List<MediaEntity> tv) {
        this.tv = tv;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}