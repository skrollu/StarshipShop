insert into manufacturer (name) values 
('TestManufacturer1'),
('TestManufacturer2'),
('TestManufacturer3'),
('TestManufacturer4'),
('TestManufacturer5'),
('TestManufacturerNotJoined')
;

insert into hyperdrive_system (name, id_manufacturer) values 
('TestManufacturerHyperdrive1', 1),
('TestManufacturerHyperdrive2', 2)
;

insert into starship (name, engines, height, width, lenght, weight, description, id_hyperdrive_system, id_manufacturer) values 
('Starship1', 'Koensayr R200 Ion Jet Engines', 7, 30, 61, 2.52, 'Known as a rebel bomber ship', 1, 1),
('Starship2', 'Novaldex J-77 Event Horizon', 0, 0, 9.1, 0, 'Known as a rebel star fighter', 2, 2)
;

insert into weapon (name, id_manufacturer) values 
('Weapon1', 1),
('Weapon2', 1),
('Weapon3', 1),
('Weapon4', 2),
('Weapon5', 2);

insert into starships_weapons (id_starship, id_weapon) 
values 
(1,1),
(1,2),
(1,3),
(2,4),
(2,5)
;