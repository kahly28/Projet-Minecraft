package minecraft.mondeCubique;

import minecraft.mondeCubique.ExpertCase.*;
import minecraft.lesBlocs.Bloc;
import minecraft.Objet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class LeMonde {
    private Case[][] grille; // "le monde" est représenté par une grille de Case et chaque case contient un bloc
    private String fichierCsv; // le fichier .csv qui contient le monde

    private int largeur; // largeur du monde
    private int hauteur; // hauteur du monde

    /**
     * @param fichierCSV le fichier CSV avec le monde decrit a l'interieur.
     * @throws IOException
     * @throws ExpertCaseException lorsque la liste des experts ne peut pas creer une case (exemple bloc inconnu)
     * @throws MondeException lancé lorsque l'on rencontre un problème lors du chargement du fichier
     * ou de la vérification du fichier (probleme de taille, pas de fichier...).
     * Cette méthode prend en paramètre un fichier csv et le retranscrit sous la forme d'une
     * grille avec des cases et dans chaque case se trouve un bloc.
     * On utilise une COR pour creer chacune des cases avec le bon bloc à l'intérieur.
     */
    public LeMonde(String fichierCSV) throws IOException, ExpertCaseException, MondeException {
        int x=0,y=0;
        this.fichierCsv=fichierCSV;

        //initialisation des experts
        ExpertCase expert = creerChaineExpert();


        if (fichierCSV == null) {
            throw new MondeException("Fichier ne peut pas être null");
        }
        File fichier = new File(fichierCSV);

        if (!fichier.exists()) {
            throw new MondeException("Ceci n'est pas un fichier");
        }
        this.hauteur=countRows(fichierCSV);
        this.largeur=countColumns(fichierCSV);
        this.grille=new Case[largeur][hauteur];

        if(!verifierFichier(fichier,hauteur,largeur)){
            throw new MondeException("la taille du monde est incorrect elle doit être de "+hauteur+" " +
                    "ligne "+largeur+" colonne");
        }

        BufferedReader reader = null;
        String ligne;

        int nbrLine = 0;

        try {
            reader = new BufferedReader(new FileReader(fichier));

            while ((ligne = reader.readLine()) != null) {
                String[] blocs=ligne.split(";");
                if(blocs.length!=largeur|| x>hauteur-1){
                    throw new MondeException("la largeur doit être égale à 20 par 10");
                }
                // On a bien lu une ligne ue fichier, maintenant qu'est-ce qu'on en fait ??
                if (expert==null) {
                    // Si y a pas de parser, alors on ne sais vraiment pas quoi faire avec et on l'affiche...
                    throw new ExpertCaseException("il n'y a pas d'expert");
                }
                else
                    // Puisqu'on a un parser, on l'utilise. C'est lui qui traitera la ligne
                    // pour créer des Blocs
                    try {
                        for(String bloc : blocs) {
                            grille[y][x]=expert.traiter(bloc);
                            y++;
                        }
                        y=0;
                        x++;
                    }
                    catch (ExpertCaseException e) {
                        System.err.println(e.getMessage()+ligne);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int getLargeur(){
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    /**
     * @param fichierCsv
     * @return int
     * @throws IOException
     * compte le nombre de colonnes du fichier
     */
    public int countRows(String fichierCsv)throws IOException{
        int rowCount=0;
        try(BufferedReader reader = new BufferedReader(new FileReader(fichierCsv))) {
            while (reader.readLine()!=null){
                rowCount++;
            }
        }
        return rowCount;
    }

    /**
     * @param csvFilePath
     * @return int
     * @throws IOException
     * compte le nombre de colonnes
     */
    public int countColumns(String csvFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                String[] columns = firstLine.split(";");
                return columns.length;
            }
        }
        return 0;
    }



    /**
     * @return
     * initialise les experts pour créer un monde
     */
    private ExpertCase creerChaineExpert(){
        ExpertCase premierExpert=null;
        premierExpert=new LeafCaseExpert(premierExpert);
        premierExpert=new BoisCaseExpert(premierExpert);
        premierExpert=new TerreCaseExpert(premierExpert);
        premierExpert=new HerbeCaseExpert(premierExpert);
        premierExpert=new AirCaseExpert(premierExpert);
        premierExpert=new PierreCaseExpert(premierExpert);
        premierExpert = new RespawnCaseExpert(premierExpert);
        premierExpert=new CharbonMineraiExpert(premierExpert);
        return premierExpert;
    }

    public Case getCase(int y,int x){
        return grille[x][y];
    }

    public void setCase(int y,int x,Case b){
        grille[x][y]=b;
    }

    public Bloc getBlocCase(int y,int x){
        return grille[x][y].getBloc();
    }

    public void setBlocCase(int y,int x,Bloc bloc){
        grille[x][y].setBloc(bloc);
    }

    /**
     * @param y
     * @param x
     * @param objet
     * @throws MondeException
     * @throws CaseException
     * ajoute un objet dans une case de la grille creer(le monde)
     * ne passe pas par la fabrication
     */
public void addObjet(int y, int x, Objet objet) throws MondeException, CaseException {
        if(!getCase(y,x).getBloc().estSolide()){
            if(!getCase(y+1,x).getBloc().estSolide()){
                throw new MondeException("le bloc en dessous est vide donc impossible d'ajouter un objet");
            }else{
                Case c=getCase(y,x);
                c.ajouterObjet(objet);
            }
        }else{
            throw new MondeException("ce n'est pas un bloc fluide donc impossible de poser d'objet");
        }
}

    public Case[][]getGrille() {
        return grille;
    }

    /**
     * @param file
     * verifie que le fichier passer en parametre à bien un nombre de ligne :
     * @param hauteur
     * verifie que pour chaque ligne on a un nombre d'élément égal à :
     * @param largeur
     * @return boolean (true or false)
     */
    public static boolean verifierFichier(File file,int hauteur,int largeur){
        int nbLignes = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] elements = ligne.split(";");
                if (elements.length != largeur) {
                    return false;
                }
                nbLignes++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nbLignes == hauteur;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fichier: "+fichierCsv+"\n"+"Taille du monde :\n"+"\tHauteur: "+hauteur+"\n\tLargeur: "+largeur+"\n");
        sb.append("Contenu de la grille : \n");

        for (int k=0;k<grille.length;k++){
            sb.append(String.format("%15d",k));
            sb.append(" ");
        }
        sb.append("\n");

        for (int i = 0; i < grille[0].length; i++) {
            sb.append(" "+i);
            for (int j = 0; j < grille.length; j++) {
                sb.append(grille[j][i].toString()).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeMonde leMonde = (LeMonde) o;
        return largeur == leMonde.largeur && hauteur == leMonde.hauteur && Arrays.equals(grille, leMonde.grille) && Objects.equals(fichierCsv, leMonde.fichierCsv);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fichierCsv, largeur, hauteur);
        result = 31 * result + Arrays.hashCode(grille);
        return result;
    }
}
