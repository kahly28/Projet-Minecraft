package minecraft.mondeCubique.ExpertCase;

import minecraft.mondeCubique.Case;
import minecraft.lesBlocs.Air;

public class AirCaseExpert extends ExpertCase{
    public AirCaseExpert(ExpertCase suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreer(String type) {
        return type.contains("A");
    }

    @Override
    public Case creerCase(String type){
        return new Case(new Air());
    }

}
