package minecraft.expertRecette;

import minecraft.Objet;
import minecraft.lesBlocs.Planche;
import minecraft.lesItems.Baton;

public class ExpertRecetteBatonPlanche extends ExpertRecette{
    public ExpertRecetteBatonPlanche(ExpertRecette suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerRecette(int dim, Class<? extends Objet>  objetVoulu, Class<? extends Objet>  materiau) {
        if(objetVoulu ==Baton.class && dim>1&&materiau == Planche.class){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Objet[][] creerRecette(int dim) {
        Objet[][]Recette=new Objet[dim][dim];
        Recette[0][1]=new Planche();
        Recette[1][1]=new Planche();
        return Recette;
    }
}
