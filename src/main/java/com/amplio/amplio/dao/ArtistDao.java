package com.amplio.amplio.dao;

import com.amplio.amplio.models.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArtistDao extends CrudRepository<Artist, UUID> {
}
