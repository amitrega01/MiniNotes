package com.dev.insu.mininotes;

import java.util.ArrayList;

/**
 * Created by amitr on 19.11.2017.
 */

public class NoteModel {
    private String title;
    private String content;
    private String date;
    private boolean active;

    public NoteModel() {
    }

    public NoteModel(String title, String content, String date, boolean active) {
        this.title = title;
        this.content = content;
        this.active = active;
        this.date = date;
    }

    public NoteModel(String content, String date, boolean active) {
        this.content = content;
        this.active = active;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
