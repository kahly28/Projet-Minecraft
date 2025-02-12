package minecraft.mondeCubique.ExpertCase;

import minecraft.mondeCubique.Case;
import minecraft.lesBlocs.Pierre;

public class PierreCaseExpert extends ExpertCase{
    public PierreCaseExpert(ExpertCase suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreer(String type) {
        return type.contains("P");
    }

    @Override
    public Case creerCase(String type){
        return new Case(new Pierre());
    }
}
