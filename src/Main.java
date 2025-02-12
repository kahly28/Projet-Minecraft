import minecraft.Personnage.Personnage;
import minecraft.Personnage.PersonnageException;
import minecraft.mondeCubique.ExpertCase.ExpertCaseException;
import minecraft.mondeCubique.LeMonde;
import minecraft.mondeCubique.MondeException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            LeMonde nouveauMonde=new LeMonde("src/Test/lescsv/MondeTestCharbon.csv");
            Personnage steve=new Personnage(nouveauMonde,"LeSteve");
            System.out.println(steve.toString());

        } catch (IOException | MondeException | ExpertCaseException | PersonnageException e) {
            throw new RuntimeException(e);
        }


    }
}