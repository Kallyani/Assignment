package com.ays.assignment.model;

/**
 * Created by Kuhu on 5/1/2016.
 */
public class Canada {

    private String title, thumbnailUrl, description;

    public Canada() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Canada(String name, String thumbnailUrl, String description) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;

    }
}
