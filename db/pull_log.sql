
drop TABLE `pull_log`;
CREATE TABLE `pull_log` (
  `logid` varchar(20) NOT NULL,
  `card`varchar(10) default NULL,
  `face` varchar(10) default NULL,
  `createtime` DATE default NULL,
  `datestr` varchar(10) default NULL,
  `timestr` varchar(10) default NULL,
  `timestamps` varchar(30) default NULL,
  PRIMARY KEY  (`logid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE pull_log ADD INDEX index_name (timestamps)
