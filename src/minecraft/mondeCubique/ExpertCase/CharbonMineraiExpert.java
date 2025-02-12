package minecraft.mondeCubique.ExpertCase;

import minecraft.lesBlocs.MineraiCharbon;
import minecraft.mondeCubique.Case;

public class CharbonMineraiExpert extends ExpertCase{
    public CharbonMineraiExpert(ExpertCase suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreer(String type) {
        return type.contains("CM");
    }

    @Override
    public Case creerCase(String type) throws Exception {
        return new Case(new MineraiCharbon());
    }
}
