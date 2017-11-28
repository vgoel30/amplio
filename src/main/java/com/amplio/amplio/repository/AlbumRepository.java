package com.amplio.amplio.repository;

import com.amplio.amplio.models.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AlbumRepository extends CrudRepository<Album, UUID> {
}
