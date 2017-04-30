package org.bibalex.eol.scheduler.content_partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Created by sara.mustafa on 4/11/17.
 */

@RestController
@RequestMapping("/contentPartners")
public class ContentPartnerController {
    private static final Logger logger = LoggerFactory.getLogger(ContentPartnerController.class);
    @Autowired
    private ContentPartnerService contentPartnerService;

    @RequestMapping( method = RequestMethod.POST, params = "partner")
    @ResponseBody
    public Callable<ResponseEntity<?>> createContentPartner(@RequestPart(required = false) MultipartFile logo, @RequestPart ContentPartner partner) throws IOException, SQLException {
        logger.debug("Create content partner request ->"+ partner.toString());
        if (logo != null)
            return () -> ResponseEntity.ok(contentPartnerService.createContentPartner(partner, logo.getBytes(), logo.getOriginalFilename()));
        else
            return () -> ResponseEntity.ok(contentPartnerService.createContentPartner(partner));
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public Callable<ResponseEntity<?>> createContentPartner(@RequestPart(required = false) MultipartFile logo, @RequestPart String name, @RequestPart(required = false) String abbreviation,
                                     @RequestPart String description, @RequestPart(required = false) String url)
            throws IOException, SQLException {
        ContentPartner partner = new ContentPartner();
        partner.setName(name);
        partner.setAbbreviation(abbreviation);
        partner.setDescription(description);
        partner.setUrl(url);
        logger.debug("Create content partner request -> "+ partner.toString());
        if (logo != null)
            return () -> ResponseEntity.ok(contentPartnerService.createContentPartner(partner, logo.getBytes(), logo.getOriginalFilename()));
        else
            return () -> ResponseEntity.ok(contentPartnerService.createContentPartner(partner));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, params = "partner")
    public Callable<ResponseEntity<?>> updateContentPartner(@PathVariable long id, @RequestPart(required = false) MultipartFile logo, @RequestPart ContentPartner partner) throws IOException, SQLException {
        logger.debug("Update content partner id: "+id +" with vals ->"+ partner.toString());
        if (logo != null)
            return () -> ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner, logo.getBytes(), logo.getOriginalFilename()));
        else
            return () -> ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Callable<ResponseEntity<?>> updateContentPartner(@PathVariable long id, @RequestPart(required = false) MultipartFile logo, @RequestPart String name, @RequestPart(required = false) String abbreviation,
                                     @RequestPart String description, @RequestPart(required = false) String url) throws IOException, SQLException {
        ContentPartner partner = new ContentPartner();
        partner.setName(name);
        partner.setAbbreviation(abbreviation);
        partner.setDescription(description);
        partner.setUrl(url);
        logger.debug("Update content partner id: "+id +" with vals ->"+ partner.toString());
        if (logo != null)
            return () -> ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner, logo.getBytes(), logo.getOriginalFilename()));
        else
            return () -> ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<ResponseEntity<Collection<ContentPartner>>> getContentPartners(@RequestParam String ids){
        logger.debug("Get content partners with ids: "+ids);
        return () -> ResponseEntity.ok(contentPartnerService.getContentPartners(ids));
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ContentPartner> getContentPartner(@PathVariable long id){
        logger.debug("Content partner controller get partner with id: "+id);
        return ResponseEntity.ok(contentPartnerService.getContentPartner(id));
    }
}
