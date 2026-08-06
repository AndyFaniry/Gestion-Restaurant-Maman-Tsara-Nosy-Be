-- Données de référence pour les tests (IDs explicites pour stabilité)
INSERT INTO unite (id, nom, symbole) VALUES
  (1, 'Gramme', 'g'),
  (2, 'Kilogramme', 'kg'),
  (3, 'Litre', 'l'),
  (4, 'Pièce', 'pcs');

INSERT INTO typeclient (id, libelle) VALUES
  (1, 'Particulier'),
  (2, 'Professionnel');

INSERT INTO typefournisseurs (id, libelle) VALUES
  (1, 'Alimentaire'),
  (2, 'Materiel');

INSERT INTO rolepersonnels (id, libelle) VALUES
  (1, 'Cuisinier'),
  (2, 'Serveur');

INSERT INTO categorieplats (id, libelle) VALUES
  (1, 'Plat principal'),
  (2, 'Boisson');

INSERT INTO categorieingredients (id, libelle) VALUES
  (1, 'Viande'),
  (2, 'Legume');

INSERT INTO statutingredient (id, libelle) VALUES
  (1, 'Actif'),
  (2, 'Inactif');

INSERT INTO typemvtingredient (id, libelle) VALUES
  (1, 'Entrée'),
  (2, 'Sortie (Cuisine)'),
  (3, 'Perte / Périmé');

INSERT INTO categoriematerielles (id, libelle) VALUES
  (1, 'Cuisine'),
  (2, 'Salle');

INSERT INTO statutmaterielles (id, libelle) VALUES
  (1, 'En service'),
  (2, 'En maintenance'),
  (3, 'Hors Service');

INSERT INTO typemvtmaterielles (id, libelle) VALUES
  (1, 'Entree'),
  (2, 'Maintenance'),
  (3, 'HorsService');

INSERT INTO typemouvementcaisse (id, libelle) VALUES
  (1, 'Entree'),
  (2, 'Sortie');

INSERT INTO raisonabsence (id, libelle) VALUES
  (1, 'Maladie'),
  (2, 'Congé');

-- Remise à niveau des séquences (PostgreSQL IDENTITY)
SELECT setval(pg_get_serial_sequence('unite', 'id'), (SELECT MAX(id) FROM unite));
SELECT setval(pg_get_serial_sequence('typeclient', 'id'), (SELECT MAX(id) FROM typeclient));
SELECT setval(pg_get_serial_sequence('typefournisseurs', 'id'), (SELECT MAX(id) FROM typefournisseurs));
SELECT setval(pg_get_serial_sequence('rolepersonnels', 'id'), (SELECT MAX(id) FROM rolepersonnels));
SELECT setval(pg_get_serial_sequence('categorieplats', 'id'), (SELECT MAX(id) FROM categorieplats));
SELECT setval(pg_get_serial_sequence('categorieingredients', 'id'), (SELECT MAX(id) FROM categorieingredients));
SELECT setval(pg_get_serial_sequence('statutingredient', 'id'), (SELECT MAX(id) FROM statutingredient));
SELECT setval(pg_get_serial_sequence('typemvtingredient', 'id'), (SELECT MAX(id) FROM typemvtingredient));
SELECT setval(pg_get_serial_sequence('categoriematerielles', 'id'), (SELECT MAX(id) FROM categoriematerielles));
SELECT setval(pg_get_serial_sequence('statutmaterielles', 'id'), (SELECT MAX(id) FROM statutmaterielles));
SELECT setval(pg_get_serial_sequence('typemvtmaterielles', 'id'), (SELECT MAX(id) FROM typemvtmaterielles));
SELECT setval(pg_get_serial_sequence('typemouvementcaisse', 'id'), (SELECT MAX(id) FROM typemouvementcaisse));
SELECT setval(pg_get_serial_sequence('raisonabsence', 'id'), (SELECT MAX(id) FROM raisonabsence));
