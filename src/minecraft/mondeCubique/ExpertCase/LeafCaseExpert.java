package minecraft.mondeCubique.ExpertCase;

import minecraft.mondeCubique.Case;
import minecraft.lesBlocs.Leaf;

public class LeafCaseExpert extends ExpertCase{

    public LeafCaseExpert(ExpertCase suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreer(String type) {
        return type.contains("L");
    }

    @Override
    public Case creerCase(String type){
        return new Case(new Leaf());
    }
}
