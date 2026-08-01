-- ============================================================
-- Vues PostgreSQL normalisées
-- Objectif : offrir des lectures "à plat" pour les rapports/dashboard
-- au-dessus du schéma normalisé généré par Hibernate (ddl-auto=update).
-- Ce sont des VIEW (pas des tables) : aucune donnée n'est dupliquée.
-- A exécuter via psql une fois les tables créées par l'application.
-- ============================================================

CREATE OR REPLACE VIEW vue_fournisseurs AS
SELECT
    f.id,
    f.nom,
    f.prenom,
    f.contact,
    t.id      AS id_type_fournisseur,
    t.libelle AS type_fournisseur
FROM fournisseurs f
JOIN typefournisseurs t ON t.id = f.typefournisseurs;

CREATE OR REPLACE VIEW vue_materielles AS
SELECT
    m.id,
    m.nom,
    m.dateentree,
    c.id      AS id_categorie,
    c.libelle AS categorie,
    s.id      AS id_statut,
    s.libelle AS statut
FROM materielles m
JOIN categoriematerielles c ON c.id = m.idcategoriematerielles
JOIN statutmaterielles s    ON s.id = m.idstatutmaterielles;

-- Agrégat utile pour le futur module "graphe" du README
CREATE OR REPLACE VIEW vue_materielles_stats AS
SELECT
    c.libelle    AS categorie,
    s.libelle    AS statut,
    COUNT(m.id)  AS nombre
FROM materielles m
JOIN categoriematerielles c ON c.id = m.idcategoriematerielles
JOIN statutmaterielles s    ON s.id = m.idstatutmaterielles
GROUP BY c.libelle, s.libelle
ORDER BY c.libelle, s.libelle;
CREATE OR REPLACE VIEW vue_historique_materielles AS
SELECT
    h.id,
    m.id   AS id_materiel,
    m.nom  AS materiel,
    h.dateentree,
    h.quantite,
    h.prixachat,
    (h.quantite * h.prixachat) AS montant,
    f.id   AS id_fournisseur,
    (f.nom || ' ' || f.prenom) AS fournisseur
FROM historiquematerielles h
JOIN materielles m ON m.id = h.idmaterielles
LEFT JOIN fournisseurs f ON f.id = h.idfournisseur;

CREATE OR REPLACE VIEW vue_maintenance_materielles AS
SELECT
    mm.id,
    m.id  AS id_materiel,
    m.nom AS materiel,
    mm.datemaintenance,
    mm.description,
    mm.cout,
    mm.technicien
FROM maintenancematerielles mm
JOIN materielles m ON m.id = mm.idmaterielles;

-- Stock courant par matériel (dernière photo EtatStockMaterielles)
CREATE OR REPLACE VIEW vue_stock_materielles AS
SELECT DISTINCT ON (m.id)
    m.id,
    m.nom,
    e.dateetatstock,
    e.quantite AS stock_actuel
FROM materielles m
LEFT JOIN etatstockmaterielles e ON e.idmaterielles = m.id
ORDER BY m.id, e.dateetatstock DESC, e.id DESC;

-- Coût total (achats + maintenance) par matériel
CREATE OR REPLACE VIEW vue_couts_materielles AS
SELECT
    m.id,
    m.nom,
    COALESCE(SUM(h.quantite * h.prixachat), 0) AS total_achats,
    COALESCE((SELECT SUM(mm.cout) FROM maintenancematerielles mm WHERE mm.idmaterielles = m.id), 0) AS total_maintenance
FROM materielles m
LEFT JOIN historiquematerielles h ON h.idmaterielles = m.id
GROUP BY m.id, m.nom;