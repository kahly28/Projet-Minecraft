package minecraft.Minage;

import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;
import minecraft.Objet;

public abstract class MiningHandler {
    private MiningHandler suivant = null;
    public MiningHandler(MiningHandler suivant)
    {
        this.suivant = suivant;
    }

    public Case traiter(Objet obj, Case maCase) throws CaseException {
        if (peutMiner(obj, maCase)) {
            return Miner(maCase);
        } else if (aUnSuivant()) {
            return getSuivant().traiter(obj, maCase);
        } else {
            return maCase;
        }
    }


    private MiningHandler getSuivant() {
        return suivant;
    }
    private boolean aUnSuivant() {
        return suivant != null;
    }

    public abstract Case Miner(Case maCase) throws CaseException;


    public abstract boolean peutMiner(Objet obj, Case maCase);
}
