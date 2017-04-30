package org.bibalex.eol.scheduler.resource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bibalex.eol.scheduler.content_partner.ContentPartner;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by sara.mustafa on 4/18/17.
 */

@Entity
@JsonIgnoreProperties
public class Resource implements Serializable{

    private enum Type {
        url,
        file
    }
    private enum HarvestFrequency{
        once,
        weekly,
        monthly,
        bimonthly,
        quarterly
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String origin_url;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String path;
    private Date last_harvested_at;
    @Enumerated(EnumType.STRING)
    private HarvestFrequency harvest_frequency;
    private Date day_of_month;
    private int nodes_count;
    private int position = -1;
    private boolean is_paused = false;
    private boolean is_approved = false;
    private boolean is_trusted = false;
    private boolean is_autopublished = false;
    private boolean is_forced = false;
    private int dataset_license = 47;
    private String dataset_rights_statement;
    private String dataset_rights_holder;
    private int default_license_string;
    private String default_rights_statement;
    private String default_rights_holder;
    private int default_language_id = 152;
    @ManyToOne
    @JoinColumn (name="content_partner_id")
    @JsonBackReference
    private ContentPartner contentPartner;

    Resource(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getLast_harvested_at() {
        return last_harvested_at;
    }

    public void setLast_harvested_at(Date last_harvested_at) {
        this.last_harvested_at = last_harvested_at;
    }

    public HarvestFrequency getHarvest_frequency() {
        return harvest_frequency;
    }

    public void setHarvest_frequency(HarvestFrequency harvest_frequency) {
        this.harvest_frequency = harvest_frequency;
    }

    public Date getDay_of_month() {
        return day_of_month;
    }

    public void setDay_of_month(Date day_of_month) {
        this.day_of_month = day_of_month;
    }

    public int getNodes_count() {
        return nodes_count;
    }

    public void setNodes_count(int nodes_count) {
        this.nodes_count = nodes_count;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isIs_paused() {
        return is_paused;
    }

    public void setIs_paused(boolean is_paused) {
        this.is_paused = is_paused;
    }

    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }

    public boolean isIs_trusted() {
        return is_trusted;
    }

    public void setIs_trusted(boolean is_trusted) {
        this.is_trusted = is_trusted;
    }

    public boolean isIs_autopublished() {
        return is_autopublished;
    }

    public void setIs_autopublished(boolean is_autopublished) {
        this.is_autopublished = is_autopublished;
    }

    public boolean isIs_forced() {
        return is_forced;
    }

    public void setIs_forced(boolean is_forced) {
        this.is_forced = is_forced;
    }

    public int getDataset_license() {
        return dataset_license;
    }

    public void setDataset_license(int dataset_license) {
        this.dataset_license = dataset_license;
    }

    public String getDataset_rights_statement() {
        return dataset_rights_statement;
    }

    public void setDataset_rights_statement(String dataset_rights_statement) {
        this.dataset_rights_statement = dataset_rights_statement;
    }

    public String getDataset_rights_holder() {
        return dataset_rights_holder;
    }

    public void setDataset_rights_holder(String dataset_rights_holder) {
        this.dataset_rights_holder = dataset_rights_holder;
    }

    public int getDefault_license_string() {
        return default_license_string;
    }

    public void setDefault_license_string(int default_license_string) {
        this.default_license_string = default_license_string;
    }

    public String getDefault_rights_statement() {
        return default_rights_statement;
    }

    public void setDefault_rights_statement(String default_rights_statement) {
        this.default_rights_statement = default_rights_statement;
    }

    public String getDefault_rights_holder() {
        return default_rights_holder;
    }

    public void setDefault_rights_holder(String default_rights_holder) {
        this.default_rights_holder = default_rights_holder;
    }

    public int getDefault_language_id() {
        return default_language_id;
    }

    public void setDefault_language_id(int default_language_id) {
        this.default_language_id = default_language_id;
    }

    public ContentPartner getContentPartner() {
        return contentPartner;
    }

    public void setContentPartner(ContentPartner contentPartner) {
        this.contentPartner = contentPartner;
    }
}
