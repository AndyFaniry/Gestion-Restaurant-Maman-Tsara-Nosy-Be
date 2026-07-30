-- Clean up (ordre pour respecter les contraintes de clés étrangères)
DELETE FROM detailscommandes;
DELETE FROM facturescommandes;
DELETE FROM commandes;
DELETE FROM mouvementcaisse;
DELETE FROM typemouvementcaisse;
DELETE FROM inventaireingredient;
DELETE FROM etatstockingredient;
DELETE FROM historiqueingredients;
DELETE FROM recetteplats;
DELETE FROM ingredients;
DELETE FROM statutingredient;
DELETE FROM typemvtingredient;
DELETE FROM categorieingredients;
DELETE FROM clients;
DELETE FROM typeclient;
DELETE FROM fournisseurs;
DELETE FROM typefournisseurs;
DELETE FROM fichepaie;
DELETE FROM personnels;
DELETE FROM rolepersonnels;
DELETE FROM materielles;
DELETE FROM categoriematerielles;
DELETE FROM statutmaterielles;
DELETE FROM zoneslivraison;
DELETE FROM plats;
DELETE FROM categorieplats;

-- Types et références de base
INSERT INTO typeclient (id, libelle) VALUES 
(1, 'Passager'),
(2, 'Habitué'),
(3, 'VIP Touriste');

INSERT INTO typefournisseurs (id, libelle) VALUES 
(1, 'Pêcheur Local'),
(2, 'Grossiste Alimentaire'),
(3, 'Fournisseur Boissons');

INSERT INTO rolepersonnels (id, libelle) VALUES 
(1, 'Chef Cuisinier'),
(2, 'Serveur'),
(3, 'Caissier'),
(4, 'Livreur');

INSERT INTO categorieplats (id, libelle) VALUES 
(1, 'Entrées / Tapas'),
(2, 'Plats de Résistance'),
(3, 'Desserts'),
(4, 'Boissons');

INSERT INTO categorieingredients (id, libelle) VALUES 
(1, 'Produits de la mer'),
(2, 'Légumes & Fruits'),
(3, 'Épices & Condiments'),
(4, 'Produits Secs');

INSERT INTO statutingredient (id, libelle) VALUES 
(1, 'Disponible'),
(2, 'Rupture de Stock'),
(3, 'Commande en cours');

INSERT INTO typemvtingredient (id, libelle) VALUES 
(1, 'Entrée'),
(2, 'Sortie (Cuisine)'),
(3, 'Perte / Périmé');

INSERT INTO categoriematerielles (id, libelle) VALUES 
(1, 'Équipement Cuisine'),
(2, 'Mobilier Salle'),
(3, 'Matériel Informatique');

INSERT INTO statutmaterielles (id, libelle) VALUES 
(1, 'En Service'),
(2, 'En Maintenance'),
(3, 'Hors Service');

INSERT INTO typemouvementcaisse (id, libelle) VALUES 
(1, 'Vente Plat'),
(2, 'Achat Fournisseur'),
(3, 'Paiement Salaire'),
(4, 'Frais Généraux');

-- Zones de livraison (Nosy Be)
INSERT INTO zoneslivraison (id, libelle, min, max, prix) VALUES 
(1, 'Hell-Ville Center', 0.000, 5.000, 5000.000),
(2, 'Ambatoloaka / Madirokely', 5.000, 15.000, 10000.000),
(3, 'Andilana', 15.000, 30.000, 25000.000);

-- Clients
INSERT INTO clients (id, nom, prenom, contact, idtypeclient) VALUES 
(1, 'Rasoa', 'Marie', '0340011122', 2),
(2, 'Dubois', 'Jean', '0321122233', 3),
(3, 'Andry', 'Rakoto', '0332233344', 1);

-- Fournisseurs
INSERT INTO fournisseurs (id, nom, prenom, contact, typefournisseurs) VALUES 
(1, 'Pêcherie d Hell-Ville', 'Gérard', '0341234567', 1),
(2, 'Nosy Be Primeurs', 'Fatima', '0327654321', 2);

-- Personnels
INSERT INTO personnels (id, nom, prenom, contact, idrolepersonnels, dateembauche) VALUES 
(1, 'Koto', 'Jean', '0345566778', 1, '2024-01-15'),
(2, 'Bao', 'Soa', '0329988776', 2, '2024-03-01');

-- Ingrédients
INSERT INTO ingredients (id, nom, idcategorieingredients, idstatutingredient, idfournisseur) VALUES 
(1, 'Camaron / Crevette Géante', 1, 1, 1),
(2, 'Riz Parfumé', 4, 1, 2),
(3, 'Lait de Coco', 2, 1, 2);

-- Plats & Recettes
INSERT INTO plats (id, nom, idcategorieplats, prixvente) VALUES 
(1, 'Camarons au Lait de Coco', 2, 35000.000),
(2, 'Riz Nature', 1, 4000.000);

INSERT INTO recetteplats (id, idplat, idingredient, quantiterequise) VALUES 
(1, 1, 1, 0.300),
(2, 1, 3, 0.200),
(3, 2, 2, 0.150);

-- Stock & Historique Ingrédients
INSERT INTO historiqueingredients (id, idingredient, dateentree, dateperemption, quantite, prixachat) VALUES 
(1, 1, '2026-07-28', '2026-08-05', 10.000, 20000.000),
(2, 2, '2026-07-20', '2026-12-31', 50.000, 2500.000);

INSERT INTO etatstockingredient (id, idingredient, dateetatstock, quantite) VALUES 
(1, 1, '2026-07-30', 8.500),
(2, 2, '2026-07-30', 45.000);

-- Commandes & Détails
INSERT INTO commandes (id, idclient, datecommande, idzonelivraison, montanttotal) VALUES 
(1, 2, '2026-07-30', 2, 45000.000);

INSERT INTO detailscommandes (id, idcommande, idplat, quantite, prixunitaire, montant) VALUES 
(1, 1, 1, 1.000, 35000.000, 35000.000);

INSERT INTO facturescommandes (id, idcommande, datefacture, montanttotal) VALUES 
(1, 1, '2026-07-30', 45000.000);

-- Caisse
INSERT INTO mouvementcaisse (id, datemouvement, montant, typemouvement) VALUES 
(1, '2026-07-30', 45000.000, 1);