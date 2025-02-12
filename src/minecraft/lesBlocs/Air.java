package minecraft.lesBlocs;

public class Air extends Bloc {
    @Override
    public boolean estSolide() {
        return false;
    }

    @Override
    public String toString() {
        return "Air";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return obj != null && getClass() == obj.getClass();
    }
}
