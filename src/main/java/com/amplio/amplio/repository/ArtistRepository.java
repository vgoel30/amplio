package com.amplio.amplio.repository;

import com.amplio.amplio.models.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
  Artist findById(Integer artistId);

  List<Artist> findTop10ArtistsByNameContaining(String artistName);
}
