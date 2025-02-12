package Test;

import minecraft.Minage.MinageException;
import minecraft.Objet;
import minecraft.expertFabrication.ExpertFabrication;
import minecraft.expertFabrication.ExpertFabricationException;
import minecraft.expertFabrication.ExpertPlancheAvecBois;
import minecraft.expertRecette.*;
import minecraft.lesBlocs.BlocException;
import minecraft.lesBlocs.Bois;
import minecraft.lesBlocs.Pierre;
import minecraft.lesBlocs.Planche;
import minecraft.lesItems.Baton;
import minecraft.lesItems.Pioche;
import minecraft.lesItems.PiocheEnBois;
import minecraft.lesItems.PiocheEnPierre;
import minecraft.mondeCubique.CaseException;
import minecraft.mondeCubique.ExpertCase.ExpertCaseException;
import minecraft.mondeCubique.LeMonde;
import minecraft.mondeCubique.MondeException;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class TestIntegration {

    @Test
    public void testComplet() throws ExpertCaseException, MondeException, IOException, PersonnageException, CaseException, ExpertRecetteException, ExpertFabricationException, BlocException, MinageException {
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestComplet.csv");
        Personnage steve=new Personnage(monde1,"Steve");

        steve.ApprendreMinageMainPierre();
        steve.apprendreMinageMainBois();
        steve.ApprendreMinagePiocheBois();
        steve.ApprendreMinagePiochePierre();
        steve.ApprendreMinageLys();
        steve.ApprendreMinageMainTerre();
        steve.ApprendreMinageHerbe();
        steve.ApprendreMinagePiocheTerre();


        steve.apprendreFabricationPiocheEnBois();
        steve.apprendreFabricationBatonPlanche();
        steve.apprendreFabricationTorche();
        steve.apprendreFabricationPlancheBois();
        steve.apprendreFabricationPiocheEnPierre();

        //Steve ne possede rien
        assertEquals(0,steve.getTailleInventaire());


        //Steve se deplace vers la droite
        steve.seDeplacerADroite();

        assertTrue(monde1.getCase(4,7).getBloc() instanceof Bois);



        //Steve mine les 3 blocs de bois
        steve.Miner(4,7);
        steve.Miner(3,7);
        steve.Miner(2,7);


        //Steve ramasse les 3 blocs de bois
        steve.ramasserObjet(4,7);
        steve.ramasserObjet(2,7);
        steve.ramasserObjet(3,7);

        //On verifie que Steve possede bien 3 blocs de bois
        assertTrue(steve.possede(Bois.class,3));
        assertFalse(steve.possede(Bois.class,4));

        ExpertFabrication premierExpert=null;
        premierExpert=new ExpertPlancheAvecBois(premierExpert);

        ExpertRecette premierExpertRecette=null;
        premierExpertRecette=new ExpertRecettePlancheBois(premierExpertRecette);
        premierExpertRecette = new ExpertRecetteBatonPlanche(premierExpertRecette);

        //Creation de la recette de planche
        Objet[][]recettePlanche=premierExpertRecette.traiter(3,Planche.class,Bois.class);

        //steve.ajoutMultipleInventaire(premierExpert.traiter(recettePlanche,steve,new Planche()));
        steve.fabrication(recettePlanche);

        //On verifie que steve possede 4 planches et 2 bois
        assertTrue(steve.possede(Planche.class, 4));
        assertTrue(steve.possede(Bois.class,2));

        //Steve fabrique 4 bâtons
        Objet[][] recetteBaton = premierExpertRecette.traiter(2,Baton.class,Planche.class);
        //steve.ajoutMultipleInventaire(premierExpert.traiter(recetteBaton,steve,new Baton()));
        steve.fabrication(recetteBaton);

        //On verifie que steve possede 2 planches, 2 blocs de bois et 4 batons
        assertTrue(steve.possede(Planche.class,2));
        assertTrue(steve.possede(Bois.class,2));
        assertTrue(steve.possede(Baton.class,4));
        assertEquals(steve.getTailleInventaire(),8);

        //Steve se deplace au pied de l'arbre
        steve.seDeplacerADroite();
        steve.seDeplacerADroite();
        steve.seDeplacerADroite();
        steve.seDeplacerADroite();
        steve.seDeplacerADroite();
        steve.seDeplacerADroite();
        steve.seDeplacerADroite();
        steve.seDeplacerEnBas();
        steve.seDeplacerADroite();
        assertEquals(5,steve.getY());
        assertEquals(14,steve.getX());

        //Steve mine les 2 blocs de bois
        steve.Miner(5,15);
        steve.Miner(4,15);

        //Steve ramasse les 2 blocs de bois
        steve.ramasserObjet(4,15);
        steve.ramasserObjet(5,15);


        //On verifie que Steve possede 2 planches, 4 blocs de bois et 4 batons
        assertEquals(steve.getTailleInventaire(),10);
        assertTrue(steve.possede(Planche.class,2));
        assertTrue(steve.possede(Bois.class,4));
        assertTrue(steve.possede(Baton.class,4));

        System.out.println("FABRICATION PIOCHE");
        premierExpertRecette = new ExpertRecettePiocheBois(premierExpertRecette);
        Objet[][] recettePiocheEnBois = premierExpertRecette.traiter(3, Pioche.class,Bois.class);
        steve.fabrication(recettePiocheEnBois);

        //On verifie que Steve possede 2 planches, 1 bloc de bois, 2 batons et une pioche en bois
        System.out.println(steve.getInventaire());
        assertEquals(6,steve.getTailleInventaire());
        assertTrue(steve.possede(Planche.class,2));
        assertTrue(steve.possede(Bois.class,1));
        assertTrue(steve.possede(Baton.class,2));
        assertTrue(steve.possede(PiocheEnBois.class,1));

        System.out.println("y = " + steve.getY() + " x = " + steve.getX());
        //Steve se deplace pres de la zone en pierre
        steve.seDeplacerAGauche();
        System.out.println("y = " + steve.getY() + " x = " + steve.getX());


        //Steve mine de la pierre avec une pioche pour pouvoir les rammasser
        steve.Tenir(new PiocheEnBois());
        steve.Miner(5,12);
        steve.Miner(6,12);
        steve.ramasserObjet(5,12);
        steve.seDeplacerAGauche();
        steve.seDeplacerEnBas();
        steve.ramasserObjet(6,12);

        //Steve descend dans le trou qu'il a creuser

        System.out.println("y = " + steve.getY() + " x = " + steve.getX());

        //Steve mine et ramasse les 2 blocs de pierre
        steve.Miner(5,11);
        steve.Miner(6,11);
        steve.ramasserObjet(5,11);
        steve.ramasserObjet(6,11);

        System.out.println(steve.getInventaire());



        //On créer la recette pour avoir une pioche en pierre puis on la créer
        premierExpertRecette = new ExpertRecettePiochePierre(premierExpertRecette);
        Objet[][] recettePiocheEnPierre = premierExpertRecette.traiter(3,Pioche.class, Pierre.class);
        steve.fabrication(recettePiocheEnPierre);

        steve.seDessaisir();

        //on  vérifie que Steve possède 6 objets, soit 0 bâtons, 1 bloc de bois, 1 bloc de pierre,
        // 2 planches, 1 pioche en bois et 1 pioche en pierre
        System.out.println(steve.getInventaire());
        assertEquals(6,steve.getTailleInventaire());
        assertTrue(steve.possede(Planche.class,2));
        assertTrue(steve.possede(Bois.class,1));
        assertTrue(steve.possede(Pierre.class,1));
        assertTrue(steve.possede(Baton.class,0));
        assertTrue(steve.possede(PiocheEnBois.class,1));
        assertTrue(steve.possede(PiocheEnPierre.class,1));



    }
}
