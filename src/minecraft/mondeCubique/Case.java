package minecraft.mondeCubique;

import minecraft.lesBlocs.Bloc;
import minecraft.Objet;

import java.util.ArrayList;
import java.util.Objects;

public class Case {
    private ArrayList<Objet> contenuBloc= new ArrayList<Objet>(); // les objets que peuvent contenir les blocs
    private Bloc bloc;// bloc contenu dans la case

    /**
     * @param bloc constructeur de case necessite un bloc
     */
    public Case(Bloc bloc){
        this.bloc=bloc;
    }
    public Bloc getBloc() {
        return bloc;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    /**
     * @return True si le bloc n'est pas solide donc peut contenir des objets sinon False
     */
    public boolean peutContenirObjet() {
        return !bloc.estSolide();
    }

    /**
     * @param objet Objet à ajouter dans le bloc
     * @throws CaseException si la case ne peut pas contenir d'objet donc que bloc n'est pas fluide
     */
    public void ajouterObjet(Objet objet) throws CaseException {
        if(peutContenirObjet())
            contenuBloc.add(objet);
        else
            throw new CaseException("la case ne contient pas un bloc fluide");
    }

    /**
     * @param itemType Type d'objet dont on veut vérifier la presence
     * @return True si la case (Bloc) contient l'objet en question
     * @throws CaseException si bloc solide
     */
    public boolean contient(Class<? extends Objet> itemType) throws CaseException {
        if(peutContenirObjet()) {
            for (Objet objet : contenuBloc) {
                if (itemType.isInstance(objet)) {
                    return true;
                }
            }
            return false;
        }
        else
            throw new CaseException("la case contient un bloc solide, donc ne peut contenir d'objet");
    }

    /**
     * Methode pour retirer un objet d'une case contenant un bloc fluide
     * @param objet Objet a retirer du bloc
     * @throws CaseException si vide ou bloc pas fluide
     */
    public void retirerObjet(Objet objet) throws CaseException {
        if(peutContenirObjet()) {
            if (contenuBloc.contains(objet)) {
                contenuBloc.remove(objet);
            } else {
                throw new CaseException("le bloc ne contient pas l'objet");
            }
        }else {
            throw new CaseException("la case contient un bloc solide, donc on ne peut en retirer un objet");
        }
    }

    public ArrayList<Objet> getContenuBloc() {
        return contenuBloc;
    }

    public boolean isEmptyBloc(){
        return contenuBloc.isEmpty();
    }

    /**
     * @return le nombre d'objet present dans la case contenant un bloc fluide
     * @throws CaseException si la case contient un bloc solide
     */
    public int nombreObjet() throws CaseException {
        if(peutContenirObjet())
            return contenuBloc.size();
        else
            throw new CaseException("le bloc contenu est un solide donc pas d'objet dedans");
    }

    @Override
    public String toString() {
        String formattedString = String.format("%15s", bloc);
        return  formattedString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return Objects.equals(contenuBloc, aCase.contenuBloc) && Objects.equals(bloc, aCase.bloc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contenuBloc, bloc);
    }
}
