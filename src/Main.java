import java.util.ArrayList;
import java.util.Random;

public class Main {
    static ArrayList<Proces> procesList;
    static Random rng;

    enum ProcesorTyp {
        proctyp1, proctyp2, proctyp3
    }

    static void gernerateProcesList(){
        KonfiguracjaSymulacji config = KonfiguracjaSymulacji.getInstance();
        procesList = new ArrayList<>();

        for (int i = 0; i < config.amtOfProcesses; i++) {
            int size = rng.nextInt(config.maxProcessSize-config.minProcessSize)+config.minProcessSize;
            float load = rng.nextFloat()*(config.maxProcessSize-config.minProcessSize)+config.minProcessSize;
            procesList.add(new Proces(size,load));
        }
    }

    static ArrayList<Proces> ProcesListCopy(){
        ArrayList<Proces> copy = new ArrayList<>();
        for (Proces proc:procesList){
            copy.add(new Proces(proc));
        }
        return copy;
    }

    static void simulate(ProcesorTyp proctyp){
        Stats statystyka = Stats.getInstance();
        statystyka.reset();

        Clock.getInstance().reset();

        KonfiguracjaSymulacji config = KonfiguracjaSymulacji.getInstance();

        ArrayList<Procesor> procesorArrayList = new ArrayList<>();

        KolejkaProcesow procQue = new KolejkaProcesow();

        procQue.setProcesList(ProcesListCopy()); //przekazujemy kopie zawsze

        ProcesorManager procesorManager = new ProcesorManager(procQue);

        for (int i = 0; i < config.iloscProcesorow_N; i++) {
            if(proctyp == ProcesorTyp.proctyp1){
                procesorArrayList.add(new ProcessorTyp1(procesorManager));
            } else if(proctyp == ProcesorTyp.proctyp2){
                procesorArrayList.add(new ProcesorTyp2(procesorManager));
            } else {
                procesorArrayList.add(new ProcesorTyp3(procesorManager));
            }
            
        }

        procesorManager.setProcessorList(procesorArrayList);


        while (procQue.getKolejkaProcesowSize() > 0){
            procesorManager.excuteProcessorsOnce();
        }

        if(proctyp == ProcesorTyp.proctyp1){
            System.out.println("Strategia procesora 1");
        } else if(proctyp == ProcesorTyp.proctyp2){
            System.out.println("Strategia procesora 2");
        } else {
            System.out.println("Strategia procesora 3");
        }

        System.out.println("Srednia obciazenia procesora: " + statystyka.getAveragePorcessorLoading());
        System.out.println("Odychylenie od sredniej obciazenia procesora: " + statystyka.getAverageLoadVariation());
        System.out.println("Ilosc zapytan i migracji: " + statystyka.getAmtOfProcessorQueries());
    }




    public static void main(String[] args) {
	// write your code here
    }
}
//TODO zrobic simulate