package com.amplio.amplio.repository;

import com.amplio.amplio.models.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Integer> {
}
