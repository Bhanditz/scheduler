package org.bibalex.eol.scheduler.content_partner;

import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by sara.mustafa on 4/11/17.
 */

@Service
public class ContentPartnerService {
    private static final Logger logger = LoggerFactory.getLogger(ContentPartnerService.class);
    @Autowired
    private ContentPartnerRepository contentPartnerRepository;

    public long createContentPartner(ContentPartner partner, byte [] logoFile, String logoName) throws SQLException{
            logger.debug("Content partner Service create new ");
            partner.setLogo(logoFile);
            partner.setLogo_type(getFileExtension(logoName));
            return contentPartnerRepository.save(partner).getId();
    }
    public long createContentPartner(ContentPartner partner){
        logger.debug("Content partner Service create new ");
        return contentPartnerRepository.save(partner).getId();
    }

    public ContentPartner updateContentPartner(long id, ContentPartner partner){
        logger.debug("Content partner Service update id "+id);
        validateContentPartner(id);
        partner.setId(id);
        return contentPartnerRepository.save(partner);
//        ContentPartner currentPartner = contentPartnerRepository.findOne(id);
//        if (currentPartner == null){
//            throw new NotFoundException("content partner", id);
//        }
//        currentPartner.setName(partner.getName());
//        currentPartner.setAbbreviation(partner.getAbbreviation());
//        currentPartner.setDescription(partner.getDescription());
//        currentPartner.setUrl(partner.getUrl());
//        currentPartner.setLogo(null);
//        currentPartner.setLogo_type(null);
 //       return contentPartnerRepository.save(currentPartner);
    }

    public ContentPartner updateContentPartner(long id, ContentPartner partner, byte [] logoFile, String logoName) throws SQLException {
        logger.debug("Content partner Service update id "+id);
        validateContentPartner(id);
        partner.setLogo(logoFile);
        partner.setLogo_type(getFileExtension(logoName));
        partner.setId(id);
        return contentPartnerRepository.save(partner);
//        ContentPartner currentPartner = contentPartnerRepository.findOne(id);
//        if (currentPartner == null){
//            throw new NotFoundException("content partner", id);
//        }
//        currentPartner.setName(partner.getName());
//        currentPartner.setAbbreviation(partner.getAbbreviation());
//        currentPartner.setDescription(partner.getDescription());
//        currentPartner.setUrl(partner.getUrl());
//        currentPartner.setLogo(logoFile);
//        currentPartner.setLogo_type(getFileExtension(logoName));
//        return contentPartnerRepository.save(currentPartner);
    }

    public List<ContentPartner> getAllContentPartners(){
        List<ContentPartner> partners = new ArrayList<>();
        contentPartnerRepository.findAll().forEach(partners::add);
        return partners;
    }

    public Collection<ContentPartner> getContentPartners(String partnerIds){
        logger.debug("Content partner service: get content partners with ids : "+partnerIds);
        if (partnerIds == null || partnerIds != null && partnerIds.length() == 0)
            throw  new NotFoundException("content partner", partnerIds);
        return contentPartnerRepository.findByIdIn(Arrays.asList(partnerIds.split("\\s*,\\s*")).stream().map(Long::valueOf).collect(Collectors.toList())).orElseThrow(
                () -> new NotFoundException("content partner", partnerIds));
    }

    public ContentPartner getContentPartner(long id){
        return contentPartnerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("content partner", id));
    }

    public void validateContentPartner(long partnerId) {
        contentPartnerRepository.findById(partnerId).orElseThrow(
                () -> new NotFoundException("content partner", partnerId));
    }

    private String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }


}
