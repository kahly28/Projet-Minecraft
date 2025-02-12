package minecraft.expertFabrication;

import minecraft.Objet;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import minecraft.lesBlocs.Bois;
import minecraft.lesItems.Baton;
import minecraft.lesItems.PiocheEnBois;

import java.util.ArrayList;

public class ExpertFabricationPiocheBois extends ExpertFabrication{

    public ExpertFabricationPiocheBois(ExpertFabrication suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerObjet(Objet[][] grilleObjets, Personnage perso)
    {
            if (perso.possede(Bois.class, 3) && perso.possede(Baton.class, 2)) {
                if (verifierTailleMatrice3x3(grilleObjets)) {
                    for (int i = 0; i < grilleObjets.length; i++)
                    {
                        if (!(grilleObjets[0][i] instanceof Bois)) {
                            return false;
                        }
                    }
                    if (!(grilleObjets[1][1] instanceof Baton)) {
                        return false;
                    }
                    if (!(grilleObjets[2][1] instanceof Baton)) {
                        return false;
                    }
                    if (grilleObjets[1][0] == null && grilleObjets[2][0] == null && grilleObjets[1][2] == null &&
                            grilleObjets[2][2] == null) {

                        return true;
                    }

                }
            }
            return false;
    }

    @Override
    public ArrayList<Objet> creerObjet(Personnage perso) throws PersonnageException {
        perso.supprimerObjetInventaire(perso.getInventaire(),Bois.class,3);
        perso.supprimerObjetInventaire(perso.getInventaire(),Baton.class,2);
        ArrayList<Objet>Pioche=new ArrayList<Objet>();
        Pioche.add(new PiocheEnBois());
        return Pioche;
    }
}
