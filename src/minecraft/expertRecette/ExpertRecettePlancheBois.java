package minecraft.expertRecette;

import minecraft.Objet;
import minecraft.lesBlocs.Bois;
import minecraft.lesBlocs.Planche;

public class ExpertRecettePlancheBois extends ExpertRecette {

    public ExpertRecettePlancheBois(ExpertRecette suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerRecette(int dim, Class<? extends Objet>  objetVoulu, Class<? extends Objet>  materiau) {
        if(objetVoulu ==Planche.class && materiau == Bois.class && dim>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Objet[][] creerRecette(int dim) {
        Objet[][]Recette=new Objet[dim][dim];
        Recette[0][0]=new Bois();
        return Recette;
    }
}
