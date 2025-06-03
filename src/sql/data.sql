INSERT INTO person (personId, firstName, lastName, fideId) VALUES
                                                               (1, 'Magnus', 'Carlsen', '1503014'),
                                                               (2, 'Hikaru', 'Nakamura', '2016192'),
                                                               (3, 'Laurentiu', 'Sarighioleanu', '1244582'),
                                                               (4, 'Fabiano', 'Caruana', '2020009'),
                                                               (5, 'R', 'Praggnanandhaa', '25059530'),
                                                               (6, 'Maxime', 'Vachier-Lagrave', '623539'),
                                                               (7, 'Gigel', 'Popescu', NULL),
                                                               (8, 'Marcel', 'Popa', NULL),
                                                               (9, 'Bogdan', 'Vasilescu', '1');

INSERT INTO organizer (organizerId, phoneNumber, email) VALUES
                                                            (7, NULL, 'test@gmail.com'),
                                                            (8, NULL, NULL),
                                                            (9, '07125597539', NULL);

INSERT INTO player (fideId, title, rating) VALUES
                                               ('1', NULL, 1401),
                                               ('1244582', NULL, 1999),
                                               ('1503014', 'GM', 2837),
                                               ('2016192', 'GM', 2804),
                                               ('2020009', 'GM', 2777),
                                               ('25059530', 'GM', 2767),
                                               ('623539', 'GM', 2736);

INSERT INTO arbiter (fideId, title) VALUES
                                        ('1', NULL),
                                        ('1244582', NULL),
                                        ('623539', 'IA');

INSERT INTO tournament (id, name, organizerId) VALUES
                                                   (2, 'Cupa primaverii', 9),
                                                   (3, 'Norway Chess', 7),
                                                   (4, 'Tournament with 0 players', 9);

INSERT INTO tournamentarbiter (fideId, tournamentId, role) VALUES
                                                               ('1', 2, 'Second'),
                                                               ('1244582', 3, 'Chief');

INSERT INTO tournamentplayer (fideId, tournamentId, points) VALUES
                                                                ('1', 2, 0),
                                                                ('1', 3, 0),
                                                                ('1244582', 2, 0),
                                                                ('1244582', 3, 4.5),
                                                                ('1503014', 3, 3.5),
                                                                ('2016192', 3, 2.5),
                                                                ('25059530', 2, 0),
                                                                ('25059530', 3, 0.5);
