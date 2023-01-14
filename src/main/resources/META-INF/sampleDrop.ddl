
    alter table clients_tours 
       drop 
       foreign key FKo9elnpuambsdwmebbnw3e3ku;

    alter table clients_tours 
       drop 
       foreign key FKorec8w838wyl9bowopuig9xdy;

    alter table employees 
       drop 
       foreign key FKng1mlmim364r4trf0ufoj4fjn;

    drop table if exists clients;

    drop table if exists clients_tours;

    drop table if exists employees;

    drop table if exists tours;

    drop table if exists users;
