package minecraft.mondeCubique.ExpertCase;

import minecraft.mondeCubique.Case;

public abstract class ExpertCase {
    private ExpertCase suivant =null;

    public ExpertCase(ExpertCase suivant){
        this.suivant=suivant;
    }

    public Case traiter(String type) throws Exception {
        if(peutCreer(type)){
            return creerCase(type);
        }else if(aUnSuivant()){
            return getSuivant().traiter(type);
        }else {
            throw new ExpertCaseException("Il n'y a pas de parser adequate pour cette ligne");
        }
    }

    private ExpertCase getSuivant() {
        return suivant;
    }

    private boolean aUnSuivant() {
        return suivant != null;
    }

    public abstract boolean peutCreer(String type);
    public abstract Case creerCase(String type)throws Exception;


}
