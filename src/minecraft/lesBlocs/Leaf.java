package minecraft.lesBlocs;

public class Leaf extends Bloc {
    @Override
    public boolean estSolide() {
        return true;
    }

    @Override
    public String toString() {
        return "Leaf";
    }
}
