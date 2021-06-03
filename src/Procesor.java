import java.util.ArrayList;

public abstract class Procesor {
    protected ArrayList<Proces> procesList;
    protected ProcesorManager procesorManager;
    protected KonfiguracjaSymulacji config = KonfiguracjaSymulacji.getInstance();
    protected Stats stats = Stats.getInstance();

    //konstruktor
    public Procesor(ProcesorManager procesorManager) {
        procesList = new ArrayList<>();
        this.procesorManager = procesorManager;
    }

    public float getObciazenie() {
        float load = 0;
        for (int i = 0; i < procesList.size(); i++) {
            load += procesList.get(i).getLoadOnProcessor();
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
            currentProces.przetworzProces((int) (100 * mult + 0.5));
            //jezeli skonczony to usun z procesora
            if(currentProces.isDone()){
                procesList.remove(i);
                i--;
            }

        }
    }



    public ArrayList<Proces> transferSomeProcesses()
    {
        int amtToTransfer = (int)((config.ileProcentDoTransferu / 100.0f) * (float)procesList.size() + 0.5f);
        ArrayList<Proces> toTransfer = new ArrayList<>();

        for (int i=0; i<amtToTransfer; i++)
        {
            toTransfer.add(procesList.get(0));
            procesList.remove(0);
        }

        return toTransfer;
    }

}
