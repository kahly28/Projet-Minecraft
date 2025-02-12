package minecraft.Minage;

import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.MineraiCharbon;
import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;

public class MinageMainMineraiCharbon extends MiningHandler{
    public MinageMainMineraiCharbon(MiningHandler suivant) {
        super(suivant);
    }

    @Override
    public Case Miner(Case maCase) throws CaseException {
        return new Case(new Air());
    }

    @Override
    public boolean peutMiner(Objet obj, Case maCase) {
        if(obj==null && maCase.getBloc() instanceof MineraiCharbon)
        {
            return true;
        }
        return false;
    }
}
