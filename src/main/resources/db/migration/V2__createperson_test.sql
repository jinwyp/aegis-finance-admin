CREATE TABLE person(
   `person_id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
   `comp_compId` INT(11) unsigned COMMENT 'compId',
   `person_name` VARCHAR(30) NOT NULL COMMENT '姓名',
   PRIMARY KEY (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT 'person表';