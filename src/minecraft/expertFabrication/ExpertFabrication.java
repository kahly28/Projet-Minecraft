package minecraft.expertFabrication;

import minecraft.Objet;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;

import java.util.ArrayList;

/**
 * Classe abstraite etendue par les différents experts pour la fabrication d'objet à partir de recette
 */
public abstract class ExpertFabrication {
    private ExpertFabrication suivant = null;//expert suivant a null par default

    /**
     * @param suivant Expert suivant pour la fabrication
     */
    public ExpertFabrication(ExpertFabrication suivant) {
        this.suivant = suivant;
    }

    /**
     * @param grilleObjets
     * @param perso
     * @return
     * @throws ExpertFabricationException
     * @throws PersonnageException
     * Permet ou non de creer un objet à partir d'une grille (recette) selon si le personnage a les ingrédients
     * en sa possession et si la grille correspond à une recette d'un expert de fabrication
     */
    public ArrayList<Objet> traiter(Objet[][]grilleObjets, Personnage perso) throws ExpertFabricationException, PersonnageException {
        if(peutCreerObjet(grilleObjets,perso)){
            return creerObjet(perso);
        }else if(aUnSuivant()){
            return getSuivant().traiter(grilleObjets,perso);
        }else {
            throw new ExpertFabricationException("pas de d'objet existant pour ce pattern ou mauvaise taille de grille");
        }
    }

    /**
     * @param grilleObjets
     * @param perso
     * @return
     * verifie si le personnage a les ingrédients et si la recette correspond à celle de l'expert
     */
    public abstract boolean peutCreerObjet(Objet[][] grilleObjets,Personnage perso);

    /**
     * @param perso
     * @return
     * @throws PersonnageException
     * retire les ingrédients de l'inventaire du personnage et lui ajoute l'item fabriqué
     */
    public abstract ArrayList<Objet> creerObjet(Personnage perso) throws PersonnageException;

    /**
     * @param grilleObjets
     * @return
     * verifie si la grille est bien homogene et 2x2
     */
    public boolean verifierTailleMatrice2x2(Objet[][] grilleObjets){
        int lignes = grilleObjets.length;
        int colonnes = grilleObjets[0].length;

        return lignes == 2 && colonnes == 2;
    }

    /**
     * @param grilleObjets
     * @return
     * verifie que la grille est homogène et 3x3
     */
    public boolean verifierTailleMatrice3x3(Objet[][] grilleObjets) {
        int lignes = grilleObjets.length;
        int colonnes = grilleObjets[0].length;

        if (lignes != 3 || colonnes != 3) {
            return false;
        }

        for (Objet[] objet : grilleObjets) {
            if (objet.length != 3) {
                return false;
            }
        }

        return true;
    }


    /**
     * @return L'expert suivant si il existe
     */
    private ExpertFabrication getSuivant() {
        return suivant;
    }

    /**
     * @return Vrai si suivant n'est pas null
     */
    private boolean aUnSuivant() {
        return suivant != null;
    }

}
