package minecraft.expertFabrication;

import minecraft.Objet;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import minecraft.lesItems.Baton;
import minecraft.lesItems.Charbon;
import minecraft.lesItems.Torche;

import java.util.ArrayList;

public class ExpertFabricationTorche extends ExpertFabrication{
    public ExpertFabricationTorche(ExpertFabrication suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerObjet(Objet[][] grilleObjets, Personnage perso) {
        int nbCharbon=0;
        boolean verticale=false;
        if(perso.possede(Baton.class,1)&&perso.possede(Charbon.class,1)) {
            if (verifierTailleMatrice2x2(grilleObjets) || verifierTailleMatrice3x3(grilleObjets)) {
                for (int i = 0; i < grilleObjets.length; i++) {
                    for (int j = 0; j < grilleObjets[i].length; j++) {
                        if(grilleObjets[i][j]instanceof Charbon){
                            nbCharbon++;
                            if (i+1<grilleObjets.length){
                                if(grilleObjets[i+1][j]instanceof Baton) {
                                    verticale =true;
                                }
                            }
                        }

                    }
                }
                return verticale&&nbCharbon==1;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Objet> creerObjet(Personnage perso) throws PersonnageException {
        perso.supprimerObjetInventaire(perso.getInventaire(),Baton.class,1);
        perso.supprimerObjetInventaire(perso.getInventaire(), Charbon.class,1);
        ArrayList<Objet>lesTorches=new ArrayList<Objet>();
        for (int i = 0; i < 4; i++) {
            lesTorches.add(new Torche());
        }
        return lesTorches;
    }
}
