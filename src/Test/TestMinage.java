package Test;

import minecraft.lesItems.PiocheEnPierre;
import minecraft.mondeCubique.CaseException;
import minecraft.mondeCubique.ExpertCase.ExpertCaseException;
import minecraft.mondeCubique.LeMonde;
import minecraft.mondeCubique.MondeException;
import minecraft.Minage.*;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Bois;
import minecraft.lesBlocs.Pierre;
import minecraft.lesBlocs.Respawn;
import minecraft.lesItems.Pioche;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TestMinage {
    @Test
    public void TestMinageImpossible() throws ExpertCaseException, MondeException, IOException, PersonnageException, CaseException, MinageException {
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestMine.csv");
        Personnage steve=new Personnage(monde1,"Steve");
        assertEquals(steve.getX(),6);
        assertEquals(steve.getY(),4);
        assertTrue(monde1.getBlocCase(4,6) instanceof Respawn);

        MiningHandler minage = null;
       // MiningService MS = new MiningService(minage);
        assertEquals(minage,null);

        //Test pour voir si (4,7) = bois
        assertInstanceOf(Bois.class,monde1.getCase(4,7).getBloc());

        int y = 4;
        int x = 7;
        steve.Miner(y,x);
        assertInstanceOf(Bois.class,monde1.getCase(4,7).getBloc());

        //Test minage case non adjacente
        assertThrows(PersonnageException.class,()->steve.Miner(5,9));

    }


    @Test
    public void TestMinageMainNue() throws ExpertCaseException, MondeException, IOException, PersonnageException, CaseException, MinageException {
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestMine.csv");
        Personnage steve=new Personnage(monde1,"Steve");
        assertNull(steve.getObjetTenu());


        //Minage bois à main nue
        //MiningHandler minage = null;
        //minage = new MinageMainBois(minage);

        steve.apprendreMinageMainBois();
        steve.Miner(4,7);
        assertThat(monde1.getBlocCase(4,7), instanceOf(Air.class));



        steve.setY(5);
        steve.setX(4);
        steve.Miner(5,5);

        assertThat(monde1.getBlocCase(5,5), instanceOf(Pierre.class));

        //minage = new MinageMainPierre(minage);
        steve.ApprendreMinageMainPierre();
        steve.Miner(5,5);
        assertThat(monde1.getBlocCase(5,5), instanceOf(Air.class));
        assertEquals(monde1.getCase(5,5).isEmptyBloc(),true);

    }

    @Test
    public void TestAvecUnePioche() throws ExpertCaseException, MondeException, IOException, PersonnageException, CaseException, MinageException {
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestMine.csv");
        Personnage steve=new Personnage(monde1,"Steve");
        assertTrue(monde1.getBlocCase(4,6) instanceof Respawn);

        assertNull(steve.getObjetTenu());
        Pioche pioche = new PiocheEnPierre();

        steve.ajoutInventaire(pioche);
        steve.Tenir(pioche);

        //Minage bois avec pioche
        //MiningHandler minage = null;
        //minage = new MinagePiocheBois(minage);
        steve.ApprendreMinagePiocheBois();

        steve.Miner(4,7);
        assertThat(monde1.getBlocCase(4,7), instanceOf(Air.class));
        assertEquals(1,monde1.getCase(4,7).nombreObjet());
        assertTrue(monde1.getCase(4,7).contient(Bois.class));



        steve.setY(5);
        steve.setX(4);
        //Minage Pierre avec pioche
        //minage = new MinagePiochePierre(minage);
        steve.ApprendreMinagePiochePierre();
        steve.Miner(5,5);
        assertTrue(monde1.getCase(5,5).contient(Pierre.class));


        //Steve se dessaisie de la pioche
        steve.seDessaisir();

        //minage = new MinageMainPierre(minage);
        steve.ApprendreMinageMainPierre();

        //Minage pierre sans pioche
        steve.setY(4);
        steve.setX(6);
        steve.Miner(5,6);
        assertThat(monde1.getBlocCase(5,5), instanceOf(Air.class));
        assertEquals(0,monde1.getCase(5,6).nombreObjet());




    }
}
