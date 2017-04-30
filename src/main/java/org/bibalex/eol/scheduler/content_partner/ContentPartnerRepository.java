package org.bibalex.eol.scheduler.content_partner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Created by sara.mustafa on 4/11/17.
 */
@RepositoryRestResource
public interface ContentPartnerRepository extends CrudRepository<ContentPartner, Long> {
    Optional<ContentPartner> findById(long id);
    Optional<List<ContentPartner>> findByIdIn(List<Long> ids);
}
