#drop table if exists post;

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table IF NOT EXISTS post
(
    id               int not null,
    userId           int not null,
    title            varchar(255) NOT NULL,
    body             varchar(255) NOT NULL,
    primary key (id)
);