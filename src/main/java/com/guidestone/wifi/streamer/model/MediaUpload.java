package com.guidestone.wifi.streamer.model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MediaUpload {

    private List<MultipartFile> media;

    private List<String> radios;

    private List<String> language;

    private List<String> genre;

    private String movies;

    private String tv;

    private String videos;

    public List<MultipartFile> getFiles() {
        return media;
    }

    public void setFiles(List<MultipartFile> files) {
        this.media = files;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public List<String> getRadios() {
        return radios;
    }

    public void setRadios(List<String> radios) {
        this.radios = radios;
    }

    public String getMovies() {
        return movies;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }
}
