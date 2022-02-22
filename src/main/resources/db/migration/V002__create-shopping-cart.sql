create table cart(
	id bigint not null auto_increment,
	name varchar(60) not null,
	price decimal(5,2) not null,

	primary key(id)
);