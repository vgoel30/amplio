package com.amplio.amplio.repository;

import com.amplio.amplio.models.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArtistRepository extends CrudRepository<Artist, UUID> {
  public Artist getArtistByArtistID(UUID artistID);
}
