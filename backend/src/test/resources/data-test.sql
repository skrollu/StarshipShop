insert into manufacturer (name) values 
('ManufacturerToGet1'),
('ManufacturerToGet2'),
('ManufacturerToUpdate3'),
('ManufacturerToUpdate4'),
('Manufacturer5'),
('ManufacturerNotNested6')
;

insert into hyperdrive_system (name, id_manufacturer) values 
('HyperdriveToGet1', 1),
('HyperdriveToUpdate2', 2)
;

insert into starship (name, engines, height, width, lenght, weight, description, id_hyperdrive_system, id_manufacturer) values 
('StarshipToGet1', 'engine1', 1, 2, 3, 4.5, 'description1', 1, 1),
('StarshipToGet2', 'engine2', 1, 2, 3, 4.5, 'description2', 2, 2),
('StarshipToUpdate1', 'engine3', 1, 2, 3, 4.5, 'description2', 2, 2),
('StarshipToDelete1', 'engine4', 1, 2, 3, 4.5, 'description2', 2, 2)
;

insert into weapon (name, id_manufacturer) values 
('WeaponToGet1', 1),
('WeaponToGet2', 1),
('WeaponToGet3', 1),
('Weapon4', 2),
('Weapon5', 2)
;

insert into starships_weapons (id_starship, id_weapon) 
values 
(1,1),
(1,2),
(1,3),
(2,4),
(2,5)
;

-- id list with testHashIdsSalt
-- 1 W5pvAw0r
-- 2 Kdp5qpxj
-- 3 mbLbXp35
-- 4 GELQkpdQ
-- 5 ?
-- 6 qZpyYpYM