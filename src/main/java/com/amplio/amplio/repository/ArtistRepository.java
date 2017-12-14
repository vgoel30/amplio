package com.amplio.amplio.repository;

import com.amplio.amplio.models.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;

import static com.amplio.amplio.constants.Constants.SONG_QUERY_LIMIT;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
  Artist findById(Integer artistId);

  List<Artist> findTop10ArtistsByNameContaining(String artistName);

  @Query(value = "SELECT id from artist where genre = :genre and name != :name limit " + SONG_QUERY_LIMIT, nativeQuery = true)
  List<Integer> findRelatedArtists(@Param("genre") String genre, @Param("name") String name);
}
