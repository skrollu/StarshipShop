
CREATE TABLE manufacturer (
    id_manufacturer BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
	CONSTRAINT PK_Manufacturer PRIMARY KEY (id_manufacturer)
);

CREATE TABLE hyperdrive_system (
	id_hyperdrive_system BIGINT NOT NULL AUTO_INCREMENT,
    reference VARCHAR(255) NOT NULL UNIQUE,
	name VARCHAR(100) NOT NULL,
	id_manufacturer INT, 
	CONSTRAINT PK_Hyperdrive_System PRIMARY KEY (id_hyperdrive_system),
	CONSTRAINT FK_manufacturer_hyperdriveSystem FOREIGN KEY (id_manufacturer) REFERENCES manufacturer(id_manufacturer)
);

CREATE TABLE starship 
(
    id_starship BIGINT NOT NULL AUTO_INCREMENT,
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
    CONSTRAINT FK_hyperdriveSystem_starship FOREIGN KEY (id_hyperdrive_system) REFERENCES hyperdrive_system(id_hyperdrive_system),
    CONSTRAINT FK_manufacturer_starship FOREIGN KEY (id_manufacturer) REFERENCES manufacturer(id_manufacturer)
);

CREATE TABLE weapon (
    id_weapon BIGINT NOT NULL AUTO_INCREMENT,
    reference VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    id_manufacturer INT, 
    CONSTRAINT PK_Weapon PRIMARY KEY (id_weapon),
    CONSTRAINT FK_manufacturer_weapon FOREIGN KEY (id_manufacturer) REFERENCES manufacturer(id_manufacturer)
);

CREATE TABLE rel_starship_weapon (
    id_starship BIGINT NOT NULL,
    id_weapon BIGINT NOT NULL,
    CONSTRAINT FK_Starship FOREIGN KEY (id_starship) REFERENCES starship(id_starship),
    CONSTRAINT FK_Weapon FOREIGN KEY (id_weapon) REFERENCES weapon(id_weapon)
);


