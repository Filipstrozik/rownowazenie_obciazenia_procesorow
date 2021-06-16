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

    public int iloscProcesorow_N = 50;      //100 - mniejsze obciazenie- brak przeciazen - czesto ponizej progu R ale nie ponizej progu P

    public float progObciazenia_P = 80;     // ma wplyw w sumie na kazda symulacje

    public int iloscProbZapytan_Z = 10;     //2 lub 1 zmienia zapytania oraz dzialania strategii 1

    public float prog_R = 20;               // pokazuje roznice dzialania miedzy strategia 2 a 3

    // my config values

    public int czasMiedzyProcesami = 1;   // sytuacje typowe czyli dosc gesto procesow
    public int amtOfProcesses = 10000;    //

    public int minProcesRozmiar = 100;    // szybkosc przetwaraznia zalezna od przydzielonego zasobu (obciazenia)
    public int maxProcesRozmiar = 5000;   //

    public float minProcesObciazenie = 15f; // dosc podobne procesy
    public float maxProcesObciazenie = 20f;

    public  float ileProcentDoTransferu = 20;    // value between 0 and 100
}
