public class KonfiguracjaSymulacji {

    //singleton

    private static KonfiguracjaSymulacji instance = null;

    public static KonfiguracjaSymulacji getInstance(){
        if(instance == null){
            instance = new KonfiguracjaSymulacji();
        }
        return instance;
    }

    private KonfiguracjaSymulacji(){

    }


    //pola

    public int iloscProcesorow_N = 50;

    public float progObciazenia_P = 8;

    public int iloscProbZapytan_Z = 5;

    public float prog_R = 5;

    // my config values
    public  float ileProcentDoTransferu = 20;    // value between 0 and 100
    public int czasMiedzyProcesami = 1;      // 3 time units average between new task is added
    public int amtOfProcesses = 10000;
    public int minProcessSize = 100;
    public int maxProcessSize = 1000; // assuming that process sized 100 can be computed in one cycle if use 100% load

    public float minProcessLoad = 0.5f;
    public float maxProcessLoad = 8;
}