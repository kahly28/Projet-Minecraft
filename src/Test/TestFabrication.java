package Test;

import minecraft.Personnage.PersonnageException;
import minecraft.expertFabrication.ExpertFabricationException;
import minecraft.expertRecette.*;
import minecraft.lesBlocs.BlocException;
import minecraft.lesBlocs.Pierre;
import minecraft.lesItems.PiocheEnBois;
import minecraft.lesItems.PiocheEnPierre;
import minecraft.mondeCubique.CaseException;
import minecraft.mondeCubique.ExpertCase.ExpertCaseException;
import minecraft.mondeCubique.LeMonde;
import minecraft.Objet;
import minecraft.Personnage.Personnage;
import minecraft.lesBlocs.Bois;
import minecraft.lesBlocs.Planche;
import minecraft.lesItems.Baton;
import minecraft.lesItems.Pioche;
import minecraft.mondeCubique.MondeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFabrication {
    @Test
    public void TestFabricationPlanche() throws Exception {
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCreation.csv");
        Personnage steve=new Personnage(monde1,"Steve");



        //Fabrication de Planche
        steve.apprendreFabricationPlancheBois();

        steve.ajoutInventaire(new Bois());
        assertEquals(1,steve.getTailleInventaire());
        assertTrue(steve.possede(Bois.class,1));

        //creation de la recette de planche
        Objet[][]recettePlanche=steve.getExpertRecette().traiter(3,Planche.class,Bois.class);

        //fonction affichage de grille
        for (int i = 0; i < recettePlanche.length; i++) {
            for (int j = 0; j < recettePlanche[i].length; j++) {
                Objet caseCourante = recettePlanche[i][j];
                if (caseCourante == null) {
                    System.out.print("- ");  // Afficher un trait pour les cases vides
                } else {
                    System.out.print("X ");  // Afficher le symbole de l'objet
                }
            }
            System.out.println();  // Passer à la ligne après chaque ligne de la grille
        }

        steve.fabrication(recettePlanche);
        assertTrue(steve.possede(Planche.class,4));
    }
    @Test
    public void testFabricationBaton() throws ExpertCaseException, MondeException, IOException, PersonnageException, CaseException, ExpertRecetteException, ExpertFabricationException, BlocException {
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCreation.csv");
        Personnage steve=new Personnage(monde1,"Steve");

       steve.apprendreFabricationBatonPlanche();


        monde1.addObjet(steve.getY(),steve.getX()+1,new Planche());
        monde1.addObjet(steve.getY(),steve.getX()+1,new Planche());
        steve.ramasserObjet(steve.getY(),steve.getX()+1);
        assertEquals(2,steve.getTailleInventaire());
        assertTrue(steve.possede(Planche.class,2));
        Objet[][]recetteBaton=steve.getExpertRecette().traiter(3,Baton.class,Planche.class);
        for (int i = 0; i < recetteBaton.length; i++) {
            for (int j = 0; j < recetteBaton[i].length; j++) {
                Objet caseCourante = recetteBaton[i][j];
                if (caseCourante == null) {
                    System.out.print("- ");  // Afficher un trait pour les cases vides
                } else {
                    System.out.print("X ");  // Afficher le symbole de l'objet
                }
            }
            System.out.println();  // Passer à la ligne après chaque ligne de la grille
        }
        steve.fabrication(recetteBaton);
        assertTrue(steve.possede(Baton.class,4));
        assertTrue(steve.possede(Planche.class,0));
        assertThrows(ExpertFabricationException.class,()->steve.fabrication(recetteBaton));

    }

    @Test
    public void testFabricationPiocheEnBois()throws Exception{
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCreation.csv");
        Personnage steve=new Personnage(monde1,"Steve");

        steve.apprendreFabricationPiocheEnBois();

        monde1.addObjet(steve.getY(),steve.getX()+1,new Bois());
        monde1.addObjet(steve.getY(),steve.getX()+1,new Bois());
        monde1.addObjet(steve.getY(),steve.getX()+1,new Bois());
        monde1.addObjet(steve.getY(),steve.getX()+1,new Baton());
        monde1.addObjet(steve.getY(),steve.getX()+1,new Baton());
        steve.ramasserObjet(steve.getY(),steve.getX()+1);
        assertTrue(steve.possede(Bois.class,3));
        assertTrue(steve.possede(Baton.class,2));
        assertEquals(5,steve.getTailleInventaire());
        Objet[][]recettePiocheEnBois=steve.getExpertRecette().traiter(3, Pioche.class, Bois.class);
        steve.fabrication(recettePiocheEnBois);
        assertEquals(1,steve.getTailleInventaire());
        assertTrue(steve.possede(PiocheEnBois.class,1));
        assertTrue(steve.possede(Bois.class,0));
        assertTrue(steve.possede(Baton.class,0));
        assertThrows(ExpertFabricationException.class,()->steve.fabrication(recettePiocheEnBois));






    }
    @Test
    public void testFabricationPiocheEnPierre()throws Exception{
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCreation.csv");
        Personnage steve=new Personnage(monde1,"Steve");

        steve.apprendreFabricationPiocheEnPierre();


        monde1.addObjet(steve.getY(),steve.getX()+1,new Pierre());
        monde1.addObjet(steve.getY(),steve.getX()+1,new Pierre());
        monde1.addObjet(steve.getY(),steve.getX()+1,new Pierre());

        monde1.addObjet(steve.getY(),steve.getX()+1,new Baton());
        monde1.addObjet(steve.getY(),steve.getX()+1,new Baton());
        steve.ramasserObjet(steve.getY(),steve.getX()+1);
        assertTrue(steve.possede(Pierre.class,3));
        assertTrue(steve.possede(Baton.class,2));
        assertEquals(5,steve.getTailleInventaire());
        Objet[][]recettePiocheEnPierre=steve.getExpertRecette().traiter(3, Pioche.class, Pierre.class);
        steve.fabrication(recettePiocheEnPierre);
        assertEquals(1,steve.getTailleInventaire());
        assertTrue(steve.possede(PiocheEnPierre.class,1));
        assertTrue(steve.possede(Pierre.class,0));
        assertTrue(steve.possede(Baton.class,0));
        assertThrows(ExpertFabricationException.class,()->steve.fabrication(recettePiocheEnPierre));






    }

}
