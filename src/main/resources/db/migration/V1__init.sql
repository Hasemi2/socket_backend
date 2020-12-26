
    drop table if exists tb_chat_content;
    drop table if exists tb_chat_room;
    drop table if exists tb_chat_user;
    drop table if exists tb_chat_user_mapping;

    create table tb_chat_content (
       chat_content_id bigint not null auto_increment,
        created_time datetime,
        updated_time datetime,
        chat_content TEXT not null,
        room_id bigint not null,
        user_id varchar(50) not null,
        primary key (chat_content_id)
    ) engine=InnoDB;

    create table tb_chat_room (
       room_id bigint not null auto_increment,
        created_time datetime,
        updated_time datetime,
        use_yn varchar(255) not null,
        primary key (room_id)
    ) engine=InnoDB;

    create table tb_chat_user (
       user_pk binary(255) not null,
        created_time datetime,
        updated_time datetime,
        password varchar(255) not null,
        token_expired_time datetime,
        use_yn varchar(255) not null,
        user_id varchar(50) not null,
        user_token varchar(255),
        primary key (user_pk)
    ) engine=InnoDB;

    create table tb_chat_user_mapping (
       chat_user_id bigint not null auto_increment,
        created_time datetime,
        updated_time datetime,
        room_id bigint not null,
        user_id varchar(50) not null,
        primary key (chat_user_id)
    ) engine=InnoDB;

    alter table tb_chat_user
       add constraint UK_iybs7o6fgbh7326vupuxahe2j unique (user_id);

    alter table tb_chat_content
       add constraint FKlsmop9lop2sdy20hxh93uqe3s
       foreign key (room_id)
       references tb_chat_room (room_id);

    alter table tb_chat_content
       add constraint FK9oeclp4kug4mycpbvh991dtdx
       foreign key (user_id)
       references tb_chat_user (user_id);

    alter table tb_chat_user_mapping
       add constraint FKt8r15tjdgnlv5u7mpeqnoyapm
       foreign key (room_id)
       references tb_chat_room (room_id);

    alter table tb_chat_user_mapping
       add constraint FKlr5un65ks9a1vq550hvvwpvet
       foreign key (user_id)
       references tb_chat_user (user_id);