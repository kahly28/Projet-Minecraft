package minecraft.expertFabrication;

import minecraft.Objet;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import minecraft.lesBlocs.Planche;
import minecraft.lesItems.PiocheEnBois;

import java.util.ArrayList;

public class ExpertFabricationTableDeCraft extends ExpertFabrication{

    public ExpertFabricationTableDeCraft(ExpertFabrication suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerObjet(Objet[][] grilleObjets, Personnage perso) {

        if(perso.possede(Planche.class,4)){

            if(verifierTailleMatrice2x2(grilleObjets)){
                for (int i=0;i<grilleObjets.length;i++) {
                    for(int j=0;j<grilleObjets[0].length;j++) {
                        if (!(grilleObjets[i][j] instanceof Planche)) {
                            return false;
                        }
                    }
                }
                return true;
            } else if (verifierTailleMatrice3x3(grilleObjets)) {
                if((grilleObjets[0][0]==null)&&(grilleObjets[0][1]==null)&&(grilleObjets[0][2]==null)){

                    if(grilleObjets[1][0]instanceof Planche&&grilleObjets[1][1]instanceof Planche&&grilleObjets[1][2]==null){
                        if(grilleObjets[2][0]instanceof Planche&&grilleObjets[2][1]instanceof Planche&&grilleObjets[2][2]==null){
                            return true;
                        }
                    }

                    if(grilleObjets[1][1]instanceof Planche&&grilleObjets[1][2]instanceof Planche&&grilleObjets[1][0]==null){
                        if(grilleObjets[2][1]instanceof Planche&&grilleObjets[2][2]instanceof Planche&&grilleObjets[2][0]==null){
                            return true;
                        }
                    }

                    return false;

                } else if ((grilleObjets[2][0]==null)&&(grilleObjets[2][1]==null)&&(grilleObjets[2][2]==null)) {
                    if(grilleObjets[0][0]instanceof Planche&&grilleObjets[0][1]instanceof Planche&&grilleObjets[0][2]==null){
                        if(grilleObjets[1][0]instanceof Planche&&grilleObjets[1][1]instanceof Planche&&grilleObjets[1][2]==null){
                            return true;
                        }
                    }

                    if(grilleObjets[0][1]instanceof Planche&&grilleObjets[0][2]instanceof Planche&&grilleObjets[0][0]==null){
                        if(grilleObjets[1][1]instanceof Planche&&grilleObjets[1][2]instanceof Planche&&grilleObjets[1][0]==null){
                            return true;
                        }
                    }

                    return false;

                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<Objet> creerObjet(Personnage perso) throws PersonnageException {
        perso.supprimerObjetInventaire(perso.getInventaire(),Planche.class,4);
        ArrayList<Objet>tableDeCraft=new ArrayList<Objet>();
        tableDeCraft.add(new PiocheEnBois());
        return tableDeCraft;
    }
}
