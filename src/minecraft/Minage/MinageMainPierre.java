package minecraft.Minage;

import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;
import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Pierre;

public class MinageMainPierre extends MiningHandler
{

    public MinageMainPierre(MiningHandler suivant) {
        super(suivant);
    }

    @Override
    public Case Miner(Case maCase) throws CaseException
    {
        Case c = new Case(new Air());
        return c;

    }

    @Override
    public boolean peutMiner(Objet obj, Case maCase) {
        if(obj==null && maCase.getBloc() instanceof Pierre)
        {
            return true;
        }
        return false;
    }
}
