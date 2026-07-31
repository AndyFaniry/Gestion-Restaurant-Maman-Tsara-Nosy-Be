Voici la marche à suivre complète en SQL pour ajouter la colonne `idUnite` comme clé étrangère dans la table `Ingredients`, la remplir pour vos données existantes, puis configurer la contrainte d'intégrité référantielle.

---

### Étape 1 : Ajouter la nouvelle colonne `idUnite`

On ajoute d'abord la colonne `idUnite` sans contrainte `NOT NULL` immédiate pour éviter tout blocage avec vos lignes d'ingrédients déjà existantes :

```sql
ALTER TABLE ingredients 
ADD COLUMN idUnite INT;

```

*(Si vos ID de la table `unite` sont de type `BIGINT`, utilisez `BIGINT` à la place d' `INT`)*.

---

### Étape 2 : Mettre à jour (`UPDATE`) les lignes existantes

Si vous avez déjà des ingrédients dans votre base, attribuez-leur un `idUnite` par défaut ou spécifique pour ne pas laisser de valeurs `NULL` indésirables :

```sql
-- Associer tous les ingrédients existants à l'unité avec l'id 1 (ex: 'Kilogramme' ou 'Pièce')
UPDATE ingredients 
SET idUnite = 1 
WHERE idUnite IS NULL;

-- Exemple pour cibler un ingrédient spécifique :
-- UPDATE ingredients SET idUnite = 2 WHERE id = 5;

```

---

### Étape 3 : Ajouter la clé étrangère (Foreign Key)

Une fois la colonne créée et éventuellement mise à jour, vous pouvez lier officiellement la table `ingredients` à la table `unite` :

```sql
ALTER TABLE ingredients 
ADD CONSTRAINT fk_ingredients_unite 
FOREIGN KEY (idUnite) 
REFERENCES unite(id)
ON DELETE RESTRICT -- Empêche de supprimer une unité si elle est utilisée par un ingrédient
ON UPDATE CASCADE;  -- Met à jour automatiquement la clé si l'ID dans la table unite change

```

---

### Étape 4 (Optionnelle) : Rendre la colonne obligatoire

Si chaque ingrédient **doit obligatoirement** posséder une unité dans votre système, vous pouvez ensuite ajouter la contrainte `NOT NULL` :

```sql
ALTER TABLE ingredients 
ALTER COLUMN idUnite SET NOT NULL;

```

*(Remarque : sur MySQL, la syntaxe pour cette étape est `ALTER TABLE ingredients MODIFY COLUMN idUnite INT NOT NULL;`)*.