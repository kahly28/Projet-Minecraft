package minecraft.expertFabrication;

import minecraft.Objet;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import minecraft.lesBlocs.Bois;
import minecraft.lesBlocs.Planche;

import java.util.ArrayList;

public class ExpertPlancheAvecBois extends ExpertFabrication{
    public ExpertPlancheAvecBois(ExpertFabrication suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerObjet(Objet[][] grilleObjets, Personnage perso)
    {
        int nbBois=0;
            if (perso.possede(Bois.class, 1)) {
                if (verifierTailleMatrice2x2(grilleObjets) || verifierTailleMatrice3x3(grilleObjets)) {
                    for (int i = 0; i < grilleObjets.length; i++) {
                        for (int j = 0; j < grilleObjets[i].length; j++) {
                            Objet caseCourante = grilleObjets[i][j];
                            if (caseCourante != null) {
                                if (caseCourante instanceof Bois) {
                                    nbBois++;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                    return nbBois == 1;
                } else {
                    return false;
                }
            } else {
                return false;
            }
    }

    @Override
    public ArrayList<Objet> creerObjet(Personnage perso) throws PersonnageException {
        perso.supprimerObjetInventaire(perso.getInventaire(),Bois.class,1);
        ArrayList<Objet>lesPlanches=new ArrayList<Objet>();
        for (int i = 0; i < 4; i++) {
            lesPlanches.add(new Planche());
        }
        return lesPlanches;
    }
}
