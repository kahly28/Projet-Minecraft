package minecraft.lesBlocs;

public class Terre extends Bloc {
    @Override
    public boolean estSolide() {
        return true;
    }

    @Override
    public String toString() {
        return "Terre";
    }
}
