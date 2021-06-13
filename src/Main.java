import java.util.ArrayList;
import java.util.Random;

public class Main {
    static ArrayList<Proces> procesList;
    static ArrayList<Integer> procesCzasList;
    static Random rng;

    enum ProcesorTyp {
        proctyp1, proctyp2, proctyp3
    }

    static void gernerateProcesList(){
        KonfiguracjaSymulacji config = KonfiguracjaSymulacji.getInstance();
        procesList = new ArrayList<>();
        procesCzasList = new ArrayList<>();

        for (int i = 0; i < config.amtOfProcesses; i++) {
            int size = rng.nextInt(config.maxProcessSize-config.minProcessSize)+config.minProcessSize;
            float load = (rng.nextFloat()*(config.maxProcessLoad-config.minProcessLoad))+config.minProcessLoad;
            procesList.add(new Proces(size,load));
            int randomBound = config.amtOfProcesses * config.czasMiedzyProcesami;
            Random rng = new Random();
            procesCzasList.add(rng.nextInt(randomBound));
        }
    }

    static ArrayList<Proces> ProcesListCopy(){
        ArrayList<Proces> copy = new ArrayList<>();
        for (Proces proc:procesList){
            copy.add(new Proces(proc));
        }
        return copy;
    }

    static ArrayList<Integer> ProcesTimeListCopy(){
        ArrayList<Integer> copy = new ArrayList<>();
        copy.addAll(procesCzasList);
        return copy;
    }

    static void simulate(ProcesorTyp proctyp){
        Stats statystyka = Stats.getInstance();
        statystyka.reset();

        Clock.getInstance().reset();

        KonfiguracjaSymulacji config = KonfiguracjaSymulacji.getInstance();

        ArrayList<Procesor> procesorArrayList = new ArrayList<>();

        KolejkaProcesow procQue = new KolejkaProcesow();

        procQue.setProcesList(ProcesListCopy(), ProcesTimeListCopy()); //przekazujemy kopie zawsze

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


//        while (procQue.getKolejkaProcesowSize() > 0){
//            procesorManager.excuteProcessorsOnce();
//        }

        while (!procesorManager.isFinished){
            procesorManager.excuteProcessorsOnce();
        }


        System.out.println();
        if(proctyp == ProcesorTyp.proctyp1){
            System.out.println("Strategia procesora 1");
        } else if(proctyp == ProcesorTyp.proctyp2){
            System.out.println("Strategia procesora 2");
        } else {
            System.out.println("Strategia procesora 3");
        }
        //zbieranie informacji do statystyk
//        System.out.println("POSZCZEGOLNE SREDNIE PROCESOROW");
        int MaxTime =0;
        float minLoad=300f, maxLoad = 0f;
        for(Procesor procesor: procesorManager.getProcesorList()){
            float currentAvg = (float) procesor.avg.getAverage();
            statystyka.addNewObciazenie(currentAvg);
//            System.out.println(procesor+"  "+currentAvg);
            if(procesor.maxCzasPrzeciazenia> MaxTime){
                MaxTime = procesor.maxCzasPrzeciazenia;
            }
            if(minLoad > currentAvg){
                minLoad = currentAvg;
            }
            if(maxLoad < currentAvg){
                maxLoad = currentAvg;
            }

        }
//        System.out.println();
        System.out.println("CZAS: " + Clock.getInstance().getCurrentTime());
//        System.out.println("Mediana: " + statystyka.getMediana());
        System.out.println("Srednia obciazenia: " + statystyka.getAveragePorcessorLoading() +"   "+statystyka.procesorSrednieObciazenie.getAmount() + "   " + statystyka.procesorSrednieObciazenie.getSum());
        System.out.println("Odychylenie od sredniej: " + statystyka.getAverageLoadVariation());
        System.out.println("Ilosc zapytan i migracji: " + statystyka.getProcessorZapytaniaCounter()+ "  " + statystyka.getProcesorMigracjeCounter());
        System.out.println("MaxTime: " + MaxTime + " minLoad: " + minLoad + " maxLoad: "+ maxLoad);
    }




    public static void main(String[] args) {
        rng = new Random();

        gernerateProcesList();
// ok proces listy takie same
//        System.out.println(procesList);
        simulate(ProcesorTyp.proctyp1);
        Procesor.resetID();
//        System.out.println(procesList);
        simulate(ProcesorTyp.proctyp2);
        Procesor.resetID();
//        System.out.println(procesList);
        simulate(ProcesorTyp.proctyp3);


    }
}