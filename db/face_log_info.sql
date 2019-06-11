
drop TABLE `face_log_info`;
CREATE TABLE `face_log_info` (
  `logidinternal` varchar(200) NOT NULL,
  `personalnr`varchar(10) default NULL,
  `username` varchar(100) default NULL,
  `createtime` DATE default NULL,
  `datestr` varchar(10) default NULL,
  `timestr` varchar(10) default NULL,
  `timestamps` varchar(30) default NULL,
  PRIMARY KEY  (`logidinternal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE face_log_info ADD INDEX index_name (timestamps)
