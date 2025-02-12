package Test;

import minecraft.mondeCubique.ExpertCase.ExpertCaseException;
import minecraft.mondeCubique.LeMonde;
import minecraft.mondeCubique.MondeException;
import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreationSteve {
    private static String mondeTestCreation;
    @BeforeAll
    public static void setUp(){
        mondeTestCreation=("src/Test/lescsv/MondeTestCreation.csv");
    }

    @Test
    public void testSteve() throws PersonnageException, ExpertCaseException, MondeException, IOException {
        Personnage personnage = new Personnage(new LeMonde(mondeTestCreation),"Steve");
        assertEquals("Steve",personnage.getNom());
        assertEquals(0,personnage.getTailleInventaire());
        assertEquals(null,personnage.getObjetTenu());
        assertEquals(6,personnage.getX());
        assertEquals(2,personnage.getY());

    }
}
