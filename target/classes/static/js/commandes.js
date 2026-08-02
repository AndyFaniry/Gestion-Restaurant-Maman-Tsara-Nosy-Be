document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('lignes-container');
    const btnAjouter = document.getElementById('btn-ajouter-plat');

    if (!container || !btnAjouter) return;

    // Compteur pour les index d'éléments : lignes[0], lignes[1], ...
    let lineIndex = container.children.length;

    // Écouteur sur le bouton d'ajout de ligne
    btnAjouter.addEventListener('click', () => {
        ajouterLignePlat();
    });

    // Fonction d'ajout d'une nouvelle ligne
    function ajouterLignePlat() {
        const row = document.createElement('div');
        row.className = 'row g-3 align-items-center mb-2 ligne-commande';
        
        // Copie des options du premier <select> disponible ou génération du template
        const selectPlatHTML = document.getElementById('plat-template-select').innerHTML;

        row.innerHTML = `
            <div class="col-md-6">
                <select name="lignes[${lineIndex}].idPlat" class="form-select" required>
                    ${selectPlatHTML}
                </select>
            </div>
            <div class="col-md-4">
                <input type="number" 
                       name="lignes[${lineIndex}].quantite" 
                       class="form-control" 
                       placeholder="Quantité" 
                       min="1" 
                       step="1" 
                       value="1" 
                       required>
            </div>
            <div class="col-md-2">
                <button type="button" class="btn btn-outline-danger btn-supprimer">
                    <i class="bi bi-trash"></i> Supprimer
                </button>
            </div>
        `;

        // Écouteur pour la suppression de la ligne
        row.querySelector('.btn-supprimer').addEventListener('click', () => {
            row.remove();
            reindexLignes();
        });

        container.appendChild(row);
        lineIndex++;
    }

    // Ré-indexation obligatoire des champs pour Spring Binding (lignes[0], lignes[1], ...)
    function reindexLignes() {
        const rows = container.querySelectorAll('.ligne-commande');
        rows.forEach((row, index) => {
            const select = row.querySelector('select');
            const input = row.querySelector('input');

            if (select) select.name = `lignes[${index}].idPlat`;
            if (input) input.name = `lignes[${index}].quantite`;
        });
        lineIndex = rows.length;
    }
});