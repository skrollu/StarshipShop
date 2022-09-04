insert into manufacturer (name) values 
('Manufacturer1'),
('Manufacturer2'),
('Manufacturer3'),
('Manufacturer4'),
('Manufacturer5'),
('ManufacturerNotJoined')
;

insert into hyperdrive_system (name, id_manufacturer) values 
('Hyperdrive1', 1),
('Hyperdrive2', 2)
;

insert into starship (name, engines, height, width, lenght, weight, description, id_hyperdrive_system, id_manufacturer) values 
('Starship1', 'engine1', 1, 2, 3, 4.5, 'description1', 1, 1),
('Starship2', 'engine2', 1, 2, 3, 4.5, 'description2', 2, 2)
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