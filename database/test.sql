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
DELETE FROM unite;
DELETE FROM maintenancematerielles;
DELETE FROM etatstockmaterielles;
DELETE FROM inventairesmaterielles;
DELETE FROM historiquematerielles;
DELETE FROM typemvtmaterielles;
DELETE FROM materielles;
DELETE FROM categoriematerielles;
DELETE FROM statutmaterielles;
-- Insertion directe avec la valeur NULL pour le symbole
INSERT INTO unite (nom, symbole) VALUES 
('Gramme', 'g'), ('Kilogramme', 'kg'), ('Litre', 'L'), ('Pièce', 'pcs');


-- Le symbole est NULL ici
INSERT INTO unite (nom, symbole)
VALUES ('Sachet', NULL);
-- Types et références de base
INSERT INTO typeclient (id, libelle)
VALUES (1, 'Passager'),
    (2, 'Habitué'),
    (3, 'VIP Touriste');
INSERT INTO typefournisseurs (id, libelle)
VALUES (1, 'Pêcheur Local'),
    (2, 'Grossiste Alimentaire'),
    (3, 'Fournisseur Boissons');
INSERT INTO rolepersonnels (id, libelle)
VALUES (1, 'Chef Cuisinier'),
    (2, 'Serveur'),
    (3, 'Caissier'),
    (4, 'Livreur');
INSERT INTO categorieplats (id, libelle)
VALUES (1, 'Entrées / Tapas'),
    (2, 'Plats de Résistance'),
    (3, 'Desserts'),
    (4, 'Boissons');
INSERT INTO categorieingredients (id, libelle)
VALUES (1, 'Produits de la mer'),
    (2, 'Légumes & Fruits'),
    (3, 'Épices & Condiments'),
    (4, 'Produits Secs');
INSERT INTO statutingredient (id, libelle)
VALUES (1, 'Disponible'),
    (2, 'Rupture de Stock'),
    (3, 'Commande en cours');
INSERT INTO typemvtingredient (id, libelle)
VALUES (1, 'Entrée'),
    (2, 'Sortie (Cuisine)'),
    (3, 'Perte / Périmé');
INSERT INTO categoriematerielles (id, libelle)
VALUES (1, 'Équipement Cuisine'),
    (2, 'Mobilier Salle'),
    (3, 'Matériel Informatique');
INSERT INTO statutmaterielles (id, libelle)
VALUES (1, 'En Service'),
    (2, 'En Maintenance'),
    (3, 'Hors Service');
-- Types de mouvement Materielles (Entree / Maintenance / HorsService — utilisés par MateriellesService)
INSERT INTO typemvtmaterielles (id, libelle)
VALUES (1, 'Entree'),
    (2, 'Maintenance'),
    (3, 'HorsService');
-- Fiches Materielles
INSERT INTO materielles (
        id,
        nom,
        dateentree,
        idcategoriematerielles,
        idstatutmaterielles
    )
VALUES (
        1,
        'Four à pizza professionnel',
        '2025-01-10',
        1,
        1
    ),
    (2, 'Réfrigérateur 400L', '2024-06-01', 1, 1),
    (
        3,
        'Tables terrasse (lot de 10)',
        '2023-11-20',
        2,
        1
    ),
    (4, 'Caisse enregistreuse', '2025-03-15', 3, 2);
-- Historique des achats (suivi prix)
INSERT INTO historiquematerielles (
        id,
        idmaterielles,
        dateentree,
        prixachat,
        quantite,
        idfournisseur
    )
VALUES (1, 1, '2025-01-10', 4500000.000, 1.000, NULL),
    (2, 2, '2024-06-01', 3200000.000, 1.000, NULL),
    (3, 3, '2023-11-20', 150000.000, 10.000, NULL),
    (4, 4, '2025-03-15', 890000.000, 1.000, NULL);
-- Journal des mouvements (Inventaire Materielles)
INSERT INTO inventairesmaterielles (
        id,
        idmaterielles,
        dateinventaire,
        quantite,
        typemvtmaterielles
    )
VALUES (1, 1, '2025-01-10', 1.000, 1),
    (2, 2, '2024-06-01', 1.000, 1),
    (3, 3, '2023-11-20', 10.000, 1),
    (4, 4, '2025-03-15', 1.000, 1),
    (5, 4, '2026-07-25', 1.000, 2);
-- Stock courant (photo la plus récente par matériel)
INSERT INTO etatstockmaterielles (id, idmaterielles, dateetatstock, quantite)
VALUES (1, 1, '2026-07-30', 1.000),
    (2, 2, '2026-07-30', 1.000),
    (3, 3, '2026-07-30', 10.000),
    (4, 4, '2026-07-30', 1.000);
-- Maintenance
INSERT INTO maintenancematerielles (
        id,
        idmaterielles,
        datemaintenance,
        description,
        cout,
        technicien
    )
VALUES (
        1,
        1,
        '2026-07-25',
        'Remplacement du rouleau papier et réparation du tiroir-caisse',
        45000.000,
        'Rija Electro'
    );
INSERT INTO typemouvementcaisse (libelle) VALUES ('Entree'), ('Sortie');
-- Zones de livraison (Nosy Be)
INSERT INTO zoneslivraison (id, libelle, min, max, prix)
VALUES (1, 'Hell-Ville Center', 0.000, 5.000, 5000.000),
    (
        2,
        'Ambatoloaka / Madirokely',
        5.000,
        15.000,
        10000.000
    ),
    (3, 'Andilana', 15.000, 30.000, 25000.000);
-- Clients
INSERT INTO clients (id, nom, prenom, contact, idtypeclient)
VALUES (1, 'Rasoa', 'Marie', '0340011122', 2),
    (2, 'Dubois', 'Jean', '0321122233', 3),
    (3, 'Andry', 'Rakoto', '0332233344', 1);
-- Fournisseurs
INSERT INTO fournisseurs (id, nom, prenom, contact, typefournisseurs)
VALUES (
        1,
        'Pêcherie d Hell-Ville',
        'Gérard',
        '0341234567',
        1
    ),
    (2, 'Nosy Be Primeurs', 'Fatima', '0327654321', 2);
-- Personnels
INSERT INTO personnels (
        id,
        nom,
        prenom,
        contact,
        idrolepersonnels,
        dateembauche
    )
VALUES (1, 'Koto', 'Jean', '0345566778', 1, '2024-01-15'),
    (2, 'Bao', 'Soa', '0329988776', 2, '2024-03-01');
-- Ingrédients
INSERT INTO ingredients (
        id,
        nom,
        idcategorieingredients,
        idstatutingredient,
        idfournisseur,
        idUnite
    )
VALUES (1, 'Camaron / Crevette Géante', 1, 1, 1, 1),
    (2, 'Riz Parfumé', 4, 1, 2, 2),
    (3, 'Lait de Coco', 2, 1, 2, 3);
-- Plats & Recettes
INSERT INTO plats (id, nom, idcategorieplats, prixvente)
VALUES (1, 'Camarons au Lait de Coco', 2, 35000.000),
    (2, 'Riz Nature', 1, 4000.000);
INSERT INTO recetteplats (id, idplat, idingredient, quantiterequise)
VALUES (1, 1, 1, 0.300),
    (2, 1, 3, 0.200),
    (3, 2, 2, 0.150);
-- Stock & Historique Ingrédients
INSERT INTO historiqueingredients (
        id,
        idingredient,
        dateentree,
        dateperemption,
        quantite,
        prixachat
    )
VALUES (
        1,
        1,
        '2026-07-28',
        '2026-08-05',
        10.000,
        20000.000
    ),
    (
        2,
        2,
        '2026-07-20',
        '2026-12-31',
        50.000,
        2500.000
    );
INSERT INTO etatstockingredient (id, idingredient, dateetatstock, quantite)
VALUES (1, 1, '2026-07-30', 8.500),
    (2, 2, '2026-07-30', 45.000);
-- Commandes & Détails
INSERT INTO commandes (
        id,
        idclient,
        datecommande,
        idzonelivraison,
        montanttotal
    )
VALUES (1, 2, '2026-07-30', 2, 45000.000);
INSERT INTO detailscommandes (
        id,
        idcommande,
        idplat,
        quantite,
        prixunitaire,
        montant
    )
VALUES (1, 1, 1, 1.000, 35000.000, 35000.000);
INSERT INTO facturescommandes (id, idcommande, datefacture, montanttotal)
VALUES (1, 1, '2026-07-30', 45000.000);
-- Caisse
INSERT INTO mouvementcaisse (id, datemouvement, montant, typemouvement)
VALUES (1, '2026-07-30', 45000.000, 1);

INSERT INTO mouvementcaisse (id, datemouvement, montant, typemouvement) VALUES 
(2, '2025-03-15', 890000.000, 1),  -- Achat Fournisseur : caisse enregistreuse
(3, '2026-07-25',  45000.000, 2);  -- Frais Généraux : maintenance caisse enregistreuse
INSERT INTO raisonabsence (libelle) VALUES ('Maladie'), ('Congé Payé'), ('Absence Injustifiée'), ('Maternité/Paternité') ON CONFLICT DO NOTHING