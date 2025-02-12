package minecraft.Minage;

import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Leaf;
import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;

public class MinageLeaf extends MiningHandler{

    public MinageLeaf(MiningHandler suivant) {
        super(suivant);
    }

    @Override
    public Case Miner(Case maCase) throws CaseException {
        Case c = new Case(new Air());
        return c;
    }

    @Override
    public boolean peutMiner(Objet obj, Case maCase) {
        if(maCase.getBloc() instanceof Leaf)
        {
            return true;
        }
        return false;
    }
}
