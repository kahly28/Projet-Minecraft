package minecraft.mondeCubique.ExpertCase;

import minecraft.mondeCubique.Case;
import minecraft.lesBlocs.Terre;

public class TerreCaseExpert extends ExpertCase {
    public TerreCaseExpert(ExpertCase suivant){
        super(suivant);
    }
    @Override
    public boolean peutCreer(String type) {
        return type.contains("T");
    }

    @Override
    public Case creerCase(String type){
        return new Case(new Terre());
    }
}
