package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "qID")
)

public class SongQueue {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID qID;
    @NotNull
    private User user;
    @OneToMany
    private List<Song> songs;
    private Integer currentSongIndex;
    @NotNull
    private Boolean isRadio;
    @NotNull
    private Boolean repeat;
    @NotNull
    private Boolean shuffle;

    public SongQueue(User user){
        this.user = user;
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
