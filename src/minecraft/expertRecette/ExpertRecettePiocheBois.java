package minecraft.expertRecette;

import minecraft.Objet;
import minecraft.lesBlocs.Bois;
import minecraft.lesItems.Baton;
import minecraft.lesItems.Pioche;


public class ExpertRecettePiocheBois extends ExpertRecette{
    public ExpertRecettePiocheBois(ExpertRecette suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerRecette(int dim, Class<? extends Objet>  objetVoulu, Class<? extends Objet> materiau) {
        if(objetVoulu == Pioche.class && dim>2&&materiau == Bois.class){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Objet[][] creerRecette(int dim) {
        Objet[][]Recette=new Objet[dim][dim];
        Recette[0][0]=new Bois();
        Recette[0][1]=new Bois();
        Recette[0][2]=new Bois();
        Recette[1][1]=new Baton();
        Recette[2][1]=new Baton();

        return Recette;
    }
}
