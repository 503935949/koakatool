
drop TABLE `koaka_user`;
CREATE TABLE `koaka_user` (
  `job_number` varchar(10) NOT NULL,
  `username`varchar(20) default NULL,
  PRIMARY KEY  (`job_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

