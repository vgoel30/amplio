package com.amplio.amplio.dao;

import com.amplio.amplio.models.Advertiser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AdvertiserDao extends CrudRepository<Advertiser, UUID> {

}
