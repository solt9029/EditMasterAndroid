package com.solt9029.editmasterandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Score {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("bpm")
    @Expose
    private Float bpm;
    @SerializedName("offset")
    @Expose
    private Float offset;
    @SerializedName("speed")
    @Expose
    private Float speed;
    @SerializedName("notes")
    @Expose
    private List<Integer> notes;
    @SerializedName("advanced_settings")
    @Expose
    private Object advancedSettings;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    private List<Integer> states;

    public Score(String username, String comment, String videoId, Float bpm, Float offset, Float speed, List<Integer> notes) {
        this.username = username;
        this.comment = comment;
        this.videoId = videoId;
        this.bpm = bpm;
        this.offset = offset;
        this.speed = speed;
        this.notes = notes;
        // TODO: initialize states here.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Float getBpm() {
        return bpm;
    }

    public void setBpm(Float bpm) {
        this.bpm = bpm;
    }

    public Float getOffset() {
        return offset;
    }

    public void setOffset(Float offset) {
        this.offset = offset;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public List<Integer> getNotes() {
        return notes;
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
    }

    public List<Integer> getStates() {
        return states;
    }

    public void setStates(List<Integer> states) {
        this.states = states;
    }

    public Object getAdvancedSettings() {
        return advancedSettings;
    }

    public void setAdvancedSettings(Object advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}