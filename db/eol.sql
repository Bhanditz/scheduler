CREATE SCHEMA `eol` DEFAULT CHARACTER SET utf8 ;


drop table IF EXISTS `eol`.`content_partner`;
create table `eol`.`content_partner`(
    `id` INT AUTO_INCREMENT,
    `name` varchar(255),
    `abbreviation` varchar(255),
    `url` text,
    `description` text,
    `logo` longblob,
    `logo_type` varchar(45),
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY ( `id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table IF EXISTS `eol`.`resource`;
create table `eol`.`resource`(
	`id` int AUTO_INCREMENT PRIMARY KEY ,
    `content_partner_id` int not null references `content_partner`(`id`),
    `name` varchar(255),
    `origin_url` text,
    `type` enum ('url', 'file'),
    `path` text,
    `last_harvested_at` datetime default null,
    `harvest_frequency` enum('once', 'weekly', 'monthly', 'bimonthly', 'quarterly'),
    `day_of_month` int default 0,
    `nodes_count` int,
    `position` int default -1,
    `is_paused` boolean default 0,
    `is_approved` boolean default 0,
    `is_trusted` boolean default 0,
    `is_autopublished` boolean default 0,
    `is_forced` boolean default 0,
    `dataset_license` int default 47,
	`dataset_rights_statement` text,
	`dataset_rights_holder` text,
	`default_license_string` int,
	`default_rights_statement` text,
	`default_rights_holder` text,
	`default_language_id` int default 152,
	`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table IF EXISTS `eol`.`harvest`;
create table `eol`.`harvest`(
	`id` int AUTO_INCREMENT PRIMARY KEY ,
    `resource_id` int not null references `resource`(`id`),
    `start_at` datetime,
    `validated_at` datetime,
    `deltas_created_at` datetime,
    `completed_at` datetime,
    `state` enum ('succeed', 'failed', 'running', 'pending																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																												'),
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

use `eol`;
drop procedure if exists `harvestResource`;

delimiter $$ 
CREATE DEFINER=`root`@`localhost` PROCEDURE `harvestResource`(IN cDate datetime)
begin
 SELECT * FROM resource
    WHERE  `type` = 'file' AND `is_paused` = '0' AND (`last_harvested_at` IS NULL OR `forced_internally` = '1' OR  `day_of_month` = DAY(cDate) OR 
    `is_forced` = '1') 
    UNION
    SELECT * FROM resource
    WHERE  `type` = 'url' AND `is_paused` = '0' AND (`last_harvested_at` IS NULL OR `forced_internally` = '1' OR  `day_of_month` = DAY(cDate) OR 
    `is_forced` = '1' OR DATE_ADD(`last_harvested_at`,INTERVAL `harvest_frequency` DAY) = cDate) ;
end$$

