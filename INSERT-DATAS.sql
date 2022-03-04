insert into manufacturer (name) values (
	'Koensayr Manufacturing'
);

insert into hyperdrive_system (reference, name, id_manufacturer) 
values ('0000000001', 'Koensayr Class 1 R-300-H Hyperdrive', 1);

insert into starship (reference, name, engines, height, width, lenght, weight, description, id_hyperdrive_system, id_manufacturer) 
values ('0000000001', 'BTL-A4 Y-Wing Starfighter', 'Koensayr R200 Ion Jet Engines', 7, 30, 61, 	2.52, '', 1, 1);

insert into weapon (reference, name, id_manufacturer)
values 
('0000000001', 'ArMek SW-4 Ion Cannons (on rotating turret)', 1),
('0000000002', 'Taim & Bak IX4 Laser Cannons', 1),
('0000000003', 'Arakyd Flex tube Proton Torpedo Launchers', 1);

insert into rel_starship_weapon (id_starship, id_weapon) 
values 
(1,1),
(1,2),
(1,3);