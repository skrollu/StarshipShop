insert into manufacturer (name) values 
('Koensayr Manufacturing'),
('Kuat System Engineering'),
('Corellian Engineering Corporation')
;

insert into hyperdrive_system (name, id_manufacturer) values 
('Koensayr Class 1 R-300-H Hyperdrive', 1),
('Icom GBK-785 hyperdrive unit', 2)
;

insert into starship (name, engines, height, width, lenght, weight, description, id_hyperdrive_system, id_manufacturer) values 
('BTL-A4 Y-Wing Starfighter', 'Koensayr R200 Ion Jet Engines', 7, 30, 61, 2.52, 'Known as a rebel bomber ship', 1, 1),
('A-Wing Starfighter', 'Novaldex J-77 Event Horizon', 0, 0, 9.1, 0, 'Known as a rebel star fighter', 2, 2)
;

insert into weapon (name, id_manufacturer) values 
('ArMek SW-4 Ion Cannons (on rotating turret)', 1),
('Taim & Bak IX4 Laser Cannons', 1),
('Arakyd Flex tube Proton Torpedo Launchers', 1),
('Borstel RG-9 Laser Cannons', 2),
('Dymek HM-6 concussion Missile Launcher', 2);

insert into starships_weapons (id_starship, id_weapon) 
values 
(1,1),
(1,2),
(1,3),
(2,4),
(2,5)
;

-- bcrypt(password) = $2y$10$XnNNdjWaPcF7ekAVxhYJP.j3nhcLaVLN3xfvAvMqaiJ2z/wx7Coiy
-- insert into account(username, password, roles) values
-- ('user', '$2a$10$3xhZl7AalnLHzSenU8i1y.anhs7JEUPyPm2kzGtQ4bT2ExF5k2aQW', 'USER'),
-- ('admin', '$2a$10$arMWicLUnoeiq/TJWbMFqe5j2XD3qfaxufdYsjCO3KOhvIbm1.Opi', 'USER, ADMIN')
-- ;