package minecraft.expertFabrication;

import minecraft.Objet;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import minecraft.lesBlocs.Planche;
import minecraft.lesItems.Baton;

import java.util.ArrayList;

public class ExpertFabricationBatonPlanche extends ExpertFabrication{
    public ExpertFabricationBatonPlanche(ExpertFabrication suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerObjet(Objet[][] grilleObjets, Personnage perso) {
        int nbPlanche=0;
        boolean verticale=false;
        if(perso.possede(Planche.class,2)) {
            if (verifierTailleMatrice2x2(grilleObjets) || verifierTailleMatrice3x3(grilleObjets)) {
                for (int i = 0; i < grilleObjets.length; i++) {
                    for (int j = 0; j < grilleObjets[i].length; j++) {
                        if(grilleObjets[i][j]instanceof Planche){
                            nbPlanche++;
                            if (i+1<grilleObjets.length){
                                if(grilleObjets[i+1][j]instanceof Planche) {
                                    verticale =true;
                                }
                            }
                        }

                    }
                }
                return verticale&&nbPlanche==2;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Objet> creerObjet(Personnage perso) throws PersonnageException {
        perso.supprimerObjetInventaire(perso.getInventaire(),Planche.class,2);
        ArrayList<Objet>lesBatons=new ArrayList<Objet>();
        for (int i = 0; i < 4; i++) {
            lesBatons.add(new Baton());
        }
        return lesBatons;
    }
}
