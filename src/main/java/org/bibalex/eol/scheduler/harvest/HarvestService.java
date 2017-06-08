package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.Resource;
import org.bibalex.eol.scheduler.resource.ResourcePositionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sara.mustafa on 5/2/17.
 */
@Service
public class HarvestService {

    private static final Logger logger = LoggerFactory.getLogger(HarvestService.class);
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    @PersistenceContext
    private EntityManager entityManager;
    private PriorityQueue<Resource> resourcePriorityQueue;


    @PostConstruct
    private void init(){
        resourcePriorityQueue = new PriorityQueue<Resource>(new ResourcePositionComparator());
        Date midnight = new Date();
        midnight.setHours(23);
        midnight.setMinutes(30);
        midnight.setSeconds(0);
        long initialDelay = new Date(midnight.getTime()-System.currentTimeMillis()).getTime();
        executor.scheduleAtFixedRate(()->{
            logger.debug("HarvestService get resources to be harvested from DB");
            StoredProcedureQuery storedProcedure = entityManager.createNamedStoredProcedureQuery("harvestResource");
            storedProcedure.setParameter("cDate",new Date(), TemporalType.DATE);
            List<Resource> resList = storedProcedure.getResultList();
            resList.forEach( r ->  resourcePriorityQueue.add(r));
        }, initialDelay , 86400000L, TimeUnit.MILLISECONDS);

    }

    @PreDestroy
    private void destroy(){
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            logger.error("executor tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                logger.error("cancel non-finished executor tasks");
            }
            executor.shutdownNow();
            logger.error("executor shutdown finished");
        }

    }

}
