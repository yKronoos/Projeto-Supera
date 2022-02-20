create table product(
	id bigint not null auto_increment,
	name varchar(60) not null,
	price decimal(5,2) not null,
	score tinyint not null,
	image varchar(60),

	primary key(id)

);