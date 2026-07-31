```Shell
 andrianandrainy@andrianandrainy-HP-EliteBook-830-G6:~/Documents/projet-vacance/Gestion-Restaurant-Maman-Tsara-Nosy-Be$ tree
.
├── compile.sh
├── database
│   ├── add-column.sql
│   ├── database.sql
│   ├── data.sql
│   ├── delete.sql
│   ├── init.sql
│   ├── test.sql
│   └── vider.sql
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.MD
├── source.sh
├── source.txt
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── gestion
│   │   │           └── restaurant
│   │   │               ├── controller
│   │   │               │   ├── caisse
│   │   │               │   │   └── CaisseController.java
│   │   │               │   ├── client
│   │   │               │   │   └── ClientsController.java
│   │   │               │   ├── commandes
│   │   │               │   │   └── CommandesController.java
│   │   │               │   ├── dashboard
│   │   │               │   │   └── DashboardController.java
│   │   │               │   ├── fournisseurs
│   │   │               │   │   └── FournisseursController.java
│   │   │               │   ├── ingredients
│   │   │               │   │   ├── IngredientsController.java
│   │   │               │   │   └── StockController.java
│   │   │               │   ├── livraisons
│   │   │               │   ├── materielles
│   │   │               │   ├── personnels
│   │   │               │   │   └── PersonnelsController.java
│   │   │               │   └── plats
│   │   │               │       └── PlatsController.java
│   │   │               ├── dto
│   │   │               ├── entity
│   │   │               │   ├── caisse
│   │   │               │   │   ├── MouvementCaisse.java
│   │   │               │   │   └── TypeMouvementCaisse.java
│   │   │               │   ├── clients
│   │   │               │   │   ├── Clients.java
│   │   │               │   │   └── TypeClient.java
│   │   │               │   ├── commandes
│   │   │               │   │   ├── Commandes.java
│   │   │               │   │   ├── DetailsCommandes.java
│   │   │               │   │   └── FacturesCommandes.java
│   │   │               │   ├── fournisseurs
│   │   │               │   │   ├── Fournisseurs.java
│   │   │               │   │   └── TypeFournisseurs.java
│   │   │               │   ├── ingredients
│   │   │               │   │   ├── CategorieIngredients.java
│   │   │               │   │   ├── EtatStockIngredient.java
│   │   │               │   │   ├── HistoriqueIngredients.java
│   │   │               │   │   ├── Ingredients.java
│   │   │               │   │   ├── InventaireIngredient.java
│   │   │               │   │   ├── StatutIngredient.java
│   │   │               │   │   ├── TypeMvtIngredient.java
│   │   │               │   │   └── Unite.java
│   │   │               │   ├── livraisons
│   │   │               │   │   └── ZonesLivraison.java
│   │   │               │   ├── materielles
│   │   │               │   │   ├── CategorieMaterielles.java
│   │   │               │   │   ├── Materielles.java
│   │   │               │   │   └── StatutMaterielles.java
│   │   │               │   ├── personnels
│   │   │               │   │   ├── FichePaie.java
│   │   │               │   │   ├── Personnels.java
│   │   │               │   │   └── RolePersonnels.java
│   │   │               │   └── plats
│   │   │               │       ├── CategoriePlats.java
│   │   │               │       ├── Plats.java
│   │   │               │       └── RecettePlats.java
│   │   │               ├── repository
│   │   │               │   ├── caisse
│   │   │               │   │   ├── MouvementCaisseRepository.java
│   │   │               │   │   └── TypeMouvementCaisseRepository.java
│   │   │               │   ├── clients
│   │   │               │   │   ├── ClientsRepository.java
│   │   │               │   │   └── TypeClientRepository.java
│   │   │               │   ├── commandes
│   │   │               │   │   ├── CommandesRepository.java
│   │   │               │   │   ├── DetailsCommandesRepository.java
│   │   │               │   │   └── FacturesCommandesRepository.java
│   │   │               │   ├── fournisseur
│   │   │               │   │   ├── FournisseursRepository.java
│   │   │               │   │   └── TypeFournisseursRepository.java
│   │   │               │   ├── ingredients
│   │   │               │   │   ├── CategorieIngredientsRepository.java
│   │   │               │   │   ├── EtatStockIngredientRepository.java
│   │   │               │   │   ├── HistoriqueIngredientsRepository.java
│   │   │               │   │   ├── IngredientsRepository.java
│   │   │               │   │   ├── InventaireIngredientRepository.java
│   │   │               │   │   ├── StatutIngredientRepository.java
│   │   │               │   │   ├── TypeMvtIngredientRepository.java
│   │   │               │   │   └── UniteRepository.java
│   │   │               │   ├── livraisons
│   │   │               │   │   └── ZonesLivraisonRepository.java
│   │   │               │   ├── materielles
│   │   │               │   │   ├── CategorieMateriellesRepository.java
│   │   │               │   │   ├── MateriellesRepository.java
│   │   │               │   │   └── StatutMateriellesRepository.java
│   │   │               │   ├── personnels
│   │   │               │   │   ├── FichePaieRepository.java
│   │   │               │   │   ├── PersonnelsRepository.java
│   │   │               │   │   └── RolePersonnelsRepository.java
│   │   │               │   └── plats
│   │   │               │       ├── CategoriePlatsRepository.java
│   │   │               │       ├── PlatsRepository.java
│   │   │               │       └── RecettePlatsRepository.java
│   │   │               ├── RestaurantApplication.java
│   │   │               ├── service
│   │   │               └── specification
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       │   ├── css
│   │       │   │   └── style.css
│   │       │   ├── Design-template
│   │       │   │   ├── Design
│   │       │   │   │   ├── dashboard.html
│   │       │   │   │   ├── form.html
│   │       │   │   │   ├── list.html
│   │       │   │   │   ├── login.html
│   │       │   │   │   ├── script.js
│   │       │   │   │   ├── style.css
│   │       │   │   │   └── style.scss
│   │       │   │   └── __MACOSX
│   │       │   │       └── Design
│   │       │   ├── images
│   │       │   ├── js
│   │       │   │   └── script.js
│   │       │   └── scss
│   │       │       └── style.scss
│   │       └── templates
│   │           ├── caisse
│   │           │   ├── form.html
│   │           │   └── list.html
│   │           ├── clients
│   │           │   ├── form.html
│   │           │   └── list.html
│   │           ├── commandes
│   │           │   ├── form.html
│   │           │   └── list.html
│   │           ├── dashboard.html
│   │           ├── Design-template
│   │           │   ├── Design
│   │           │   │   ├── dashboard.html
│   │           │   │   ├── form.html
│   │           │   │   ├── list.html
│   │           │   │   ├── login.html
│   │           │   │   ├── script.js
│   │           │   │   ├── style.css
│   │           │   │   └── style.scss
│   │           │   └── __MACOSX
│   │           │       └── Design
│   │           ├── fournisseurs
│   │           │   ├── form.html
│   │           │   └── list.html
│   │           ├── fragments
│   │           │   └── layout.html
│   │           ├── ingredients
│   │           │   ├── form.html
│   │           │   └── list.html
│   │           ├── livraisons
│   │           ├── personnels
│   │           │   ├── form.html
│   │           │   └── list.html
│   │           ├── plats
│   │           │   ├── form.html
│   │           │   └── list.html
│   │           └── stocks
│   │               ├── form.html
│   │               └── list.html
│   └── test
│       └── java
│           └── com
│               └── gestion
│                   └── restaurant
│                       └── RestaurantApplicationTests.java
└── target
    ├── classes
    │   ├── application.properties
    │   ├── com
    │   │   └── gestion
    │   │       └── restaurant
    │   │           ├── controller
    │   │           │   ├── caisse
    │   │           │   │   └── CaisseController.class
    │   │           │   ├── client
    │   │           │   │   └── ClientsController.class
    │   │           │   ├── commandes
    │   │           │   │   └── CommandesController.class
    │   │           │   ├── dashboard
    │   │           │   │   └── DashboardController.class
    │   │           │   ├── fournisseurs
    │   │           │   │   └── FournisseursController.class
    │   │           │   ├── ingredients
    │   │           │   │   ├── IngredientsController.class
    │   │           │   │   └── StockController.class
    │   │           │   ├── livraisons
    │   │           │   ├── materielles
    │   │           │   ├── personnels
    │   │           │   │   └── PersonnelsController.class
    │   │           │   └── plats
    │   │           │       └── PlatsController.class
    │   │           ├── dto
    │   │           ├── entity
    │   │           │   ├── caisse
    │   │           │   │   ├── MouvementCaisse.class
    │   │           │   │   └── TypeMouvementCaisse.class
    │   │           │   ├── clients
    │   │           │   │   ├── Clients.class
    │   │           │   │   └── TypeClient.class
    │   │           │   ├── commandes
    │   │           │   │   ├── Commandes.class
    │   │           │   │   ├── DetailsCommandes.class
    │   │           │   │   └── FacturesCommandes.class
    │   │           │   ├── fournisseurs
    │   │           │   │   ├── Fournisseurs.class
    │   │           │   │   └── TypeFournisseurs.class
    │   │           │   ├── ingredients
    │   │           │   │   ├── CategorieIngredients.class
    │   │           │   │   ├── EtatStockIngredient.class
    │   │           │   │   ├── HistoriqueIngredients.class
    │   │           │   │   ├── Ingredients.class
    │   │           │   │   ├── InventaireIngredient.class
    │   │           │   │   ├── StatutIngredient.class
    │   │           │   │   ├── TypeMvtIngredient.class
    │   │           │   │   └── Unite.class
    │   │           │   ├── livraisons
    │   │           │   │   └── ZonesLivraison.class
    │   │           │   ├── materielles
    │   │           │   │   ├── CategorieMaterielles.class
    │   │           │   │   ├── Materielles.class
    │   │           │   │   └── StatutMaterielles.class
    │   │           │   ├── personnels
    │   │           │   │   ├── FichePaie.class
    │   │           │   │   ├── Personnels.class
    │   │           │   │   └── RolePersonnels.class
    │   │           │   └── plats
    │   │           │       ├── CategoriePlats.class
    │   │           │       ├── Plats.class
    │   │           │       └── RecettePlats.class
    │   │           ├── repository
    │   │           │   ├── caisse
    │   │           │   │   ├── MouvementCaisseRepository.class
    │   │           │   │   └── TypeMouvementCaisseRepository.class
    │   │           │   ├── clients
    │   │           │   │   ├── ClientsRepository.class
    │   │           │   │   └── TypeClientRepository.class
    │   │           │   ├── commandes
    │   │           │   │   ├── CommandesRepository.class
    │   │           │   │   ├── DetailsCommandesRepository.class
    │   │           │   │   └── FacturesCommandesRepository.class
    │   │           │   ├── fournisseur
    │   │           │   │   ├── FournisseursRepository.class
    │   │           │   │   └── TypeFournisseursRepository.class
    │   │           │   ├── ingredients
    │   │           │   │   ├── CategorieIngredientsRepository.class
    │   │           │   │   ├── EtatStockIngredientRepository.class
    │   │           │   │   ├── HistoriqueIngredientsRepository.class
    │   │           │   │   ├── IngredientsRepository.class
    │   │           │   │   ├── InventaireIngredientRepository.class
    │   │           │   │   ├── StatutIngredientRepository.class
    │   │           │   │   ├── TypeMvtIngredientRepository.class
    │   │           │   │   └── UniteRepository.class
    │   │           │   ├── livraisons
    │   │           │   │   └── ZonesLivraisonRepository.class
    │   │           │   ├── materielles
    │   │           │   │   ├── CategorieMateriellesRepository.class
    │   │           │   │   ├── MateriellesRepository.class
    │   │           │   │   └── StatutMateriellesRepository.class
    │   │           │   ├── personnels
    │   │           │   │   ├── FichePaieRepository.class
    │   │           │   │   ├── PersonnelsRepository.class
    │   │           │   │   └── RolePersonnelsRepository.class
    │   │           │   └── plats
    │   │           │       ├── CategoriePlatsRepository.class
    │   │           │       ├── PlatsRepository.class
    │   │           │       └── RecettePlatsRepository.class
    │   │           ├── RestaurantApplication.class
    │   │           ├── service
    │   │           └── specification
    │   ├── static
    │   │   ├── css
    │   │   │   └── style.css
    │   │   ├── Design-template
    │   │   │   └── Design
    │   │   │       ├── dashboard.html
    │   │   │       ├── form.html
    │   │   │       ├── list.html
    │   │   │       ├── login.html
    │   │   │       ├── script.js
    │   │   │       ├── style.css
    │   │   │       └── style.scss
    │   │   ├── js
    │   │   │   └── script.js
    │   │   └── scss
    │   │       └── style.scss
    │   └── templates
    │       ├── caisse
    │       │   ├── form.html
    │       │   └── list.html
    │       ├── clients
    │       │   ├── form.html
    │       │   └── list.html
    │       ├── commandes
    │       │   ├── form.html
    │       │   └── list.html
    │       ├── dashboard.html
    │       ├── Design-template
    │       │   └── Design
    │       │       ├── dashboard.html
    │       │       ├── form.html
    │       │       ├── list.html
    │       │       ├── login.html
    │       │       ├── script.js
    │       │       ├── style.css
    │       │       └── style.scss
    │       ├── fournisseurs
    │       │   ├── form.html
    │       │   └── list.html
    │       ├── fragments
    │       │   └── layout.html
    │       ├── ingredients
    │       │   ├── form.html
    │       │   └── list.html
    │       ├── personnels
    │       │   ├── form.html
    │       │   └── list.html
    │       ├── plats
    │       │   ├── form.html
    │       │   └── list.html
    │       └── stocks
    │           ├── form.html
    │           └── list.html
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    ├── maven-status
    │   └── maven-compiler-plugin
    │       ├── compile
    │       │   └── default-compile
    │       │       ├── createdFiles.lst
    │       │       └── inputFiles.lst
    │       └── testCompile
    │           └── default-testCompile
    │               ├── createdFiles.lst
    │               └── inputFiles.lst
    └── test-classes
        └── com
            └── gestion
                └── restaurant
                    └── RestaurantApplicationTests.class

143 directories, 221 files
```
