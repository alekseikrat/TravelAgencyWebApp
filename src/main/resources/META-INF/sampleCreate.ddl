
    create table clients (
       id bigint not null auto_increment,
        age integer not null,
        name varchar(25) not null,
        surname varchar(25) not null,
        primary key (id)
    ) engine=InnoDB;

    create table clients_tours (
       client_id bigint not null,
        tour_id bigint not null,
        primary key (client_id, tour_id)
    ) engine=InnoDB;

    create table employees (
       id bigint not null auto_increment,
        age integer not null,
        name varchar(25) not null,
        surname varchar(25) not null,
        tour_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table tours (
       id bigint not null auto_increment,
        name varchar(50) not null,
        primary key (id)
    ) engine=InnoDB;

    create table users (
       id bigint not null auto_increment,
        password varchar(10) not null,
        username varchar(10) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table tours 
       add constraint UK_li8h6s0rh5fsmelkpsfd9neop unique (name);

    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table clients_tours 
       add constraint FKo9elnpuambsdwmebbnw3e3ku 
       foreign key (tour_id) 
       references tours (id);

    alter table clients_tours 
       add constraint FKorec8w838wyl9bowopuig9xdy 
       foreign key (client_id) 
       references clients (id);

    alter table employees 
       add constraint FKng1mlmim364r4trf0ufoj4fjn 
       foreign key (tour_id) 
       references tours (id);

    alter table tours 
       add column arrival varchar(20) not null;

    alter table tours 
       add column departure varchar(20) not null;

    alter table tours 
       add column transport varchar(20) not null;
