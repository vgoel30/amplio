package com.amplio.amplio.repository;

import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.amplio.amplio.constants.Constants.SONG_QUERY_LIMIT;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
  Album findById(Integer albumId);

  List<Album> findAlbumsByArtist(Artist artist);

  List<Album> findTop10AlbumsByTitleContaining(String albumName);
  @Query(value = "select min(id) from album where genre = :genre and title !=  :name group by title limit " + SONG_QUERY_LIMIT, nativeQuery = true)
  List<Integer> findRelatedAlbums(@Param("genre") String genre, @Param("name") String name);
}
