package minecraft.Minage;

import minecraft.mondeCubique.Case;
import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Bois;
import minecraft.mondeCubique.CaseException;

public class MinageMainBois extends MiningHandler
{

    public MinageMainBois(MiningHandler suivant) {
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
        if(obj==null&&maCase.getBloc() instanceof Bois)
        {
            return true;
        }
        return false;
    }
}
