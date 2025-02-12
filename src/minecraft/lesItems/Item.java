package minecraft.lesItems;

import minecraft.Objet;

public abstract class Item extends Objet {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return obj != null && getClass() == obj.getClass();
    }

}
