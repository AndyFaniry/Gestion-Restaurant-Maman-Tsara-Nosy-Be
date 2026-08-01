-- Active: 1785442317642@@127.0.0.1@5432@restaurant@public
\c restaurant;
--modules ingredients et plats  et fournisseurs 
CREATE TABLE Unite(
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE,
    symbole VARCHAR(50)
);
CREATE TABLE typefournisseurs (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE fournisseurs (
    id BIGSERIAL PRIMARY KEY,
    typefournisseurs BIGINT NOT NULL REFERENCES typefournisseurs(id),
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL
);
CREATE TABLE categorieingredients (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE statutingredient (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE ingredients (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    idcategorieingredients BIGINT NOT NULL REFERENCES categorieingredients(id),
    idstatutingredient BIGINT NOT NULL REFERENCES statutingredient(id),
    idfournisseur BIGINT NOT NULL REFERENCES fournisseurs(id),
    idunite BIGINT NOT NULL REFERENCES unite(id)
);
CREATE TABLE historiqueingredients (
    id BIGSERIAL PRIMARY KEY,
    idingredient BIGINT NOT NULL REFERENCES ingredients(id),
    dateentree DATE NOT NULL,
    dateperemption DATE CHECK (dateperemption IS NULL OR dateperemption >= dateentree),
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite > 0),
    prixachat DECIMAL(16, 3) NOT NULL CHECK (prixachat >= 0)
);
CREATE TABLE typemvtingredient (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE inventaireingredient (
    id BIGSERIAL PRIMARY KEY,
    idingredient BIGINT NOT NULL REFERENCES ingredients(id),
    dateinventaire DATE NOT NULL,
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite > 0),
    typemvtingredient BIGINT NOT NULL REFERENCES typemvtingredient(id)
);
CREATE TABLE etatstockingredient (
    id BIGSERIAL PRIMARY KEY,
    idingredient BIGINT NOT NULL REFERENCES ingredients(id),
    dateetatstock DATE NOT NULL,
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite >= 0)
);
CREATE TABLE categorieplats (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE plats (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    idcategorieplats BIGINT NOT NULL REFERENCES categorieplats(id),
    prixvente DECIMAL(16, 3) NOT NULL CHECK (prixvente >= 0)
);
CREATE TABLE recetteplats (
    id BIGSERIAL PRIMARY KEY,
    idplat BIGINT NOT NULL REFERENCES plats(id),
    idingredient BIGINT NOT NULL REFERENCES ingredients(id),
    quantiterequise DECIMAL(16, 3) NOT NULL CHECK (quantiterequise > 0)
);
-- Materielles 
CREATE TABLE categoriematerielles (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE statutmaterielles (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE materielles (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    dateentree DATE NOT NULL,
    idcategoriematerielles BIGINT NOT NULL REFERENCES categoriematerielles(id),
    idstatutmaterielles BIGINT NOT NULL REFERENCES statutmaterielles(id)
);
CREATE TABLE historiquematerielles (
    id BIGSERIAL PRIMARY KEY,
    idmaterielles BIGINT NOT NULL REFERENCES materielles(id),
    dateentree DATE NOT NULL,
    prixachat NUMERIC(16, 3) NOT NULL,
    quantite NUMERIC(16, 3) NOT NULL,
    idfournisseur BIGINT REFERENCES fournisseurs(id)
);
CREATE TABLE typemvtmaterielles (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE inventairesmaterielles (
    id BIGSERIAL PRIMARY KEY,
    idmaterielles BIGINT NOT NULL REFERENCES materielles(id),
    dateinventaire DATE NOT NULL,
    quantite NUMERIC(16, 3) NOT NULL,
    typemvtmaterielles BIGINT NOT NULL REFERENCES typemvtmaterielles(id)
);
CREATE TABLE etatstockmaterielles (
    id BIGSERIAL PRIMARY KEY,
    idmaterielles BIGINT NOT NULL REFERENCES materielles(id),
    dateetatstock DATE NOT NULL,
    quantite NUMERIC(16, 3) NOT NULL CHECK (quantite >= 0)
);

CREATE TABLE maintenancematerielles (
    id BIGSERIAL PRIMARY KEY,
    idmaterielles BIGINT REFERENCES materielles(id),
    datemaintenance DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    cout NUMERIC(16, 3) NOT NULL,
    technicien VARCHAR(100)
);
-- Personnels 
CREATE TABLE rolepersonnels (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE personnels (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    idrolepersonnels BIGINT NOT NULL REFERENCES rolepersonnels(id),
    dateembauche DATE NOT NULL
);
CREATE TABLE fichepaie (
    id BIGSERIAL PRIMARY KEY,
    idpersonnels BIGINT NOT NULL REFERENCES personnels(id),
    datepaie DATE NOT NULL,
    salaire DECIMAL(16, 3) NOT NULL CHECK (salaire >= 0),
    montanttotal DECIMAL(16, 3) NOT NULL CHECK (montanttotal >= 0)
);
CREATE TABLE HistoriqueSalaire(
    id SERIAL PRIMARY KEY,
    idPersonnels INT NOT NULL,
    dateDebut Date NOT NULL,
    dateFin Date CHECK(dateFin IS NULL OR dateFin >= dateDebut),
    salaire DECIMAL(16, 3) NOT NULL CHECK (salaire >= 0),
    Foreign KEY (idPersonnels) REFERENCES Personnels(id)
);
CREATE TABLE RaisonAbsence(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE AbsencePersonnels(
    id SERIAL PRIMARY KEY,
    idPersonnels INT NOT NULL,
    dateDebut Date NOT NULL,
    dateFin Date CHECK(dateFin IS NULL OR dateFin >= dateDebut),
    idRaisonAbsence INT NOT NULL,
    Foreign KEY (idPersonnels) REFERENCES Personnels(id),
    Foreign KEY (idRaisonAbsence) REFERENCES RaisonAbsence(id)
);
CREATE TABLE BonusGlobaleSalairePersonnels(
    id SERIAL PRIMARY KEY,
    idPersonnels INT NOT NULL,
    dateBonus Date NOT NULL,
    montantBonus DECIMAL(16, 3) NOT NULL CHECK (montantBonus >= 0),
    Foreign KEY (idPersonnels) REFERENCES Personnels(id)
);
--Clients 
CREATE TABLE typeclient (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    idtypeclient BIGINT NOT NULL REFERENCES typeclient(id)
);
-- Commandes et zones Livraisons et factures 
CREATE TABLE zoneslivraison (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    min DECIMAL(16, 3) NOT NULL,
    max DECIMAL(16, 3) NOT NULL,
    prix DECIMAL(16, 3) NOT NULL CHECK (prix >= 0)
);
CREATE TABLE commandes (
    id BIGSERIAL PRIMARY KEY,
    idclient BIGINT NOT NULL REFERENCES clients(id),
    datecommande DATE NOT NULL,
    idzonelivraison BIGINT NOT NULL REFERENCES zoneslivraison(id),
    montanttotal DECIMAL(16, 3) NOT NULL CHECK (montanttotal >= 0)
);
CREATE TABLE detailscommandes (
    id BIGSERIAL PRIMARY KEY,
    idcommande BIGINT NOT NULL REFERENCES commandes(id),
    idplat BIGINT NOT NULL REFERENCES plats(id),
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite > 0),
    prixunitaire DECIMAL(16, 3) NOT NULL CHECK (prixunitaire >= 0),
    montant DECIMAL(16, 3) NOT NULL CHECK (montant >= 0)
);
CREATE TABLE facturescommandes (
    id BIGSERIAL PRIMARY KEY,
    idcommande BIGINT NOT NULL REFERENCES commandes(id),
    datefacture DATE NOT NULL,
    montanttotal DECIMAL(16, 3) NOT NULL CHECK (montanttotal >= 0)
);
CREATE TABLE typemouvementcaisse (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE mouvementcaisse (
    id BIGSERIAL PRIMARY KEY,
    datemouvement DATE NOT NULL,
    montant DECIMAL(16, 3) NOT NULL CHECK (montant > 0),
    typemouvement BIGINT NOT NULL REFERENCES typemouvementcaisse(id)
);