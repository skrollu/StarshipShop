CREATE TABLE student (
    id_test2 INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    id_test INT NOT NULL,
	CONSTRAINT PK_Test2 PRIMARY KEY (id_test2),
	CONSTRAINT FK_Test FOREIGN KEY (id_test) REFERENCES test(id_test)
);

CREATE TABLE manufacturer (
    id_manufacturer INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
	CONSTRAINT PK_Manufacturer PRIMARY KEY (id_manufacturer)
);

CREATE TABLE hyperdrive_system (
	id_hyperdrive_system INT NOT NULL AUTO_INCREMENT,
    reference VARCHAR(255) NOT NULL UNIQUE,
	name VARCHAR(100) NOT NULL,
	id_manufacturer INT, 
	CONSTRAINT PK_Hyperdrive_System PRIMARY KEY (id_hyperdrive_system),
	CONSTRAINT FK_Manufacturer FOREIGN KEY (id_manufacturer) REFERENCES manufacturer(id_manufacturer)
);

CREATE TABLE starship 
(
    id_starship INT NOT NULL AUTO_INCREMENT,
    reference VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    engines VARCHAR(255) NOT NULL,
    height FLOAT NOT NULL,
    width FLOAT NOT NULL,
    lenght FLOAT NOT NULL,
    weight FLOAT NOT NULL,
    description TEXT,
    id_hyperdrive_system INT, 
    id_manufacturer INT, 
    CONSTRAINT PK_Starship PRIMARY KEY (id_starship),
    CONSTRAINT FK_Hyperdrive_System FOREIGN KEY (id_hyperdrive_system) REFERENCES hyperdrive_system(id_hyperdrive_system),
    CONSTRAINT FK_Manufacturer FOREIGN KEY (id_manufacturer) REFERENCES manufacturer(id_manufacturer)
);

CREATE TABLE weapon (
    id_weapon INT NOT NULL AUTO_INCREMENT,
    reference VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    id_manufacturer INT, 
    CONSTRAINT PK_Weapon PRIMARY KEY (id_weapon),
    CONSTRAINT FK_Manufacturer FOREIGN KEY (id_manufacturer) REFERENCES manufacturer(id_manufacturer)
);

CREATE TABLE rel_starship_weapon (
    id_starship INT NOT NULL,
    id_weapon INT NOT NULL,
    CONSTRAINT FK_Starship FOREIGN KEY (id_starship) REFERENCES starship(id_starship),
    CONSTRAINT FK_Weapon FOREIGN KEY (id_weapon) REFERENCES weapon(id_weapon)
);