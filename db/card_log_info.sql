
drop TABLE `card_log_info`;
CREATE TABLE `card_log_info` (
  `logidinternal` varchar(200) NOT NULL,
  `personalnr`varchar(10) default NULL,
  `cardnr` varchar(50) default NULL,
  `deviceaddress` char(50) default NULL,
  `datapointtype` varchar(20) default NULL,
  `logdate` varchar(30) default NULL,
  `eventtypeinfo` varchar(10) default NULL,
  `allname` varchar(200) default NULL,
	`cardname` varchar(100) default NULL,
  `username` varchar(100) default NULL,
  `createtime` DATE default NULL,
  `datestr` varchar(10) default NULL,
  `timestr` varchar(10) default NULL,
  `timestamps` varchar(30) default NULL,
  PRIMARY KEY  (`logidinternal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE card_log_info ADD INDEX index_name (timestamps)
