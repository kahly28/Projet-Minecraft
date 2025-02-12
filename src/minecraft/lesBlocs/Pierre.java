package minecraft.lesBlocs;

public class Pierre extends Bloc {
    @Override
    public boolean estSolide() {
        return true;
    }

    @Override
    public String toString() {
        return "Pierre";
    }
}
