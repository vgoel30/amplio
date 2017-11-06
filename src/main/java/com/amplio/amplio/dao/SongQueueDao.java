package com.amplio.amplio.dao;

import com.amplio.amplio.models.SongQueue;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface SongQueueDao extends CrudRepository<SongQueue, UUID> {
}
