package minecraft.lesBlocs;

import minecraft.Objet;

public abstract class Bloc extends Objet {
    public abstract boolean estSolide();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return obj != null && getClass() == obj.getClass();
    }
}
