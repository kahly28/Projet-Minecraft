package minecraft.Minage;

import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;
import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Terre;

public class MinageMainTerre extends MiningHandler{

    public MinageMainTerre(MiningHandler suivant) {
        super(suivant);
    }

    @Override
    public Case Miner(Case maCase) throws CaseException
    {
        Case c = new Case(new Air());
        c.ajouterObjet(maCase.getBloc());
        return c;
    }

    @Override
    public boolean peutMiner(Objet obj, Case maCase) {
        if(obj==null&&maCase.getBloc() instanceof Terre)
        {
            return true;
        }
        return false;
    }
}
