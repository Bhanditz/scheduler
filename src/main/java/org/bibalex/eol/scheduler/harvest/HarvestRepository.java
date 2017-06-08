package org.bibalex.eol.scheduler.harvest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by sara.mustafa on 5/2/17.
 */
@RepositoryRestResource
public interface HarvestRepository extends CrudRepository<Harvest, Long>{
    public List<Harvest> findByResourceId(long resourceId);
}
