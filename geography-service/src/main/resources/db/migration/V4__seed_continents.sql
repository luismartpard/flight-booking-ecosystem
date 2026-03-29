INSERT INTO continents (id_continent, code, name)
VALUES (1, 'AF', 'Africa'),
       (2, 'AN', 'Antarctica'),
       (3, 'AS', 'Asia'),
       (4, 'EU', 'Europe'),
       (5, 'NA', 'North America'),
       (6, 'OC', 'Oceania'),
       (7, 'SA', 'South America')
ON CONFLICT (code) DO NOTHING;

SELECT setval(
               pg_get_serial_sequence('geography.continents', 'id_continent'),
               (SELECT MAX(id_continent) FROM continents)
       );