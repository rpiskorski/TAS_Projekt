DROP TRIGGER IF EXISTS refreshP;
DROP TRIGGER IF EXISTS editP;
DROP TRIGGER IF EXISTS deleteP;

DROP TRIGGER IF EXISTS refreshS;
DROP TRIGGER IF EXISTS editS;
DROP TRIGGER IF EXISTS deleteS;

DROP TABLE IF EXISTS product_user;
DROP TABLE IF EXISTS serv_user;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS services;

DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS category;

#TABLE CATEGORY
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Sprzęt AGD'),
(2, 'Biuro'),
(3, 'Komputery'),
(4, 'Budowa i remont'),
(5, 'Motoryzacja'),
(6, 'Książka'),
(7, 'Sprzęt RTV'),
(8, 'Prezenty'),
(9, 'Gry'),
(10, 'Hobby');


ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


#TABLE PRODUCTS
CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `manufacturer_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `num_of_votes` int(11) DEFAULT '0',
  `avg_rating` double(2,1) DEFAULT '0.0',
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


INSERT INTO `products` (`id`, `manufacturer_name`, `name`, `num_of_votes`, `avg_rating`, `category_id`) VALUES
(1, 'Sony', 'Odkurzacz', 0, 0, 1),
(2, 'Samsung', 'Pralka', 0, 0, 1),
(3, 'Amica', 'Kuchenka gazowa', 0, 0, 1),
(4, 'Philips', 'Suszarka', 0, 0, 1),
(5, 'Amica', 'Pralka 2', 0, 0, 1),
(6, 'Samsung', 'Suszarka', 0, 0, 1),
(7, 'Amica', 'Prostownica', 0, 0, 1),
(8, 'BIC', 'Ołówek BIC czarny', 0, 0, 2),
(9, 'BIC', 'Długopis BIC czarny', 0, 0, 2),
(10, 'BIC', 'Długopis BIC niebieski', 0, 0, 2),
(11, 'Papier', 'Papier A4 Ryza', 0, 0, 2),
(12, 'BRW', 'Stół biały', 0, 0, 2),
(13, 'BRW', 'Biurko białe', 0, 0, 2),
(14, 'Polskie Meble', 'Szafa biała', 0, 0, 2),
(15, 'X-kom', 'Komputer Stacjonarny', 0, 0, 3),
(16, 'X-kom', 'Komputer Przenośy', 0, 0, 3),
(17, 'Komputronik', 'Komputer Przenośny', 0, 0, 3),
(18, 'Samsung', 'Monitor 34"', 0, 0, 3),
(19, 'Samsung', 'Monitor 33"', 0, 0, 3),
(20, 'Dell', 'Monitor 34"', 0, 0, 3),
(21, 'Lenovo', 'Laptop Lenovo', 0, 0, 3),
(22, 'CRTfan', 'Monitor CRT 17"', 0, 0, 3),
(23, 'Razer', 'Monitor 40"', 0, 0, 3),
(24, 'Razer', 'Mysz komputerowa przew.', 0, 0, 3),
(25, 'Razer', 'Mysz komputerowa bez.przew.', 0, 0, 3),
(26, 'Razer', 'Komputer Przenośny', 0, 0, 3),
(27, 'Razer', 'Monitor 34"', 0, 0, 3),
(28, 'Razer', 'Podkładka pod mysz', 0, 0, 3),
(29, 'Razer', 'Podkładka pod mysz duża', 0, 0, 3),
(30, 'Bosh', 'Miara ', 0, 0, 4),
(31, 'Bosh', 'Miernik laserowy', 0, 0, 4),
(32, 'Bosh', 'Wiertarka', 0, 0, 4),
(33, 'Bosh', 'Wiertarka udarowa', 0, 0, 4),
(34, 'JBC', 'Miara', 0, 0, 4),
(35, 'JBC', 'Drabina', 0, 0, 4),
(36, 'JBC', 'Drabina długa', 0, 0, 4),
(37, 'JBC', 'Miernik laserowy JBC', 0, 0, 4),
(38, 'Castorama', 'Wiertarka udarowa Cast.', 0, 0, 4),
(39, 'MacAllister', 'Wiertarka udarowa', 0, 0, 4),
(40, 'MacAllister', 'Młot udarowy', 0, 0, 4),
(41, 'MacAllister', 'Miara 3m', 0, 0, 4),
(42, 'MacAllister', 'Drabina', 0, 0, 4),
(43, 'MacAllister', 'Śrubokręt', 0, 0, 4),
(44, 'JBC', 'Pilnik', 0, 0, 4),
(45, 'MacAllister', 'Młotek', 0, 0, 4),
(46, 'Bosh', 'Młot duży', 0, 0, 4),
(47, 'Samsung', 'Mikrofalówka', 0, 0, 1);



ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1cf90etcu98x1e6n9aks3tel3` (`category_id`);


ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `products`
  ADD CONSTRAINT `FK1cf90etcu98x1e6n9aks3tel3` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);


#TABLE SERVICES
CREATE TABLE `services` (
  `id` int(11) NOT NULL,
  `localization` varchar(255) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `num_of_votes` int(11) DEFAULT '0',
  `owner_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `avg_rating` double(2,1) DEFAULT '0.0',
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `services` (`id`, `localization`, `name`, `num_of_votes`, `owner_name`, `avg_rating`, `category_id`) VALUES
(1, 'Warszawa ul Wiejska 6', 'Naprawa komputerów', 0, 'IT Spec Sp. z o. o.', 0, 3),
(2, 'Warszawa ul Wiejska 12', 'Sprzedaż komputerów', 0, 'IT TI Sp. z o. o.', 0, 3),
(3, 'Kraków ul Wiejska 14', 'Sprzedaż komputerów', 0, 'IT 1 Sp. z o. o.', 0, 3),
(4, 'Olsztyn ul Wiejska 16', 'Sprzedaż komputerów', 0, 'IT 2 Sp. z o. o.', 0, 3),
(5, 'Szczecin ul Wiejska 17', 'Sprzedaż komputerów', 0, 'IT 3 Sp. z o. o.', 0, 3),
(6, 'Poznań ul Wiejska 24', 'Sprzedaż komputerów', 0, 'IT 4 Sp. z o. o.', 0, 3),
(7, 'Poznań ul Wiejska 22', 'Wytwarzanie oprogramowania', 0, 'IT 5 Sp. z o. o.', 0, 3),
(8, 'Kraków ul Wiejska 1', 'Wytwarzanie oprogramowania', 0, 'IT 6 Sp. z o. o.', 0, 3),
(9, 'Szczecin ul Wiejska 12', 'Wytwarzanie oprogramowania', 0, 'IT 7 Sp. z o. o.', 0, 3),
(10, 'Olsztyn ul Wiejska 32', 'Wytwarzanie oprogramowania', 0, 'IT 8 Sp. z o. o.', 0, 3),
(11, 'Wrocław ul Wiejska 2', 'Wytwarzanie oprogramowania', 0, 'IT 9 Sp. z o. o.', 0, 3),
(12, 'Kielce ul Wiejska 4', 'Wytwarzanie oprogramowania', 0, 'IT 10 Sp. z o. o.', 0, 3),
(13, 'Gdańsk ul Wiejska 25', 'Wytwarzanie oprogramowania', 0, 'IT 11 Sp. z o. o.', 0, 3),
(14, 'Katowice ul Wiejska 19', 'Wytwarzanie oprogramowania', 0, 'IT 12 Sp. z o. o.', 0, 3),
(15, 'Zielona Góra ul Wiejska 6', 'Wytwarzanie oprogramowania', 0, 'IT 13 Sp. z o. o.', 0, 3),
(16, 'Zielona Góra ul Półwiejska 6', 'Projektowanie wnętrz', 0, 'BudProj Sp. z o. o.', 0, 4),
(17, 'Kraków ul Półwiejska 6', 'Budowa domów jednorodzinnych', 0, 'BudBUD Sp. z o. o.', 0, 4),
(18, 'Poznań ul Półwiejska 6', 'Prace remontowo-budowlane', 0, 'Bud-Rem  Sp. z o. o.', 0, 4),
(19, 'Szczecin ul Półwiejska 6', 'Wykończenie wnętrz', 0, 'Budomix Sp. z o. o.', 0, 4),
(20, 'Olsztyn ul Półwiejska 6', 'Wynajem maszyn budowlanych', 0, 'RobEx Sp. z o. o.', 0, 4),
(21, 'Katowice ul Półwiejska 6', 'Wynajem maszyn budowlanych', 0, 'Bud-Rob Sp. z o. o.', 0, 4),
(22, 'Gdańsk ul Półwiejska 6', 'Wynajem maszyn budowlanych', 0, 'RobBud Sp. z o. o.', 0, 4),
(23, 'Gdynia ul Półwiejska 6', 'Wykończenie wnętrz', 0, 'InterBud Sp. z o. o.', 0, 4),
(24, 'Sopot ul Półwiejska 6', 'Wykończenie wnętrz', 0, 'BudWyk Sp. z o. o.', 0, 4),
(25, 'Kielce ul Półwiejska 6', 'Prace remontowo-budowlane', 0, 'BudRemix Sp. z o. o.', 0, 4),
(26, 'Warszawa ul Półwiejska 6', 'Budowa domów jednorodzinnych', 0, 'Budorem Sp. z o. o.', 0, 4),
(27, 'Piła ul Półwiejska 6', 'Projektowanie wnętrz', 0, 'Proj.Wnętrz Sp. z o. o.', 0, 4),
(28, 'Poznań ul Mostowa 6', 'Serwis pralek', 0, 'Serwis pralek Sp. z o. o.', 0, 1),
(29, 'Piła ul Mostowa 6', 'Serwis lodówek', 0, 'Serwis lodówek Sp. z o. o.', 0, 1),
(30, 'Kraków ul Mostowa 6', 'Sprzedaż sprzętu AGD', 0, 'AGD-Expert Sp. z o. o.', 0, 1);

ALTER TABLE `services`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK60ko207yj7h3shgu88lh31j09` (`category_id`);

ALTER TABLE `services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `services`
  ADD CONSTRAINT `FK60ko207yj7h3shgu88lh31j09` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

#TABLE ROLES
CREATE TABLE `roles` (
  `name` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `roles` (`name`) VALUES
('ADMIN'),
('USER');

ALTER TABLE `roles`
  ADD PRIMARY KEY (`name`);


#TABLE USERS
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `role` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


INSERT INTO `users` (`id`, `enabled`, `name`, `password`, `role`) VALUES
(1, 1, 'Admin', '$2a$10$HdFAgilBYsuCACn.bhToWOwiqByv9CplLEivrFpJWDWbc.G2RiapC', 'ADMIN'),
(2, 1, 'RegularUser2', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(3, 1, 'RegularUser3', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(4, 1, 'RegularUser4', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(5, 1, 'RegularUser5', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(6, 1, 'RegularUser6', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(7, 1, 'RegularUser7', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(8, 1, 'RegularUser8', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(9, 1, 'RegularUser9', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(10, 1, 'RegularUser10', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(11, 1, 'RegularUser11', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(12, 1, 'RegularUser12', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(13, 1, 'RegularUser13', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(14, 1, 'RegularUser14', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(15, 1, 'RegularUser15', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(16, 1, 'RegularUser16', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(17, 1, 'RegularUser17', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(18, 1, 'RegularUser18', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(19, 1, 'RegularUser19', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER'),
(20, 1, 'RegularUser20', '$2a$10$9bbB01AmyMcXGpevU35fW.3vkm52cTBx6RUw4maqS7aGtx9ViQuiC', 'USER');

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_3g1j96g94xpk3lpxl2qbl985x` (`name`),
  ADD KEY `FK4c6vlshk8x83ifeoggi3exg3k` (`role`);

ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `users`
  ADD CONSTRAINT `FK4c6vlshk8x83ifeoggi3exg3k` FOREIGN KEY (`role`) REFERENCES `roles` (`name`);


#TABLE PRODUCT_USER
CREATE TABLE `product_user` (
  `id` int(11) NOT NULL,
  `comment` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `rating` int(11) DEFAULT '3',
  `date` bigint(20) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `product_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjqcj9ldg9wboo0vumixudga7g` (`product_id`),
  ADD KEY `FKl61e9j8gr4g77k3b85ndqqq7d` (`user_id`);

ALTER TABLE `product_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

ALTER TABLE `product_user`
  ADD CONSTRAINT `FKjqcj9ldg9wboo0vumixudga7g` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `FKl61e9j8gr4g77k3b85ndqqq7d` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

#TRIGGERS ON PRODUCT_USER
DELIMITER $$
CREATE TRIGGER `deleteP` AFTER DELETE ON `product_user` FOR EACH ROW BEGIN
SELECT AVG(pu.rating) INTO @currentAvg FROM `product_user` pu WHERE pu.product_id=OLD.product_id AND pu.rating IS NOT NULL;
SELECT COUNT(pu.id) INTO @currentNum FROM `product_user` pu WHERE pu.product_id=OLD.product_id AND pu.rating IS NOT NULL;

SELECT IF(@currentNum=0,0.0,@currentAvg) INTO @currentAvg;
    
    UPDATE `products` p 
    SET p.num_of_votes = @currentNum,
    p.avg_rating = @currentAvg
    WHERE p.id = OLD.product_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `editP` AFTER UPDATE ON `product_user` FOR EACH ROW BEGIN
SELECT AVG(pu.rating) INTO @currentAvg FROM `product_user` pu WHERE pu.product_id=NEW.product_id AND pu.rating IS NOT NULL;
SELECT COUNT(pu.id) INTO @currentNum FROM `product_user` pu WHERE pu.product_id=NEW.product_id AND pu.rating IS NOT NULL;
    
    UPDATE `products` p 
    SET p.num_of_votes = @currentNum,
    p.avg_rating = @currentAvg
    WHERE p.id = NEW.product_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `refreshP` AFTER INSERT ON `product_user` FOR EACH ROW BEGIN

SELECT AVG(pu.rating) INTO @currentAvg FROM `product_user` pu WHERE pu.product_id=NEW.product_id AND pu.rating IS NOT NULL;
SELECT COUNT(pu.id) INTO @currentNum FROM `product_user` pu WHERE pu.product_id=NEW.product_id AND pu.rating IS NOT NULL;
   
UPDATE `products` p 
SET p.num_of_votes = @currentNum,
p.avg_rating = @currentAvg
WHERE p.id = NEW.product_id;

END
$$
DELIMITER ;



INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(1, 'Polecam', 5, 1542475755570, 1, 2);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(2, 'Polecam', 5, 1542475755570, 1, 3);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(3, 'Polecam', 5, 1542475755570, 1, 4);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(4, 'Polecam', 5, 1542475755570, 1, 5);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(5, 'Polecam', 4, 1542475755570, 1, 6);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(6, 'Polecam', 4, 1542475755570, 1, 7);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(7, 'Polecam', 4, 1542475755570, 1, 8);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(8, 'Polecam', 4, 1542475755570, 1, 9);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(9, 'Nie warto', 3, 1542475755570, 2, 2);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(10, 'Nie warto', 3, 1542475755570, 2, 3);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(11, 'Nie warto', 3, 1542475755570, 2, 4);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(12, 'Nie warto', 3, 1542475755570, 2, 5);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(13, 'Nie warto', 2, 1542475755570, 2, 6);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(14, 'Nie warto', 2, 1542475755570, 2, 7);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(15, 'Nie warto', 2, 1542475755570, 2, 8);
INSERT INTO `product_user` (`id`, `comment`, `rating`, `date`, `product_id`, `user_id`) VALUES
(16, 'Nie warto', 2, 1542475755570, 2, 9);


#TABLE SERV_USER
CREATE TABLE `serv_user` (
  `id` int(11) NOT NULL,
  `comment` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `rating` int(11) DEFAULT '3',
  `date` bigint(20) DEFAULT NULL,
  `service_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `serv_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo0xj70qwrhvxwr4b13rfcjtmt` (`service_id`),
  ADD KEY `FK66cdsb2nbk5fblj9x6yjwojbm` (`user_id`);

ALTER TABLE `serv_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `serv_user`
  ADD CONSTRAINT `FK66cdsb2nbk5fblj9x6yjwojbm` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKo0xj70qwrhvxwr4b13rfcjtmt` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`);

#TRIGGERS ON SERV_USER
DELIMITER $$
CREATE TRIGGER `deleteS` AFTER DELETE ON `serv_user` FOR EACH ROW BEGIN
SELECT AVG(su.rating) INTO @currentAvg FROM `serv_user` su WHERE su.service_id=OLD.service_id AND su.rating IS NOT NULL;
SELECT COUNT(su.id) INTO @currentNum FROM `serv_user` su WHERE su.service_id=OLD.service_id AND su.rating IS NOT NULL;

SELECT IF(@currentNum=0,0.0,@currentAvg) INTO @currentAvg;
    
    UPDATE `services` s 
    SET s.num_of_votes = @currentNum,
    s.avg_rating = @currentAvg
    WHERE s.id = OLD.service_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `editS` AFTER UPDATE ON `serv_user` FOR EACH ROW BEGIN
SELECT AVG(su.rating) INTO @currentAvg FROM `serv_user` su WHERE su.service_id=NEW.service_id AND su.rating IS NOT NULL;
SELECT COUNT(su.id) INTO @currentNum FROM `serv_user` su WHERE su.service_id=NEW.service_id AND su.rating IS NOT NULL;
    
    UPDATE `services` s 
    SET s.num_of_votes = @currentNum,
    s.avg_rating = @currentAvg
    WHERE s.id = NEW.service_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `refreshS` AFTER INSERT ON `serv_user` FOR EACH ROW BEGIN

SELECT AVG(su.rating) INTO @currentAvg FROM `serv_user` su WHERE su.service_id=NEW.service_id AND su.rating IS NOT NULL;
SELECT COUNT(su.id) INTO @currentNum FROM `serv_user` su WHERE su.service_id=NEW.service_id AND su.rating IS NOT NULL;
   
UPDATE `services` s 
SET s.num_of_votes = @currentNum,
s.avg_rating = @currentAvg
WHERE s.id = NEW.service_id;

END
$$
DELIMITER ;


INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(1, 'Polecam', 5, 1542475755570, 1, 2);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(2, 'Polecam', 5, 1542475755570, 1, 3);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(3, 'Polecam', 5, 1542475755570, 1, 4);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(4, 'Polecam', 5, 1542475755570, 1, 5);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(5, 'Polecam', 4, 1542475755570, 1, 6);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(6, 'Polecam', 4, 1542475755570, 1, 7);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(7, 'Polecam', 4, 1542475755570, 1, 8);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(8, 'Polecam', 4, 1542475755570, 1, 9);

INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(9, 'Nie warto', 3, 1542475755570, 2, 2);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(10, 'Nie warto', 3, 1542475755570, 2, 3);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(11, 'Nie warto', 3, 1542475755570, 2, 4);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(12, 'Nie warto', 3, 1542475755570, 2, 5);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(13, 'Nie warto', 2, 1542475755570, 2, 6);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(14, 'Nie warto', 2, 1542475755570, 2, 7);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(15, 'Nie warto', 2, 1542475755570, 2, 8);
INSERT INTO `serv_user` (`id`, `comment`, `rating`, `date`, `service_id`, `user_id`) VALUES
(16, 'Nie warto', 2, 1542475755570, 2, 9);
