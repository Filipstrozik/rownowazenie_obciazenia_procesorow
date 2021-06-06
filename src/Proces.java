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

    //functions

    public Proces(Proces toCopy){
        this.procesID = toCopy.procesID;
        this.procesSize = toCopy.procesSize;
        this.procesRemainingSize = toCopy.procesRemainingSize;
        this.loadOnProcessor = toCopy.loadOnProcessor;
    }

    public void przetworzProces(int ile){
        procesRemainingSize -= ile;
    }

    public boolean isDone(){
        return procesRemainingSize <= 0;
    }

    // getters

    public int getID()
    {
        return procesID;
    }

    public int getSize()
    {
        return procesSize;
    }

    public int getRemainingSize()
    {
        return procesRemainingSize;
    }

    public float getLoadOnProcessor()
    {
        return loadOnProcessor;
    }

    @Override
    public String toString(){
        return "proc: " + procesID +", "+ procesSize+", "+ loadOnProcessor;
    }
}
