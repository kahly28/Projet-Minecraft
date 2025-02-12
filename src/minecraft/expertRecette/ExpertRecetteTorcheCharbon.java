package minecraft.expertRecette;

import minecraft.Objet;
import minecraft.lesItems.Baton;
import minecraft.lesItems.Charbon;
import minecraft.lesItems.Torche;

public class ExpertRecetteTorcheCharbon extends ExpertRecette{
    public ExpertRecetteTorcheCharbon(ExpertRecette suivant) {
        super(suivant);
    }

    @Override
    public boolean peutCreerRecette(int dim, Class<? extends Objet>  objetVoulu, Class<? extends Objet>  materiau) {
        if(objetVoulu == Torche.class && dim>1&&materiau == Charbon.class){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Objet[][] creerRecette(int dim) {
        Objet[][]Recette=new Objet[dim][dim];
        Recette[0][1]=new Charbon();
        Recette[1][1]=new Baton();
        return Recette;
    }
}
