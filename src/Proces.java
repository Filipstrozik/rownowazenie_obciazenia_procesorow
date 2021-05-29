public class Proces {
    private static int lastID=0;
    private int procesID;  // id procesu
    private int procesSize; // wielkosc czasowa procesu
    private int procesRemainingSize; // pozostala wielkosc
    private float loadOnProcessor;

    public Proces (int procesSize, float loadOnProcessor){
        this.procesID = lastID++;

        this.procesSize = procesSize;
        procesRemainingSize = procesSize;

        this.loadOnProcessor = loadOnProcessor;
    }


    
}
