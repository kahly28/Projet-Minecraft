package minecraft.expertRecette;

import minecraft.Objet;
import minecraft.lesBlocs.Pierre;
import minecraft.lesItems.Baton;
import minecraft.lesItems.Pioche;

public class ExpertRecettePiochePierre extends ExpertRecette{
    public ExpertRecettePiochePierre(ExpertRecette suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerRecette(int dim, Class<? extends Objet> objetVoulu,Class<? extends Objet> materiau) {
        if(objetVoulu == Pioche.class && dim>2&&materiau==Pierre.class){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Objet[][] creerRecette(int dim) {
        Objet[][]Recette=new Objet[dim][dim];
        Recette[0][0]=new Pierre();
        Recette[0][1]=new Pierre();
        Recette[0][2]=new Pierre();
        Recette[1][1]=new Baton();
        Recette[2][1]=new Baton();

        return Recette;
    }
}
