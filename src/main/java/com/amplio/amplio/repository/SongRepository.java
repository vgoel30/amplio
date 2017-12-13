package com.amplio.amplio.repository;

import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import com.amplio.amplio.models.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

import static com.amplio.amplio.constants.Constants.SONG_QUERY_LIMIT;

public interface SongRepository extends CrudRepository<Song, Integer> {
  @Query(value = "select s.id from song_genre_enum g join song s on s.id = g.song_id " +
      "where g.genre_enum = :genre group by s.song_name limit " + SONG_QUERY_LIMIT, nativeQuery = true)
  Set<Integer> findSongsByGenre(@Param("genre") String genre);

  Song findSongById(Integer songId);
  List<Song> findTop25SongsByArtistOrderByNumberPlaysDesc(Artist Artist);
  List<Song> findSongsByAlbum(Album album);

  List<Song> findTop10SongsBySongNameContainingOrderByNumberPlaysDesc(String songName);
}
