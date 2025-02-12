Implémentation des mécaniques de Minecraft

L’objectif est de modéliser un monde cubique en 2D, où un joueur, appelé Steve, peut :
- Se déplacer en respectant des contraintes de terrain et de gravité.
- Miner des blocs en fonction des outils utilisés.
- Ramasser et stocker des objets dans un inventaire.
- Fabriquer de nouveaux objets via un système de recettes (craft).

Aucune interface graphique n’a été développée, l’interaction se fait via la console et des tests unitaires JUnit.

Fonctionnalités principales

Déplacement :
Steve peut se déplacer dans 8 directions (haut, bas, gauche, droite, diagonales).
Il ne peut avancer que dans les blocs fluides (air, eau) et doit respecter les lois de la gravité.
Si un bloc solide bloque sa tête ou ses pieds, le déplacement est interdit.

Minage :
Steve peut miner des blocs en fonction de l’outil qu’il utilise :
À mains nues : certains blocs comme le bois peuvent être récoltés.
Avec une pioche : d'autres blocs comme la pierre peuvent être extraits.
L’algorithme repose sur une chaîne de responsabilité pour garantir une extensibilité facile.

Inventaire & Ramassage :
Steve peut ramasser des objets posés au sol.
Les objets sont stockés dans un inventaire, qui peut être utilisé lors du crafting.

Fabrication (Crafting) :
Steve peut fabriquer de nouveaux objets à partir d’ingrédients, selon des recettes définies.
Trois types de recettes sont gérées :
2×2 sans table de craft.
3×3 avec une table de craft.
2×1 dans un four.

Exemples de crafts :
- Planches à partir de bois.
- Pioche en bois à partir de bâtons et de planches.
- Torches à partir de charbon et de bâtons.
- Organisation du projet

Le projet est organisé selon une architecture orientée objet, permettant d’ajouter facilement de nouveaux blocs, objets et recettes.

src/ – Contient le code source.
tests/ – Tests unitaires JUnit.
data/ – Fichiers CSV définissant le monde initial.

Tests & Validation

Des tests unitaires JUnit valident les règles du jeu. Un test d'intégration simule une session complète où Steve collecte des ressources et fabrique une pioche en pierre.
