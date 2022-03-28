ALTER TABLE cookbook.ingredient
    CHANGE COLUMN name name CHAR(100) NOT NULL;

ALTER TABLE cookbook.recipe
    CHANGE COLUMN name name CHAR(100) NOT NULL;