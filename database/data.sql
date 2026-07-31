-- Active: 1785442317642@@127.0.0.1@5432@restaurant
\c restaurant;
--modules ingredients et plats  et fournisseurs 
CREATE TABLE Unite(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE,
    symbole VARCHAR(50)
);
CREATE TABLE TypeFournisseurs(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE Fournisseurs(
    id SERIAL PRIMARY KEY,
    typeFournisseurs INT NOT NULL,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    Foreign Key (typeFournisseurs) REFERENCES TypeFournisseurs(id)
);
CREATE TABLE CategorieIngredients(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE StatutIngredient(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE Ingredients(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    idCategorieIngredients INT NOT NULL,
    idStatutIngredient INT NOT NULL,
    idFournisseur INT NOT NULL,
    idUnite INT NOT NULL,
    Foreign Key (idUnite) REFERENCES Unite(id),
    Foreign Key (idCategorieIngredients) REFERENCES CategorieIngredients(id),
    Foreign KEY (idStatutIngredient) REFERENCES StatutIngredient(id),
    Foreign KEY (idFournisseur) REFERENCES Fournisseurs(id)
);
CREATE TABLE HistoriqueIngredients(
    id SERIAL PRIMARY KEY,
    idIngredient INT NOT NULL,
    dateEntree Date NOT NULL,
    datePeremption Date CHECK( datePeremption IS NULL OR datePeremption >= dateEntree),
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite > 0),
    prixAchat DECIMAL(16, 3) NOT NULL CHECK (prixAchat >= 0),
    Foreign KEY (idIngredient) REFERENCES Ingredients(id)
);
CREATE TABLE TypeMvtIngredient(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE InventaireIngredient(
    id SERIAL PRIMARY KEY,
    idIngredient INT NOT NULL,
    dateInventaire Date NOT NULL,
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite > 0),
    typeMvtIngredient INT NOT NULL,
    Foreign KEY (idIngredient) REFERENCES Ingredients(id),
    Foreign KEY (typeMvtIngredient) REFERENCES TypeMvtIngredient(id)
);
CREATE TABLE EtatStockIngredient(
    id SERIAL PRIMARY KEY,
    idIngredient INT NOT NULL,
    dateEtatStock Date NOT NULL,
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite > 0),
    Foreign KEY (idIngredient) REFERENCES Ingredients(id)
);
CREATE TABLE CategoriePlats(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE Plats(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    idCategoriePlats INT NOT NULL,
    prixVente DECIMAL(16, 3) NOT NULL CHECK (prixVente >= 0),
    Foreign KEY (idCategoriePlats) REFERENCES CategoriePlats(id)
);
CREATE TABLE RecettePlats(
    id SERIAL PRIMARY KEY,
    idPlat INT NOT NULL,
    idIngredient INT NOT NULL,
    quantiteRequise DECIMAL(16, 3) NOT NULL CHECK (quantiteRequise > 0),
    Foreign KEY (idPlat) REFERENCES Plats(id),
    Foreign KEY (idIngredient) REFERENCES Ingredients(id)
);
-- Materielles 
CREATE TABLE CategorieMaterielles(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE StatutMaterielles(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE Materielles(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    dateEntree Date NOT NULL,
    idCategorieMaterielles INT NOT NULL,
    idStatutMaterielles INT NOT NULL,
    Foreign KEY (idCategorieMaterielles) REFERENCES CategorieMaterielles(id),
    Foreign KEY (idStatutMaterielles) REFERENCES StatutMaterielles(id)
);
CREATE TABLE HistoriqueMaterielles(
    id SERIAL PRIMARY KEY,
    idMaterielles INT NOT NULL,
    dateEntree Date NOT NULL,
    prixAchat DECIMAL(16, 3) NOT NULL CHECK (prixAchat >= 0),
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite > 0),
    Foreign KEY (idMaterielles) REFERENCES Materielles(id)
);
CREATE TABLE TypeMvtMaterielles(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE InventairesMaterielles(
    id SERIAL PRIMARY KEY,
    idMaterielles INT NOT NULL,
    dateInventaire Date NOT NULL,
    quantite DECIMAL(16, 3) NOT NULL CHECK (quantite > 0),
    typeMvtMaterielles INT NOT NULL,
    Foreign KEY (idMaterielles) REFERENCES Materielles(id),
    Foreign KEY (typeMvtMaterielles) REFERENCES TypeMvtMaterielles(id)
);
CREATE TABLE EtatStockMaterielles(
    id SERIAL PRIMARY KEY,
    idMaterielles INT NOT NULL,
    dateEtatStock Date NOT NULL,
    quantite DECIMAL(16, 3) NOT NULL CHECK(quantite > 0),
    Foreign KEY (idMaterielles) REFERENCES Materielles(id)
);
-- Personnels 
CREATE TABLE RolePersonnels(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE Personnels(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    idRolePersonnels INT NOT NULL,
    dateEmbauche Date NOT NULL,
    Foreign KEY (idRolePersonnels) REFERENCES RolePersonnels(id)
);
CREATE TABLE FichePaie(
    id SERIAL PRIMARY KEY,
    idPersonnels INT NOT NULL,
    datePaie Date NOT NULL,
    salaire DECIMAL(16, 3) NOT NULL CHECK (salaire >= 0),
    montantTotal DECIMAL(16, 3) NOT NULL CHECK(montantTotal >= 0),
    --Denormaliser
    Foreign KEY (idPersonnels) REFERENCES Personnels(id)
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
CREATE TABLE TypeClient(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE Clients(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    idTypeClient INT NOT NULL,
    Foreign KEY (idTypeClient) REFERENCES TypeClient(id)
);
-- Commandes et zones Livraisons et factures 
CREATE TABLE ZonesLivraison(
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    min DECIMAL(16, 3) NOT NULL CHECK(min >= 0),
    max DECIMAL(16, 3) NOT NULL CHECK(max >= min),
    prix DECIMAL(16, 3) NOT NULL CHECK(prix >= 0)
);
CREATE TABLE Commandes(
    id SERIAL PRIMARY KEY,
    idClient INT NOT NULL,
    dateCommande DATE NOT NULL,
    idZoneLivraison INT NOT NULL,
    montantTotal DECIMAL(16, 3) CHEcK (montantTotal > 0) NOT NULL,
    FOREIGN KEY(idClient) REFERENCES Clients(id),
    FOREIGN KEY(idZoneLivraison) REFERENCES ZonesLivraison(id)
);
CREATE TABLE DetailsCommandes(
    id SERIAL PRIMARY KEY,
    idCommande INT NOT NULL,
    idPlat INT NOT NULL,
    quantite DECIMAL(16, 3) CHECK (quantite > 0) NOT NULL,
    prixUnitaire DECIMAL(16, 3) CHECK (prixUnitaire >= 0) NOT NULL,
    montant DECIMAL(16, 3) CHECK (montant > 0) NOT NULL,
    FOREIGN KEY(idCommande) REFERENCES Commandes(id),
    FOREIGN KEY(idPlat) REFERENCES Plats(id)
);
CREATE TABLE FacturesCommandes(
    id SERIAL PRIMARY KEY,
    idCommande INT NOT NULL,
    dateFacture DATE NOT NULL,
    montantTotal DECIMAL(16, 3) CHECK (montantTotal > 0) NOT NULL,
    FOREIGN KEY(idCommande) REFERENCES Commandes(id)
);
CREATE TABLE TypeMouvementCaisse (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE MouvementCaisse (
    id SERIAL PRIMARY KEY,
    dateMouvement DATE NOT NULL,
    montant DECIMAL(16, 3) CHECK (montant > 0) NOT NULL,
    typeMouvement INT NOT NULL,
    FOREIGN KEY(typeMouvement) REFERENCES TypeMouvementCaisse(id)
);