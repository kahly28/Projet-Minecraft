package Test;

import minecraft.mondeCubique.ExpertCase.ExpertCaseException;
import minecraft.mondeCubique.LeMonde;
import minecraft.mondeCubique.MondeException;
import minecraft.lesBlocs.Bloc;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDeplacement {
    private static String mondeTestDeplacement;

    @BeforeAll
    public  static void setUp(){
        mondeTestDeplacement=("src/Test/lescsv/MondeTestDeplacement.csv");
    }

    @Test
    public void testDeplacement() throws MondeException, IOException, PersonnageException, ExpertCaseException {
        Personnage personnage = new Personnage(new LeMonde(mondeTestDeplacement),"Steve");
        assertEquals(6,personnage.getX());
        assertEquals(2,personnage.getY());
        Personnage.Direction dir = Personnage.Direction.GAUCHE;
        Personnage.Direction dir2 = Personnage.Direction.DROITE;
        Personnage.Direction dir3 = Personnage.Direction.HAUT;
        Personnage.Direction dir4 = Personnage.Direction.BAS;
        Personnage.Direction dir5 = Personnage.Direction.HAUT_GAUCHE;
        Personnage.Direction dir6 = Personnage.Direction.HAUT_DROITE;
        Personnage.Direction dir7 = Personnage.Direction.BAS_GAUCHE;
        Personnage.Direction dir8 = Personnage.Direction.BAS_DROITE;

        //Test deplacement vers la gauche
        personnage.seDeplacerAGauche();
        assertEquals(5,personnage.getX());
        assertEquals(2,personnage.getY());


        //Test deplacement vers la droite
        personnage.seDeplacerADroite();
        assertEquals(6,personnage.getX());
        assertEquals(2,personnage.getY());

        //Test deplacement vers le haut
        personnage.seDeplacerEnHaut();
        assertEquals(6,personnage.getX());
        assertEquals(1,personnage.getY());

        //Test deplacemet vers le bas
        personnage.seDeplacerEnBas();
        assertEquals(6,personnage.getX());
        assertEquals(2,personnage.getY());


        //Test deplacement Haut gauche
        personnage.setX(16);
        personnage.setY(4);
        System.out.println(personnage.getMonde().getBlocCase(4,16));
        Bloc c = personnage.getMonde().getBlocCase(5,16);
        System.out.println(c);

        personnage.seDeplacerHautGauche();
        assertEquals(15,personnage.getX());
        assertEquals(3,personnage.getY());

        //Test deplacement Bas droit
        personnage.seDeplacerBasDroite();
        assertEquals(16,personnage.getX());
        assertEquals(4,personnage.getY());

        //Test deplacement Haut droit
        personnage.seDeplacerHautDroite();
        assertEquals(17,personnage.getX());
        assertEquals(3,personnage.getY());

        //Test deplacement Bas Gauche
        personnage.seDeplacerBasGauche();
        assertEquals(16,personnage.getX());
        assertEquals(4,personnage.getY());
    }


    @Test
    public void testDeplacementHorsDuMonde() throws ExpertCaseException, MondeException, IOException, PersonnageException {
        LeMonde monde1 = new LeMonde(mondeTestDeplacement);
        Personnage personnage = new Personnage(monde1,"Steve");
        assertEquals(6,personnage.getX());
        assertEquals(2,personnage.getY());
        Personnage.Direction dir = Personnage.Direction.GAUCHE;
        Personnage.Direction dir2 = Personnage.Direction.DROITE;
        Personnage.Direction dir3 = Personnage.Direction.HAUT;
        Personnage.Direction dir4 = Personnage.Direction.BAS;
        Personnage.Direction dir5 = Personnage.Direction.HAUT_GAUCHE;
        Personnage.Direction dir6 = Personnage.Direction.HAUT_DROITE;
        Personnage.Direction dir7 = Personnage.Direction.BAS_GAUCHE;
        Personnage.Direction dir8 = Personnage.Direction.BAS_DROITE;

        System.out.println(monde1.getBlocCase(2,6));

        //Test deplacement haut gauche
        personnage.setX(0);
        personnage.setY(4);
        //System.out.println(monde1.getBloc(20,10));
        assertThrows(PersonnageException.class,()->personnage.seDeplacerAGauche());



        //Test deplacement haut gauche
        assertThrows(PersonnageException.class,()->personnage.seDeplacerHautGauche());

        //Test deplacement bas
        personnage.setX(19);
        personnage.setY(9);
        assertThrows(PersonnageException.class,()->personnage.seDeplacerEnBas());


        //Test deplacement haut
        personnage.setX(19);
        personnage.setY(1);
        assertThrows(PersonnageException.class,()->personnage.seDeplacerEnHaut());

    }

    @Test
    public void testDeplacementObstacle() throws ExpertCaseException, MondeException, IOException, PersonnageException {
        LeMonde monde1 = new LeMonde(mondeTestDeplacement);
        Personnage personnage = new Personnage(monde1,"Steve");
        assertEquals(6,personnage.getX());
        assertEquals(2,personnage.getY());
        Personnage.Direction dir = Personnage.Direction.GAUCHE;
        Personnage.Direction dir2 = Personnage.Direction.DROITE;
        Personnage.Direction dir3 = Personnage.Direction.HAUT;
        Personnage.Direction dir4 = Personnage.Direction.BAS;
        Personnage.Direction dir5 = Personnage.Direction.HAUT_GAUCHE;
        Personnage.Direction dir6 = Personnage.Direction.HAUT_DROITE;
        Personnage.Direction dir7 = Personnage.Direction.BAS_GAUCHE;
        Personnage.Direction dir8 = Personnage.Direction.BAS_DROITE;

        //Test deplacement vers la droite
        assertThrows(PersonnageException.class,()->personnage.seDeplacerADroite());

        //Test deplacement vers la droite 2
        personnage.setX(4);
        personnage.setY(3);
        assertThrows(PersonnageException.class,()->personnage.seDeplacerADroite());


        //Test deplacement vers la gauche
        personnage.setX(13);
        personnage.setY(3);
        assertThrows(PersonnageException.class,()->personnage.seDeplacerAGauche());


        //Test deplacement vers le haut
        personnage.setX(8);
        personnage.setY(6);
        assertThrows(PersonnageException.class,()->personnage.seDeplacerEnHaut());


    }
}
