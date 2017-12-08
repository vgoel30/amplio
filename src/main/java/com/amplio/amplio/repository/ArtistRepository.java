package com.amplio.amplio.repository;

import com.amplio.amplio.models.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
  public Artist findByArtistId(Integer artistId);
}
