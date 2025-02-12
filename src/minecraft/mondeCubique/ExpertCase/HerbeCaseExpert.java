package minecraft.mondeCubique.ExpertCase;

import minecraft.mondeCubique.Case;
import minecraft.lesBlocs.Herbe;

public class HerbeCaseExpert extends ExpertCase {
    public HerbeCaseExpert(ExpertCase suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreer(String type) {
        return type.contains("H");
    }

    @Override
    public Case creerCase(String type){
        return new Case(new Herbe());
    }
}
