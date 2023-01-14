SET SQL_SAFE_UPDATES = 0;

LOCK TABLES `clients_tours` WRITE;
DELETE FROM `clients_tours`;
UNLOCK TABLES;

LOCK TABLES `clients` WRITE;
DELETE FROM `clients`;
UNLOCK TABLES;

LOCK TABLES `tours` WRITE;
DELETE FROM `tours`;
UNLOCK TABLES;

LOCK TABLES `employees` WRITE;
DELETE FROM `employees`;
UNLOCK TABLES;

LOCK TABLES `users` WRITE;
DELETE FROM `users`;
UNLOCK TABLES;

LOCK TABLES `clients` WRITE;
INSERT INTO `clients` (id, name, surname, age) VALUES (1,'Андрій','Засядько',35),(2,'Володимир','Зеленський',45),
(3,'Аліна','Андрусенко',29),(4,'Олексій','Крат',24),(5,'Лариса','Горбатюк',57),
(6,'Олександр','Антоненко',41);
UNLOCK TABLES;

LOCK TABLES `employees` WRITE;
INSERT INTO `employees` (id, name, surname, age, tour_id) VALUES (1,'Олександр','Овсянніков',45,3),(2,'Іван','Якименко',35,5),
(3,'Юлія','Кравченко',27,4),(4,'Андрій','Іванченко',25,2),(5,'Анастасія','Омелянчук',32,1),
(6,'Олег','Головін',41,6);
UNLOCK TABLES;

LOCK TABLES `tours` WRITE;
INSERT INTO `tours` (id, departure, arrival, transport) VALUES (1,'Київ','Стамбул','Автобус'),
(2,'Київ','Лондон','Літак'),
(3,'Львів','Рим','Літак'),
(4,'Дніпро','Варшава','Потяг'),
(5,'Дніпро','Софія','Потяг'),
(6,'Одеса','Афіни','Автобус');
UNLOCK TABLES;

LOCK TABLES `clients_tours` WRITE;
INSERT INTO `clients_tours` (client_id, tour_id) VALUES (1,1),(1,4),(2,2),(3,6),(3,1),
(4,5),(4,6),(5,4),(6,3);
UNLOCK TABLES;

LOCK TABLES `users` WRITE;
INSERT INTO `users` (id, `username`, password) VALUES (1, 'Admin','krat19');
UNLOCK TABLES;

SET SQL_SAFE_UPDATES = 1;
