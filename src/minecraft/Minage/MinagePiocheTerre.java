package minecraft.Minage;

import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Terre;
import minecraft.lesItems.Pioche;
import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;

public class MinagePiocheTerre extends MiningHandler {
    public MinagePiocheTerre(MiningHandler suivant) {
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
        if(obj instanceof Pioche && maCase.getBloc() instanceof Terre)
        {
            return true;
        }
        return false;
    }
}
