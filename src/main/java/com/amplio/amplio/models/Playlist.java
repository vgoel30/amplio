package com.amplio.amplio.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Playlist extends SongCollection
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID playlistID;

    private String image;

    @NotNull
    private User owner;

    @NotNull
    private boolean isPublic;


    public void addSong(Song song)
    {
        getSongs().add(song);
    }

    public void removeSong(Song song)
    {
        getSongs().remove(song);
    }
}
