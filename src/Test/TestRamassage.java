package Test;

import minecraft.lesItems.PiocheEnPierre;
import minecraft.mondeCubique.*;
import minecraft.mondeCubique.ExpertCase.ExpertCaseException;
import minecraft.lesBlocs.Bloc;
import minecraft.lesBlocs.BlocException;
import minecraft.lesItems.Item;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import minecraft.lesBlocs.Herbe;
import minecraft.lesBlocs.Pierre;
import minecraft.lesBlocs.Planche;
import minecraft.lesBlocs.Respawn;
import minecraft.lesItems.Pioche;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestRamassage {
    @Test
    public void testRamassageObjets() throws ExpertCaseException, MondeException, IOException, PersonnageException, BlocException, CaseException {
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCreation.csv");
        Personnage steve=new Personnage(monde1,"Steve");
        //on vérifie que steve est en (2,6) sur un bloc de respawn
        assertEquals(steve.getX(),6);
        assertEquals(steve.getY(),2);
        assertTrue(monde1.getBlocCase(2,6) instanceof Respawn);

        //on verifie que la case (2,7) est vide (sans objet)
        Case c=monde1.getCase(2,7);
        assertTrue(c.isEmptyBloc());

        //on creer une pioche on l'ajoute et on vérifie sa présence
        Item pioche1=new PiocheEnPierre();
        monde1.addObjet(2,7,pioche1);
        assertTrue(c.contient(Pioche.class));

        //on creer une une pierre et on la place et on verifie le contenu du bloc
        //si il y a bien une pioche et une pierre dedans
        Bloc pierre=new Pierre();
        monde1.addObjet(2,7,pierre);
        assertEquals(2,c.nombreObjet());
        assertTrue(c.contient(Pierre.class));
        assertTrue(c.contient(Pierre.class));

        //on verifie que la case
        Bloc planche=new Planche();
        assertFalse(monde1.getBlocCase(1,7).estSolide());
        assertTrue(monde1.getCase(1,7).isEmptyBloc());
        assertThrows(MondeException.class,()->monde1.addObjet(1,7,planche));
        assertTrue(monde1.getCase(1,7).isEmptyBloc());
        assertThrows(MondeException.class,()->monde1.addObjet(3,7,planche));
        assertTrue(monde1.getBlocCase(3,7 )instanceof Herbe);
        assertTrue(monde1.getCase(3,7).isEmptyBloc());

        //Verif sur steve et le ramassage
        assertEquals(0,steve.getTailleInventaire());
        steve.ramasserObjet(2,7);
        assertEquals(2,steve.getTailleInventaire());
        assertTrue(steve.possede(Pierre.class,1));
        assertTrue(steve.possede(Pioche.class,1));

        System.out.println(steve.getY());
        System.out.println(steve.getX());

        monde1.getCase(2,8).ajouterObjet(planche);
        assertThrows(PersonnageException.class,()->steve.ramasserObjet(2,8));


    }
}