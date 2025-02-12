package minecraft.expertRecette;

import minecraft.Objet;

/**
 * Classe abstraite etendue par les différents expert pour creer de recette pour farbiquer des objets
 */
public abstract class ExpertRecette {

    private ExpertRecette suivant=null; // pointeur sur l'expert suivant init a null

    /**
     * @param suivant Modifie le pointeur sur l'expert suivant en lui affectant une valeur
     */
    public ExpertRecette(ExpertRecette suivant){
        this.suivant=suivant;
    }

    /**
     * @param dim Dimension de la grille dans laquelle il y aura la recette
     * @param objetVoulu Objet que l'on veut fabriquer
     * @param materiau Materiau utilisé pour fabriquer l'objetVoulu
     * @return  Retourne une grille Objet qui contient le placement des objets (mateiaux) pour fabriquer objetVoulu
     * @throws ExpertRecetteException
     * si on peut creer la recette alors  on le fait sinon on passe à l'expert suivant et si il n'y en pas on lance
     * une exception
     */
    public Objet[][] traiter(int dim,Class<? extends Objet> objetVoulu,Class<? extends Objet> materiau) throws ExpertRecetteException {
        if(peutCreerRecette(dim,objetVoulu,materiau)){
            return creerRecette(dim);
        } else if (aUnSuivant()) {
            return getSuivant().traiter(dim,objetVoulu,materiau);
            
        }else {
            throw new ExpertRecetteException("pas de recette pour fabriquer cela ou dimension invalide");
        }
    }

    /**
     * @param dim
     * @param objetVoulu
     * @param materiau
     * @return
     * verifie si pour la dimension donnée l'objet voulu et le materiau la recette correspond sinon retourne faux
     */
    public abstract boolean peutCreerRecette(int dim,Class<? extends Objet>  objetVoulu,Class<? extends Objet>  materiau);

    /**
     * @param dim
     * @return
     * methode abstraite implémenté par les autres classes qui retourne une grille d'objet avec le pattern
     * des objets pour fabriquer objetVoulu
     */
    public abstract Objet[][] creerRecette(int dim);

    /**
     * @return
     * si il y a un expert suivant cela retourne vrai sinon faux
     */
    private boolean aUnSuivant(){return suivant!=null;}

    /**
     * @return
     * permet d'obtenir l'expert suivant
     */
    public ExpertRecette getSuivant() {
        return suivant;
    }
}
