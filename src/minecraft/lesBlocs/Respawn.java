package minecraft.lesBlocs;

public class Respawn extends Air{
    private static Respawn instance=null;

    private Respawn(){};

    public static Respawn getInstance(){
        if(instance == null){
            instance = new Respawn();
        }
        return instance;
    }

    @Override
    public String toString() {
        return "Respawn";
    }


}