package Test;

import minecraft.mondeCubique.Case;
import minecraft.mondeCubique.ExpertCase.ExpertCaseException;
import minecraft.mondeCubique.LeMonde;
import minecraft.mondeCubique.MondeException;
import minecraft.lesBlocs.Bloc;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.Pierre;
import minecraft.lesBlocs.Respawn;
import minecraft.lesBlocs.Terre;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCreationMonde {
    private static String mondeVide;
    private static String mondeIncoherent;
    private static String mondeTestCreation;

    @BeforeAll
    public static void initDesMonde(){
        mondeVide=("");
        mondeIncoherent=("src/Test/lescsv/MondeIncoherent.csv");
        mondeTestCreation=("src/Test/lescsv/MondeTestCreation.csv");
    }

    @Test
    public void fichierInexistant(){
        assertThrows(MondeException.class,()->new LeMonde(mondeVide));
    }

    @Test
    public void FichierMalFormate(){
        assertThrows(MondeException.class,()->new LeMonde(mondeIncoherent));
    }

    @Test
    public void testNormal() throws ExpertCaseException, MondeException, IOException {
        LeMonde monde=new LeMonde(mondeTestCreation);
        int largeur=monde.getLargeur();
        int hauteur=monde.getHauteur();
        Case grid[][] = monde.getGrille();
        assertEquals(hauteur,grid[0].length);
        assertEquals(largeur,grid.length);
        for(int i = 0; i<hauteur;i++){
            for (int j=0;j<largeur;j++){
                assertNotNull(grid[j][i]);
            }
        }
        assertEquals(20,largeur);
        assertEquals(10,hauteur);
        Bloc c = monde.getBlocCase(2,6);
        assertTrue(c instanceof Respawn);
    }

    @Test
    public void testGetCase() throws ExpertCaseException, MondeException, IOException {
        LeMonde monde=new LeMonde(mondeTestCreation);

        assertTrue(monde.getBlocCase(0,0) instanceof Air);

        assertTrue(monde.getBlocCase(0,19) instanceof Air);

        assertTrue(monde.getBlocCase(9,19) instanceof Terre);

        assertTrue(monde.getBlocCase(9,0) instanceof Terre);

        assertTrue(monde.getBlocCase(8,9) instanceof Pierre);
    }



}
