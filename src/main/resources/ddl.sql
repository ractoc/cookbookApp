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

create table recipe_ingredients
(
    recipe_id     int not null,
    ingredient_id int not null,
    amount        int null,
    constraint recipe_ingredients_pk
        primary key (recipe_id, ingredient_id),
    constraint recipe_ingredients_ingredient_id_fk
        foreign key (ingredient_id) references ingredient (id),
    constraint recipe_ingredients_recipe_id_fk
        foreign key (recipe_id) references recipe (ID)
);

create table step
(
    id           int auto_increment
        primary key,
    description  tinytext not null,
    recipe_id    int      not null,
    step_counter int      not null,
    constraint step_recipe_fk
        foreign key (recipe_id) references recipe (ID)
);