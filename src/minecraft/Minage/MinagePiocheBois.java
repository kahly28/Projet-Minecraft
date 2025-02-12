package minecraft.Minage;

import minecraft.lesItems.Pioche;
import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;
import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Bois;

public class MinagePiocheBois extends MiningHandler{

    public MinagePiocheBois(MiningHandler suivant) {
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
        if(obj instanceof Pioche && maCase.getBloc() instanceof Bois)
        {
            return true;
        }
        return false;
    }
}
