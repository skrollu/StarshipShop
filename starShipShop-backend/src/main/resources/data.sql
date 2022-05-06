insert into manufacturer (name) values 
('Koensayr Manufacturing'),
('Kuat System Engineering'),
('Corellian Engineering Corporation')
;

insert into hyperdrive_system (reference, name, id_manufacturer) values 
('0000000001', 'Koensayr Class 1 R-300-H Hyperdrive', 1),
('0000000002', 'Icom GBK-785 hyperdrive unit', 2)
;

insert into starship (reference, name, engines, height, width, lenght, weight, description, id_hyperdrive_system, id_manufacturer) values 
('0000000001', 'BTL-A4 Y-Wing Starfighter', 'Koensayr R200 Ion Jet Engines', 7, 30, 61, 2.52, 'Known as a rebel bomber ship', 1, 1),
('0000000002', 'A-Wing Starfighter', 'Novaldex J-77 Event Horizon', 0, 0, 9.1, 0, 'Known as a rebel star fighter', 2, 2)
;

insert into weapon (reference, name, id_manufacturer) values 
('0000000001', 'ArMek SW-4 Ion Cannons (on rotating turret)', 1),
('0000000002', 'Taim & Bak IX4 Laser Cannons', 1),
('0000000003', 'Arakyd Flex tube Proton Torpedo Launchers', 1),
('0000000004', 'Borstel RG-9 Laser Cannons', 2),
('0000000005', 'Dymek HM-6 concussion Missile Launcher', 2);

insert into starships_weapons (id_starship, id_weapon) 
values 
(1,1),
(1,2),
(1,3),
(2,4),
(2,5)
;