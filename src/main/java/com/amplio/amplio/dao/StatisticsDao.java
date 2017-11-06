package com.amplio.amplio.dao;

import com.amplio.amplio.models.Statistics;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StatisticsDao extends CrudRepository<Statistics, UUID> {
}
