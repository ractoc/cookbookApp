create schema cookbook collate utf8mb4_0900_ai_ci;

grant alter, create, delete, drop, index, insert, select, update on cookbook.* to cookbook;

create table ingredient
(
    id               int auto_increment
        primary key,
    name             char(25) not null,
    measurement_type int      not null,
    constraint ingredient_name_uindex
        unique (name)
);

create table recipe
(
    ID              int auto_increment
        primary key,
    NAME            char(50)  not null,
    DESCRIPTION     char(250) null,
    IMAGE_FILE_NAME char(25)  null,
    constraint RECIPE_NAME_uindex
        unique (NAME)
);