
drop TABLE `koaka_overtime_info`;
CREATE TABLE `koaka_overtime_info` (
  `logidinternal` varchar(200) NOT NULL,
  `personalnr`varchar(10) default NULL,
  `username` varchar(100) default NULL,
  `createtime` DATE default NULL,
  `datestr` varchar(10) default NULL,
  `starttime` varchar(30) default NULL,
  `endtime` varchar(30) default NULL,
  `overtimeh` FLOAT(10, 2) default NULL,
  PRIMARY KEY  (`logidinternal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
