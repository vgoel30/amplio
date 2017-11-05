package com.amplio.amplio.models;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class SongQueue {

    @NotNull
    private UUID qID;
    @OneToMany
    private List<Song> songs;
    private Integer currentSongIndex;
    @NotNull
    private Boolean isRadio;
    @NotNull
    private Boolean repeat;
    @NotNull
    private Boolean shuffle;

    public SongQueue(){
        this.isRadio = false;
        this.repeat = false;
        this.shuffle = false;
    }
    public void toggleRepeat(Boolean toggle){
        repeat = toggle;
    }
    public void toggleShuffle(Boolean toggle){
        shuffle = toggle;
    }
    public Song getNextSong(){
        return songs.get(currentSongIndex + 1);
    }
    public Song getPrevSong(){
        return songs.get(currentSongIndex - 1);
    }
}
