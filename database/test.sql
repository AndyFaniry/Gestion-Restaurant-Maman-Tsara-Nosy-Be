
-- Insertion directe avec la valeur NULL pour le symbole
INSERT INTO unite (nom, symbole) VALUES 
('Gramme', 'g'), ('Kilogramme', 'kg'), ('Litre', 'L'), ('Pièce', 'pcs');


-- Le symbole est NULL ici
INSERT INTO unite (nom, symbole)
VALUES ('Sachet', NULL);
-- Types et références de base
INSERT INTO typeclient ( libelle)
VALUES ( 'Passager'),
    ( 'Habitué'),
    ( 'VIP Touriste');
INSERT INTO typefournisseurs ( libelle)
VALUES ( 'Pêcheur Local'),
    ( 'Grossiste Alimentaire'),
    ( 'Fournisseur Boissons');
INSERT INTO rolepersonnels ( libelle)
VALUES ( 'Chef Cuisinier'),
    ( 'Serveur'),
    ( 'Caissier'),
    ( 'Livreur');
INSERT INTO categorieplats ( libelle)
VALUES ( 'Entrées / Tapas'),
    ( 'Plats de Résistance'),
    ( 'Desserts'),
    ( 'Boissons');
INSERT INTO categorieingredients ( libelle)
VALUES ( 'Produits de la mer'),
    ( 'Légumes & Fruits'),
    ( 'Épices & Condiments'),
    ( 'Produits Secs');
INSERT INTO statutingredient ( libelle)
VALUES ( 'Disponible'),
    ( 'Rupture de Stock'),
    ( 'Commande en cours');
INSERT INTO typemvtingredient ( libelle)
VALUES ( 'Entrée'),
    ( 'Sortie (Cuisine)'),
    ( 'Perte / Périmé');
INSERT INTO categoriematerielles ( libelle)
VALUES ( 'Équipement Cuisine'),
    ( 'Mobilier Salle'),
    ( 'Matériel Informatique');
INSERT INTO statutmaterielles ( libelle)
VALUES ( 'En Service'),
    ( 'En Maintenance'),
    ( 'Hors Service');
-- Types de mouvement Materielles (Entree / Maintenance / HorsService — utilisés par MateriellesService)
INSERT INTO typemvtmaterielles ( libelle)
VALUES ( 'Entree'),
    ( 'Maintenance'),
    ( 'HorsService');
-- Fiches Materielles
INSERT INTO materielles (
      
        nom,
        dateentree,
        idcategoriematerielles,
        idstatutmaterielles
    )
VALUES (
        
        'Four à pizza professionnel',
        '2025-01-10',
        1,
        1
    ),
    ( 'Réfrigérateur 400L', '2024-06-01', 1, 1),
    (
        
        'Tables terrasse (lot de 10)',
        '2023-11-20',
        2,
        1
    ),
    ( 'Caisse enregistreuse', '2025-03-15', 3, 2);
-- Historique des achats (suivi prix)
INSERT INTO historiquematerielles (
    
        idmaterielles,
        dateentree,
        prixachat,
        quantite,
        idfournisseur
    )
VALUES (  1,'2025-01-10', 4500000.000, 1.000, NULL),
    (  2,'2024-06-01', 3200000.000, 1.000, NULL),
    (  3,'2023-11-20', 150000.000, 10.000, NULL),
    (  4,'2025-03-15', 890000.00, 10.000, NULL);
-- Journal des mouvements (Inventaire Materielles)
INSERT INTO inventairesmaterielles (
        idmaterielles,
        dateinventaire,
        quantite,
        typemvtmaterielles
    )
VALUES ( 1, '2025-01-10', 1.000, 1),
    ( 2, '2024-06-01', 1.000, 1),
    ( 3, '2023-11-20', 10.000, 1),
    ( 4, '2025-03-15', 1.000, 1),
    ( 4, '2026-07-25', 1.000, 2);
-- Stock courant (photo la plus récente par matériel)
INSERT INTO etatstockmaterielles ( idmaterielles, dateetatstock, quantite)
VALUES ( 1, '2026-07-30', 1.000),
    ( 2, '2026-07-30', 1.000),
    ( 3, '2026-07-30', 10.000),
    ( 4, '2026-07-30', 1.000);
-- Maintenance
INSERT INTO maintenancematerielles (
        
        idmaterielles,
        datemaintenance,
        description,
        cout,
        technicien
    )
VALUES (
        1,
        '2026-07-25',
        'Remplacement du rouleau papier et réparation du tiroir-caisse',
        45000.000,
        'Rija Electro'
    );
INSERT INTO typemouvementcaisse (libelle) VALUES ('Entree'), ('Sortie');
-- Zones de livraison (Nosy Be)
INSERT INTO zoneslivraison ( libelle, min, max, prix)
VALUES ( 'Hell-Ville Center', 0.000, 5.000, 5000.000),
    (
        
        'Ambatoloaka / Madirokely',
        5.000,
        15.000,
        10000.000
    ),
    ( 'Andilana', 15.000, 30.000, 25000.000);
-- Clients
INSERT INTO clients ( nom, prenom, contact, idtypeclient)
VALUES ( 'Rasoa', 'Marie', '0340011122', 2),
    ( 'Dubois', 'Jean', '0321122233', 3),
    ( 'Andry', 'Rakoto', '0332233344', 1);
-- Fournisseurs
INSERT INTO fournisseurs ( nom, prenom, contact, typefournisseurs)
VALUES (
        
        'Pêcherie d Hell-Ville',
        'Gérard',
        '0341234567',
        1
    ),
    ( 'Nosy Be Primeurs', 'Fatima', '0327654321', 2);
-- Personnels
INSERT INTO personnels (
        
        nom,
        prenom,
        contact,
        idrolepersonnels,
        dateembauche
    )
VALUES ( 'Koto', 'Jean', '0345566778', 1, '2024-01-15'),
    ( 'Bao', 'Soa', '0329988776', 2, '2024-03-01');
-- Ingrédients
INSERT INTO ingredients (
        nom,
        idcategorieingredients,
        idstatutingredient,
        idfournisseur,
        idUnite
    )
VALUES ( 'Camaron / Crevette Géante', 1, 1, 1, 1),
    ( 'Riz Parfumé', 4, 1, 2, 2),
    ( 'Lait de Coco', 2, 1, 2, 3);
-- Plats & Recettes
INSERT INTO plats ( nom, idcategorieplats, prixvente)
VALUES ( 'Camarons au Lait de Coco', 2, 35000.000),
    ( 'Riz Nature', 1, 4000.000);
INSERT INTO recetteplats ( idplat, idingredient, quantiterequise)
VALUES ( 1, 1, 0.300),
    ( 1, 3, 0.200),
    ( 2, 2, 0.150);
-- Stock & Historique Ingrédients
INSERT INTO historiqueingredients (
        
        idingredient,
        dateentree,
        dateperemption,
        quantite,
        prixachat
    )
VALUES (
        
        1,
        '2026-07-28',
        '2026-08-05',
        10.000,
        20000.000
    ),
    (
        2,
        '2026-07-20',
        '2026-12-31',
        50.000,
        2500.000
    );
INSERT INTO etatstockingredient ( idingredient, dateetatstock, quantite)
VALUES ( 1, '2026-07-30', 8.500),
    ( 2, '2026-07-30', 45.000);
-- Commandes & Détails
INSERT INTO commandes (
        
        idclient,
        datecommande,
        idzonelivraison,
        montanttotal
    )
VALUES ( 2, '2026-07-30', 2, 45000.000);
INSERT INTO detailscommandes (
        idcommande,
        idplat,
        quantite,
        prixunitaire,
        montant
    )
VALUES ( 1, 1, 1.000, 35000.000, 35000.000);
INSERT INTO facturescommandes ( idcommande, datefacture, montanttotal)
VALUES ( 1, '2026-07-30', 45000.000);
-- Caisse
INSERT INTO mouvementcaisse ( datemouvement, montant, typemouvement)
VALUES ( '2026-07-30', 45000.000, 1);

INSERT INTO mouvementcaisse ( datemouvement, montant, typemouvement) VALUES 
( '2025-03-15', 890000.000, 1),  -- Achat Fournisseur : caisse enregistreuse
( '2026-07-25',  45000.000, 2);  -- Frais Généraux : maintenance caisse enregistreuse
INSERT INTO raisonabsence (libelle) VALUES ('Maladie'), ('Congé Payé'), ('Absence Injustifiée'), ('Maternité/Paternité') ON CONFLICT DO NOTHING