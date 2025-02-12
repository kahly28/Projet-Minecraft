package minecraft.Personnage;
import minecraft.expertFabrication.*;
import minecraft.Minage.*;
import minecraft.expertRecette.*;
import minecraft.lesBlocs.BlocException;
import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.CaseException;
import minecraft.mondeCubique.LeMonde;
import minecraft.lesBlocs.Bloc;
import minecraft.Minage.MiningHandler;
import minecraft.Objet;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Respawn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Personnage {
    private String nom; //nom du personnage
    private int x; //position horizontale
    private int y; //position verticale
    private LeMonde monde; //monde dans lequel se trouve le joueur
    private ArrayList<Objet> inventaire = new ArrayList<Objet>(); //inventaire du joueur avec des objets
    private Objet objetTenu; //objet que le personnage tient en main
    private ExpertFabrication expertFabrication; //expert qui se charge de fabriquer un objet à partir d'une grille
    private ExpertRecette expertRecette; //expert qui creer une grille avec le bon pattern pour l'objet souhaité
    private MiningHandler expertMinage; //expert qui traite le minage de bloc avec ce que l'on a en main


    public enum Direction { GAUCHE, DROITE, HAUT, BAS, HAUT_GAUCHE, HAUT_DROITE, BAS_GAUCHE, BAS_DROITE}

    /**
     * @param inventaire L'inventaire du joueur
     * @param itemType Le type d'objet que l'on veut supprimer de l'inventaire
     * @param i Le nombre d'occurence de l'objet que l'on veut supprimer
     * @throws PersonnageException si le personnage ne possede pas l'objet ou pas dans la quantité demandé
     */
    public void supprimerObjetInventaire(ArrayList<Objet> inventaire, Class<?extends Objet> itemType, int i) throws PersonnageException {
        Iterator<Objet> iterator = inventaire.iterator();
        int compteur = 0;
        if (this.possede(itemType,i)) {
            while (iterator.hasNext() && compteur < i) {
                Objet objet = iterator.next();

                if (itemType.isInstance(objet)) {
                    iterator.remove();
                    compteur++;
                }
            }
        }else {
            throw new PersonnageException("objet pas prèsent ou pas dans les proportion indiqué");
        }
    }


    /**
     * Constructeur de Personnage
     * @param Monde Monde dans lequel se trouve le personnage
     * @param nom Nom du joueur
     * @throws PersonnageException certaines methodes font appel à cette exception
     */
    public Personnage(LeMonde Monde, String nom) throws PersonnageException {
        setNom(nom);
        setMonde(Monde);
        Case[][] grid = Monde.getGrille();
        Point point = getRespawn(Monde);
        setX(point.x);
        setY(point.y);
        this.expertRecette=creerChaineExpertRecette();
        this.expertFabrication=creerChaineExpertFabricationVide();
        this.expertMinage = creerChaineMinageVide();
    }

    /**
     * @param x Position x à laquelle on souhaite placer le personnage (horizontal)
     * @throws PersonnageException Si les coordonnées ne sont pas valides
     */
    public void setX(int x) throws PersonnageException {
        if(x<0 || x>monde.getLargeur())
            throw new PersonnageException("les coordonnées en x sont hors du monde");
        else
            this.x = x;

    }

    /**
     * @param y Position y à laquelle on souhaite placer le personnage (verticale)
     * @throws PersonnageException Si les coordonnées ne sont pas valide
     */
    public void setY(int y) throws PersonnageException {
        if(y<0 || y>monde.getHauteur())
            throw new PersonnageException("les coordonnées en y sont hors du monde");
        else
            this.y = y;
    }

    /**
     * @param nom Nom que l'on souhaite donner au personnage
     * @throws PersonnageException Si le nom est vide ou null
     */
    private void setNom(String nom) throws PersonnageException
    {
        if(nom == null || nom.trim().equals(""))
        {
            throw new PersonnageException("Nom ne peut pas être null");
        }
        this.nom = nom;
    }

    /**
     * @param monde Setter du monde du personnage
     */
    private void setMonde(LeMonde monde)
    {
        this.monde = monde;
    }

    /**
     * @return monde
     */
    public LeMonde getMonde() {
        return monde;
    }

    /**
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param monde dans lequel se trouve le personnage
     * @return le point de Respawn du personnage
     */
    public Point getRespawn(LeMonde monde)
    {
        Case[][] grid = this.monde.getGrille();
        Point point = null;
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                // Vérification si la case est une instance de la classe recherchée
                if (grid[j][i].getBloc() instanceof Respawn) {
                   point = new Point(j,i);
                }
            }
        }
        return point;
    }

    /**
     * Retire l'objet de la main du personnage et le place dans son inventaire
     */
    public void seDessaisir()
    {
        ajoutInventaire(this.getObjetTenu());
        objetTenu = null;
    }

    /**
     * @return Le nombre d'element dans l'inventaire du personnage
     */
    public int getTailleInventaire()
    {
        return inventaire.size();
    }

    /**
     * @param objet Objet à ajouter dans l'inventaire du personnage
     */
    public void ajoutInventaire(Objet objet)
    {
        inventaire.add(objet);
    }

    /**
     * @param lesObjets Liste d'objets à ajouter dans l'inventaire
     */
    public void ajoutMultipleInventaire(ArrayList<Objet> lesObjets){
        inventaire.addAll(lesObjets);
    }

    public Objet getObjetTenu()
    {
        return this.objetTenu;
    }

    public ArrayList<Objet> getInventaire() {
        return inventaire;
    }

    /**
     * @param itemType Type d'objet dont on veut vérifier la présence
     * @param nombre Nombre d'élément que l'on veut verifier la presence
     * @return True si possède le nombre exact ou plus sinon False
     */
    public boolean possede(Class<? extends Objet> itemType,int nombre){
        int compte=0;
        for (Objet objet : inventaire) {
            if (itemType.isInstance(objet)) {
                compte++;
            }
        }
        return compte>=nombre;
    }

    /**
     * @param y
     * @param x
     * @throws PersonnageException
     * @throws BlocException
     * @throws CaseException
     * Cette methode permet au personnage de ramasser des objets dans les blocs qui sont dans son voisinage
     * de 1 bloc
     */
    public void ramasserObjet(int y,int x) throws PersonnageException, BlocException, CaseException {
        int distanceX= Math.abs(getX()-x);
        int distanceY= Math.abs((getY()-1)-y);
        if (((distanceX == 1 && distanceY == 0) || (distanceX == 0 && distanceY == 1) ||
                (distanceX == 1 && distanceY == 1) )) {
            if(!monde.getCase(y,x).getBloc().estSolide()) {
                if(!monde.getCase(y,x).isEmptyBloc()) {
                    inventaire.addAll(monde.getCase(y, x).getContenuBloc());
                }else{
                    throw new CaseException("le bloc est vide impossible de ramasser");
                }
            }else{
                throw new BlocException("impossible de ramasser dans un bloc solide");
            }
        }else{
            throw new PersonnageException("impossible de ramasser ce bloc n'est pas voisin");
        }
    }

    /**
     * @param object
     * @throws PersonnageException
     * Prend l'objet dans l'inventaire et le place dans la main du personnage
     */
    public void Tenir(Objet object) throws PersonnageException {
        if(possede(object.getClass(),1))
        {
            objetTenu = object;
            supprimerObjetInventaire(inventaire,object.getClass(),1);
        }
    }

    public int getX()
    {
        return this.x;
    }

    public int getY() {
        return y;
    }


    /**
     * Se deplacer à droite si cela est possible sinon
     * @throws PersonnageException
     */
    public void seDeplacerADroite() throws PersonnageException {
        int PersoX = this.getX();
        int PersoY = this.getY();
        int PersoX2 = this.getX();
        int PersoY2 = this.getY()-1;
        if(PersoX+1 <= monde.getGrille().length-1) //on peut remplacer par LeMonde.HAUTEUR
        {
            Bloc c = monde.getBlocCase( PersoY,PersoX + 1);
            Bloc c2 = monde.getBlocCase(PersoY2,PersoX+1);
            System.out.println(c);
            System.out.println(c2);
            if (c instanceof Air && c2 instanceof Air)
            {
                this.setX(this.getX()+1);
                this.setY(this.getY());;
                return;
            }
        }
        throw new PersonnageException("Deplacement à droite impossible");

    }

    /**
     * Se deplacer à gauche si cela est possible sinon
     * @throws PersonnageException
     */
    public void seDeplacerAGauche() throws PersonnageException {
        int PersoX = this.getX();
        int PersoY = this.getY();
        int PersoX2 = this.getX();
        int PersoY2 = this.getY()-1;


        if(PersoX-1 >= 0)
        {
            Bloc c = monde.getBlocCase(PersoY,PersoX-1);
            Bloc c2 = monde.getBlocCase(PersoY2-1,PersoX2-1);
            if(c instanceof Air && c2 instanceof Air)
            {

                this.setX(this.getX()-1);
                this.setY(this.getY());
                return;
            }
        }

        throw new PersonnageException("Deplacement à gauche impossible");
    }

    /**
     * Se deplacer en haut si cela est possible sinon
     * @throws PersonnageException
     */
    public void seDeplacerEnHaut() throws PersonnageException {
        int PersoX = this.getX();
        int PersoY = this.getY();
        int PersoX2 = this.getX();
        int PersoY2 = this.getY()-1;
        if(PersoY2-1 >= 0)
        {
            Bloc c = monde.getBlocCase(PersoY - 1,PersoX);
            Bloc c2 = monde.getBlocCase(PersoY - 2,PersoX);

            if (c instanceof Air && c2 instanceof Air)
            {
                this.setX(this.getX());
                this.setY(this.getY()-1);
                return;
            }
        }
        throw new PersonnageException("Deplacement en haut impossible");
    }

    /**
     * Se deplacer en bas si cela est possible sinon
     * @throws PersonnageException
     */
    public void seDeplacerEnBas() throws PersonnageException {
        int PersoX = this.getX();
        int PersoY = this.getY();
        if(PersoY+1 <= monde.getGrille()[0].length-1)
        {
            Bloc c = monde.getBlocCase(PersoY + 1,PersoX);
            if (c instanceof Air) {
                this.setX(this.getX());
                this.setY(this.getY()+1);
                return;
            }
        }
        throw new PersonnageException("Deplacement en bas impossible");
    }

    /**
     * Se deplacer en haut à gauche si cela est possible sinon
     * @throws PersonnageException
     */
    public void seDeplacerHautGauche() throws PersonnageException {
        int PersoX = this.getX();
        int PersoY = this.getY();
        int PersoX2 = this.getX();
        int PersoY2 = this.getY()-1;
        if(PersoX2-1 >= 0 && PersoY2-1 >= 0 )
        {
            Bloc c = monde.getBlocCase(PersoY - 1,PersoX - 1);
            Bloc c2 = monde.getBlocCase(PersoY2 - 1,PersoX2-1);

            if (c instanceof Air && c2 instanceof Air) {
                this.setX(this.getX()-1);
                this.setY(this.getY()-1);
                return;
            }
        }
        throw new PersonnageException("Deplacement en haut à gauche impossible");
    }

    /**
     * Se deplacer en haut à droite si cela est possible sinon
     * @throws PersonnageException
     */
    public void seDeplacerHautDroite() throws PersonnageException {
        int PersoX = this.getX();
        int PersoY = this.getY();
        int PersoX2 = this.getX();
        int PersoY2 = this.getY()-1;
        if(PersoX2+1 <= monde.getGrille().length-1 && PersoY2-1 >= 0)
        {
            Bloc c = monde.getBlocCase(PersoY - 1,PersoX + 1);
            Bloc c2 = monde.getBlocCase(PersoY2 - 1,PersoX2+1);

            if (c instanceof Air) {
                this.setX(this.getX()+1);
                this.setY(this.getY()-1);
                return;
            }
        }
        throw new PersonnageException("Deplacement en haut à droite impossible");
    }

    /**
     * Se deplacer en bas à droite si cela est possible sinon
     * @throws PersonnageException
     */
    public void seDeplacerBasDroite() throws PersonnageException {
        int PersoX = this.getX();
        int PersoY = this.getY();
        int PersoX2 = this.getX();
        int PersoY2 = this.getY()-1;

        System.out.println(" X = " + PersoX + " Y = " + PersoY);
        System.out.println(" X2 = " + PersoX2 + " Y2 = " + PersoY2);

        if(PersoX+1 <= monde.getGrille().length-1 && PersoY+1 <= monde.getGrille()[0].length-1)
        {
            Bloc c = monde.getBlocCase(PersoY + 1,PersoX + 1);
            Bloc c2 = monde.getBlocCase(PersoY2 + 1,PersoX2+1);
            System.out.println(c);
            System.out.println(c2);

            if (c instanceof Air && c2 instanceof Air)
            {
                this.setX(this.getX()+1);
                this.setY(this.getY()+1);
                return;
            }
        }
        throw new PersonnageException("Deplacement en bas à droite impossible");
    }

    /**
     * Se deplacer en bas à gauche si cela est possible sinon
     * @throws PersonnageException
     */
    public void seDeplacerBasGauche() throws PersonnageException {
        int PersoX = this.getX();
        int PersoY = this.getY();
        int PersoX2 = this.getX();
        int PersoY2 = this.getY()-1;
        if(PersoX-1 >= 0 && PersoY+1 <= monde.getGrille()[0].length-1)
        {
            Bloc c = monde.getBlocCase(PersoY+1,PersoX-1);
            Bloc c2 = monde.getBlocCase(PersoY2 + 1,PersoX2-1);

            if(c instanceof Air && c2 instanceof Air)
            {
                this.setX(this.getX()-1);
                this.setY(this.getY()+1);
                return;
            }
        }
        throw new PersonnageException("Deplacement en bas à gauche impossible");
    }


    /**
     * @param y ou on veut miner position verticale
     * @param x ou on veut miner position horizontale
     * @throws CaseException
     * @throws PersonnageException
     */
    public void Miner(int y, int x) throws CaseException, PersonnageException, MinageException {

        int x1 = x-getX();
        int y1 = y-(getY()-1);
        if ( ! ( (x1 == -1 && (y1 >= -1 && y1 <= 2)) || (x1 == 0 && (y1 == -1 || y1 == 2)) || (x1 == 1 && (y1 >= -1 && y1 <= 2))) )
        {
            throw new PersonnageException("Minage seulement case adjacente");
            //return minage.traiter(obj, maCase);
        }
        else {
            if(this.expertMinage != null){
                Case c = this.expertMinage.traiter(this.getObjetTenu(),getMonde().getCase(y,x));
                monde.setCase(y,x,c);
            }
        }

    }



    /**
     * Cette méthode appel l'expert de Fabrication qui verifie si le pattern correspond a celui d'un objet et
     * si le personnage possède les ingrédients pour le fabriquer alors place l'objet fabriquer
     * dans l'inventaire et retire les ingrédients de l'inventaire
     * @param grille Une grille avec le pattern des objets a utiliser pour fabriquer un autre objet
     * @throws ExpertFabricationException
     * @throws PersonnageException
     */
    public void fabrication(Objet[][]grille) throws ExpertFabricationException, PersonnageException {
        Personnage perso= this;
        ajoutMultipleInventaire(this.expertFabrication.traiter(grille,perso));
    }

    public ExpertFabrication getExpertFabrication() {
        return expertFabrication;
    }


    /**
     * @return creer une chaine d'expert pour la fabrication
     */
    private ExpertFabrication creerChaineExpertFabrication()
    {
        ExpertFabrication premierExpert=null;
        premierExpert=new ExpertPlancheAvecBois(premierExpert);
        premierExpert=new ExpertFabricationPiocheBois(premierExpert);
        premierExpert=new ExpertFabricationPiocheEnPierre(premierExpert);
        premierExpert=new ExpertFabricationBatonPlanche(premierExpert);
        return premierExpert;
    }

    /**
     * @return creer une chaine d'expert vide
     */
    private ExpertFabrication creerChaineExpertFabricationVide(){
        ExpertFabrication premierExpert=null;
        return premierExpert;
    }

    public void apprendreFabricationPlancheBois(){
        expertFabrication=new ExpertPlancheAvecBois(expertFabrication);
    }

    public void apprendreFabricationBatonPlanche(){
        expertFabrication=new ExpertFabricationBatonPlanche(expertFabrication);
    }

    public void apprendreFabricationPiocheEnBois(){
        expertFabrication=new ExpertFabricationPiocheBois(expertFabrication);
    }

    public void apprendreFabricationPiocheEnPierre(){
        expertFabrication=new ExpertFabricationPiocheEnPierre(expertFabrication);
    }

    public void apprendreFabricationTorche(){
        expertFabrication=new ExpertFabricationTorche(expertFabrication);
    }


    /**
     * @param dim Dimension de la grille voulu pour avoir la recette 2x2 ou 3x3 ou autre
     * @param objetVoulu Objet dont on veut la recette
     * @param materiau Materiau que l'on veut utiliser pour fabriquer l'objet
     * @return Retourne une grille avec les objets placer dans des cases (le pattern)
     * @throws ExpertRecetteException
     */
    public Objet[][] creerRecette(int dim,Class<? extends Objet> objetVoulu,Class<? extends Objet>materiau) throws ExpertRecetteException {
        return expertRecette.traiter(dim,objetVoulu,materiau);
    }
    public ExpertRecette getExpertRecette(){return expertRecette;}

    /**
     * @return creer chaine des experts de recette
     */
    private ExpertRecette creerChaineExpertRecette(){
        ExpertRecette premierExpert=null;
        premierExpert=new ExpertRecetteBatonPlanche(premierExpert);
        premierExpert=new ExpertRecettePiocheBois(premierExpert);
        premierExpert=new ExpertRecettePiochePierre(premierExpert);
        premierExpert=new ExpertRecettePlancheBois(premierExpert);
        premierExpert=new ExpertRecetteTorcheCharbon(premierExpert);
        return premierExpert;
    }

    /**
     * @return creer une chaine des experts de recette vide
     */
    private ExpertRecette creerChaineExpertRecetteVide(){
        ExpertRecette premierExpert=null;
        return premierExpert;
    }











    public MiningHandler getExpertMinage() {
        return expertMinage;
    }
    private MiningHandler creerChaineMinageVide()
    {
        MiningHandler ExpertMinage = null;
        return ExpertMinage;
    }

    public MiningHandler apprendreTousMinage(){
        this.expertMinage=new MinageMainTerre(this.expertMinage);
        this.expertMinage=new MinageMainHerbe(this.expertMinage);
        this.expertMinage=new MinageMainBois(this.expertMinage);
        this.expertMinage=new MinageLeaf(this.expertMinage);
        this.expertMinage=new MinageMainPierre(this.expertMinage);
        this.expertMinage=new MinageMainMineraiCharbon(this.expertMinage);
        this.expertMinage=new MinagePiocheBois(this.expertMinage);
        this.expertMinage=new MinagePiocheMineraiCharbon(this.expertMinage);
        this.expertMinage=new MinagePiocheTerre(this.expertMinage);
        this.expertMinage=new MinagePiochePierre(this.expertMinage);
        return expertMinage;
    }

    public MiningHandler apprendreMinageMainBois()
    {
        this.expertMinage = new MinageMainBois(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinageHerbe()
    {
        this.expertMinage = new MinageMainHerbe(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinageLys()
    {
        this.expertMinage = new MinageLeaf(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinageMainPierre()
    {
        this.expertMinage = new MinageMainPierre(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinageMainTerre()
    {
        this.expertMinage = new MinageMainTerre(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinagePiocheBois()
    {
        this.expertMinage = new MinagePiocheBois(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinagePiochePierre()
    {
        this.expertMinage = new MinagePiochePierre(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinagePiocheTerre()
    {
        this.expertMinage = new MinagePiocheTerre(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinageMainMineraiCharbon(){
        this.expertMinage=new MinageMainMineraiCharbon(this.expertMinage);
        return this.expertMinage;
    }

    public MiningHandler ApprendreMinagePiocheMineraiCharbon(){
        this.expertMinage=new MinagePiocheMineraiCharbon(this.expertMinage);
        return this.expertMinage;
    }

    @Override
    public String toString() {
        return "Personnage\n" +
                "nom de Joueur: " + nom + "\n" +
                "Position (x: "+this.x+"; y: "+this.y+")\n"+
                "Monde: \n" + monde +
                "Inventaire=" + inventaire.toString() +
                "\nObjetTenu=" + objetTenu+"\n";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personnage that = (Personnage) o;
        return Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
