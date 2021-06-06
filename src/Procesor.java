import java.util.ArrayList;

public abstract class Procesor {
    private static int lastID=0;
    private int procesorID;  // id procesu
    protected ArrayList<Proces> procesList;
    protected ProcesorManager procesorManager;
    protected KonfiguracjaSymulacji config = KonfiguracjaSymulacji.getInstance();
    protected Stats stats = Stats.getInstance();

    //TODO DODAC POLA KTORE BY LICZYLY DLUGOSC CZASU PRZECIAZENIA PROCESORA CZYLI KIEDY OBCIAZENIE JEST>100.0 NP JAKIS CURRENT CZAS I MAX CZAS TEGO ZJAWISKA
    //TODO FAKT ZE JAK JEST OBCIAZONY TO DLUZEJ TRWA CHYBA JEST NAPISANY W WYKONAJ PROCES - ODP NIEPRAWDA TRZEBA TO DODAC
    //TODO KOLEJKA CZEKAJACYCH PROCESOW AZ JAKIS PROCESOR BEDZIIE MIAL OBCIAZENIE<P

    //konstruktor
    public Procesor(ProcesorManager procesorManager) {
        this.procesorID = lastID++;
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
            //TODO TA WARTOSC ZMIEJSZYC PROPORCJONALNIE DO OBCIAZENIA PROCESORA
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
        int amtToTransfer = (int)((config.ileProcentDoTransferu / 100.0) * (float)procesList.size() + 0.5);
        ArrayList<Proces> toTransfer = new ArrayList<>();

        for (int i=0; i<amtToTransfer; i++)
        {
            toTransfer.add(procesList.get(0));
            procesList.remove(0);
        }

        return toTransfer;
    }

    @Override
    public String toString() {
        return "proc: "+ procesorID +" Load: " + getObciazenie() + " Size: "+procesList.size();
    }
}
