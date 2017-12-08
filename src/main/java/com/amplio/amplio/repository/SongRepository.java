package com.amplio.amplio.repository;

import com.amplio.amplio.models.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SongRepository extends CrudRepository<Song, Integer> {
  @Query(value = "select s.song_id from song_genre_enum g join song s on s.song_id = g.song_song_id " +
      "where g.genre_enum = :genre group by s.song_name limit 20", nativeQuery = true)
  Set<Integer> getSongsByGenreEnum(@Param("genre") String genre);
  Song getSongBySongId(Integer songId);
}
