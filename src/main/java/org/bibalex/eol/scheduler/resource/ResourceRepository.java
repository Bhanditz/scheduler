package org.bibalex.eol.scheduler.resource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Created by sara.mustafa on 4/18/17.
 */
@RepositoryRestResource
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    public List<Resource> findByContentPartnerId(long contentPartnerId);
    public Optional<Resource> findById(long id);
}
