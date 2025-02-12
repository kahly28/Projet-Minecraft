package minecraft.mondeCubique.ExpertCase;

import minecraft.mondeCubique.Case;
import minecraft.lesBlocs.Bois;

public class BoisCaseExpert extends ExpertCase{

    public BoisCaseExpert(ExpertCase suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreer(String type) {
        return type.contains("B");
    }

    @Override
    public Case creerCase(String type){
        return new Case(new Bois());
    }
}
