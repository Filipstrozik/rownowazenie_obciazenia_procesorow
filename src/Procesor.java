import java.util.ArrayList;

public abstract class Procesor {
    private static int lastID=0;
    private final int procesorID;  // id procesu
    protected ArrayList<Proces> procesList;
    protected ProcesorManager procesorManager;
    protected KonfiguracjaSymulacji config = KonfiguracjaSymulacji.getInstance();
    protected Stats stats = Stats.getInstance();

    protected int currentCzasPrzeciazenia;
    protected int maxCzasPrzeciazenia;
    protected Average avg;
    protected boolean isFinished;


    //konstruktor
    public Procesor(ProcesorManager procesorManager) {
        this.procesorID = lastID++;
        procesList = new ArrayList<>();
        this.procesorManager = procesorManager;
        currentCzasPrzeciazenia=0;
        maxCzasPrzeciazenia=0;
        avg = new Average();
        isFinished = false;
    }


    public float getObciazenie() {
        float load = 0;
        for (Proces proces : procesList) {
            load += proces.getLoadOnProcessor();
        }
        return load;
    }

    public abstract void dodajProces(Proces proces);

    public void dodajProcesMimoWszystko(Proces proces) {
        procesList.add(proces);
    }

    public void wykonajProces() {
        for (int i = 0; i < procesList.size(); i++) {
            Proces currentProces = procesList.get(i);
            //tutaj trzeba w zaleznosci od obciazenia wykonac x sekund procesu
            float mult = (float) (currentProces.getLoadOnProcessor() / 100.0);
            float curentOciazegnie = getObciazenie();
//            avg.addNewValue(curentOciazegnie); // to mi zalatwia dodwanie obciazenia w procesmanager?
            if(curentOciazegnie>100.0){
                //System.out.println("proces dluzej wykonywany bo obciazenie!");
                mult = mult * 100f / curentOciazegnie;
                currentProces.przetworzProces((int) (100 * mult + 0.5));
            } else {
               //System.out.println("proces normalnie wykonywany bo obciazenie!");
                currentProces.przetworzProces((int) (100 * mult + 0.5));
            }

            //jezeli skonczony to usun z procesora
            if(currentProces.isDone()){
                procesList.remove(i);
                i--;
            }

        }
    }



    public ArrayList<Proces> transferSomeProcesses()
    {
        int amtToTransfer = (int)((config.ileProcentDoTransferu / 100.0) * (float)procesList.size() + 0.5);
        ArrayList<Proces> toTransfer = new ArrayList<>();
        for (int i=0; i<amtToTransfer; i++)
        {
            toTransfer.add(procesList.get(0));
            procesList.remove(0);
        }
        return toTransfer;
    }

    public void setFinished(){
        isFinished=true;
    }

    @Override
    public String toString() {
        return "proc: "+ procesorID +" Load: " + getObciazenie() +" MaxTime: " + maxCzasPrzeciazenia + " Size: "+procesList.size();
    }

    public static void resetID(){
        lastID=0;
    }
}
