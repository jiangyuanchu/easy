CREATE TABLE IF NOT EXISTS job(
id varchar(36) NOT NULL COMMENT '主键id',
spider_id varchar(36),
spider_name varchar(64),
spider_table_name varchar(64),
status char(32),
start_time datetime,
end_time datetime,
time_cost bigint(20),
create_time datetime,
modify_time datetime,
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS configurable_spider(
id varchar(36) NOT NULL COMMENT '主键id',
name varchar(64) NOT NULL,
table_name varchar(64) NOT NULL,
list_regex varchar(512) NOT NULL,
entry_url varchar(1024) NOT NULL,
fields_json text NOT NULL,
create_time datetime NOT NULL,
modify_time datetime NOT NULL,
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;