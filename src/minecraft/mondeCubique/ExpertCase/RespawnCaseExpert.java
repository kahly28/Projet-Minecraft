package minecraft.mondeCubique.ExpertCase;

import minecraft.mondeCubique.Case;
import minecraft.lesBlocs.Respawn;

public class RespawnCaseExpert extends ExpertCase {
    private static boolean aRespawn =false;
    public RespawnCaseExpert(ExpertCase suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreer(String type) {
        return type.contains("R");
    }

    @Override
    public Case creerCase(String type){
        aRespawn=true;
        return new Case(Respawn.getInstance());
    }
}
