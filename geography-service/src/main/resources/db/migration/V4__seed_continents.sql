INSERT INTO continents (id_continent, code)
VALUES (1, 'AF'),
       (2, 'AN'),
       (3, 'AS'),
       (4, 'EU'),
       (5, 'NA'),
       (6, 'OC'),
       (7, 'SA')
ON CONFLICT (code) DO NOTHING;

SELECT setval(
               pg_get_serial_sequence('geography.continents', 'id_continent'),
               (SELECT MAX(id_continent) FROM continents)
       );