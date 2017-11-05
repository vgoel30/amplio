package com.amplio.amplio.models;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class SongCollection {

    @NotNull
    private String title;
    @NotNull
    private List<Song> songs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
