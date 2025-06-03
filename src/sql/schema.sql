CREATE TABLE `person` (
                          `personId` int NOT NULL AUTO_INCREMENT,
                          `firstName` varchar(50) DEFAULT NULL,
                          `lastName` varchar(50) DEFAULT NULL,
                          `fideId` varchar(13) DEFAULT NULL,
                          PRIMARY KEY (`personId`),
                          UNIQUE KEY `fideId` (`fideId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `player` (
                          `fideId` varchar(10) NOT NULL,
                          `title` varchar(5) DEFAULT NULL,
                          `rating` int DEFAULT NULL,
                          PRIMARY KEY (`fideId`),
                          CONSTRAINT `fk_player` FOREIGN KEY (`fideId`) REFERENCES `person` (`fideId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `organizer` (
                             `organizerId` int NOT NULL,
                             `phoneNumber` varchar(15) DEFAULT NULL,
                             `email` varchar(100) DEFAULT NULL,
                             PRIMARY KEY (`organizerId`),
                             CONSTRAINT `fk_organizer` FOREIGN KEY (`organizerId`) REFERENCES `person` (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `arbiter` (
                           `fideId` varchar(10) NOT NULL,
                           `title` varchar(5) DEFAULT NULL,
                           PRIMARY KEY (`fideId`),
                           CONSTRAINT `fk_arbiter` FOREIGN KEY (`fideId`) REFERENCES `person` (`fideId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tournament` (
                              `tournamentId` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(100) DEFAULT NULL,
                              `organizerId` int DEFAULT NULL,
                              PRIMARY KEY (`tournamentId`),
                              KEY `fk_tournament_organizer` (`organizerId`),
                              CONSTRAINT `fk_tournament_organizer` FOREIGN KEY (`organizerId`) REFERENCES `organizer` (`organizerId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tournamentarbiter` (
                                     `fideId` varchar(10) NOT NULL,
                                     `tournamentId` int NOT NULL,
                                     `role` varchar(20) DEFAULT NULL,
                                     PRIMARY KEY (`fideId`,`tournamentId`),
                                     KEY `fk_tournamentarbiter_tournamentId` (`tournamentId`),
                                     CONSTRAINT `fk_tournamentarbiter_fideId` FOREIGN KEY (`fideId`) REFERENCES `arbiter` (`fideId`),
                                     CONSTRAINT `fk_tournamentarbiter_tournamentId` FOREIGN KEY (`tournamentId`) REFERENCES `tournament` (`tournamentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tournamentplayer` (
                                    `fideId` varchar(10) NOT NULL,
                                    `tournamentId` int NOT NULL,
                                    `points` double DEFAULT NULL,
                                    PRIMARY KEY (`fideId`,`tournamentId`),
                                    KEY `fk_tournamentplayer_tournamentId` (`tournamentId`),
                                    CONSTRAINT `fk_tournamentplayer_fideId` FOREIGN KEY (`fideId`) REFERENCES `player` (`fideId`),
                                    CONSTRAINT `fk_tournamentplayer_tournamentId` FOREIGN KEY (`tournamentId`) REFERENCES `tournament` (`tournamentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;