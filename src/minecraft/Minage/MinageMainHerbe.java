package minecraft.Minage;

import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Herbe;
import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;

public class MinageMainHerbe extends MiningHandler{
    public MinageMainHerbe(MiningHandler suivant) {
        super(suivant);
    }

    @Override
    public Case Miner(Case maCase) throws CaseException {
        Case c = new Case(new Air());
        c.ajouterObjet(maCase.getBloc());
        return c;
    }

    @Override
    public boolean peutMiner(Objet obj, Case maCase) {
        if(maCase.getBloc() instanceof Herbe)
        {
            return true;
        }
        return false;
    }
}
