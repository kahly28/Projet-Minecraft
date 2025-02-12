package Test;
import minecraft.Objet;
import minecraft.Personnage.Personnage;
import minecraft.expertFabrication.ExpertFabricationException;
import minecraft.lesBlocs.Air;
import minecraft.lesBlocs.MineraiCharbon;
import minecraft.lesItems.*;
import minecraft.mondeCubique.LeMonde;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCharbon {

    @Test
    public void testCreationMondeAvecCharbon() throws Exception{
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCharbon.csv");
        Personnage steve=new Personnage(monde1,"Steve");
        //on verifie que la case (4,4) contient un minerai de charbon
        assertTrue(monde1.getBlocCase(4,4)instanceof MineraiCharbon);

    }

    @Test
    public void testMinageMineraiCharbonMain()throws Exception{
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCharbon.csv");
        Personnage steve=new Personnage(monde1,"Steve");

        //on verifie que steve ne peut pas miner a main nu le charbon
        assertNull(steve.getObjetTenu());
        //pourquoi ça throw rien
        //assertThrows(Exception.class,()->steve.Miner(steve.getObjetTenu(),monde1.getCase(4,4),4,4));

        steve.ApprendreMinageMainMineraiCharbon();

        //test minage a la main
        assertTrue(monde1.getBlocCase(4,4)instanceof MineraiCharbon);
        steve.Miner(4,4);
        assertTrue(monde1.getBlocCase(4,4)instanceof Air);
        assertTrue(monde1.getCase(4,4).isEmptyBloc());
    }

    @Test
    public void testMinageMineraiCharbonPioche()throws Exception{
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCharbon.csv");
        Personnage steve=new Personnage(monde1,"Steve");


        steve.ajoutInventaire(new PiocheEnPierre());
        steve.Tenir(new PiocheEnPierre());
        assertTrue(steve.getObjetTenu()instanceof Pioche);
        //pourquoi ça throw rien
        //assertThrows(Exception.class,()->steve.Miner(steve.getObjetTenu(),monde1.getCase(4,4),4,4));

        steve.ApprendreMinagePiocheMineraiCharbon();


        //test minage a la main
        assertTrue(monde1.getBlocCase(4,4)instanceof MineraiCharbon);
        steve.Miner(4,4);
        assertTrue(monde1.getBlocCase(4,4)instanceof Air);
        assertFalse(monde1.getCase(4,4).isEmptyBloc());
        assertEquals(0,steve.getTailleInventaire());
        steve.ramasserObjet(4,4);
        int nbItems=steve.getTailleInventaire();
        //on verifie que le minerai à donner entre 1 et 4 charbons
        assertTrue(nbItems==1||nbItems==2||nbItems==3||nbItems==4);
        System.out.println("il y a normalement "+nbItems+"charbon(s)");
        assertTrue(steve.possede(Charbon.class,nbItems));
    }

    @Test
    public void testFabricationTorche()throws Exception{
        LeMonde monde1 = new LeMonde("src/Test/lescsv/MondeTestCharbon.csv");
        Personnage steve=new Personnage(monde1,"Steve");


        steve.apprendreFabricationTorche();

        steve.ajoutInventaire(new Baton());
        steve.ajoutInventaire(new Charbon());
        System.out.println(steve);
        assertTrue(steve.possede(Charbon.class,1));
        assertTrue(steve.possede(Baton.class,1));
        Objet[][]recetteTorche=steve.creerRecette(2,Torche.class, Charbon.class);

        for (int i = 0; i < recetteTorche.length; i++) {
            for (int j = 0; j < recetteTorche[i].length; j++) {
                Objet caseCourante = recetteTorche[i][j];
                if (caseCourante == null) {
                    System.out.print("- ");  // Afficher un trait pour les cases vides
                } else {
                    System.out.print("X ");  // Afficher le symbole de l'objet
                }
            }
            System.out.println();  // Passer à la ligne après chaque ligne de la grille
        }

        assertTrue(recetteTorche[0][1]instanceof Charbon);
        assertTrue(recetteTorche[1][1]instanceof Baton);


        steve.fabrication(recetteTorche);
        assertTrue(steve.possede(Torche.class,4));
        assertTrue(steve.possede(Baton.class,0));
        assertTrue(steve.possede(Charbon.class,0));
        assertEquals(4,steve.getTailleInventaire());
        assertThrows(ExpertFabricationException.class,()->steve.fabrication(recetteTorche));


    }
}
