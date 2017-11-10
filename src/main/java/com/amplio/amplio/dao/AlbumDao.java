package com.amplio.amplio.dao;

import com.amplio.amplio.models.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AlbumDao extends CrudRepository<Album, UUID> {
}
