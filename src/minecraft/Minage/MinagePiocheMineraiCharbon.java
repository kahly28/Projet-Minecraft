package minecraft.Minage;

import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.MineraiCharbon;
import minecraft.lesItems.Charbon;
import minecraft.lesItems.Pioche;
import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;

import java.util.Random;

public class MinagePiocheMineraiCharbon extends MiningHandler{
    public MinagePiocheMineraiCharbon(MiningHandler suivant) {
        super(suivant);
    }

    @Override
    public Case Miner(Case maCase) throws CaseException {
        Random random=new Random();
        int nombreCharbon=random.nextInt(4)+1;

        int i=1;
        Case c = new Case(new Air());
        do {
            c.ajouterObjet(new Charbon());
            i++;
        } while (i<=nombreCharbon);
        return c;
    }

    @Override
    public boolean peutMiner(Objet obj, Case maCase) {
        if(obj instanceof Pioche && maCase.getBloc() instanceof MineraiCharbon)
        {
            return true;
        }
        return false;
    }
}
