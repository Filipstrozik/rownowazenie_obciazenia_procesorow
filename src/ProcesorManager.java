import java.util.ArrayList;
import java.util.Random;

public class ProcesorManager {

    private ArrayList<Procesor> procesorList;
    private KolejkaProcesow procesQue;
    private Clock clock = Clock.getInstance();
    private Stats stats = Stats.getInstance();
    private Random rand;
    private int nextProcessorToGetNewProcess;

    public boolean isFinished;
    public int skonczoneProcesy;

    public ProcesorManager(KolejkaProcesow procQue)
    {
        procesorList = new ArrayList<>();
        this.procesQue = procQue;
        rand = new Random();
        isFinished = false;
        nextProcessorToGetNewProcess=0;
        skonczoneProcesy=0;
    }

    //functions

    public void setProcessorList(ArrayList<Procesor> procesorList)
    {
        this.procesorList = new ArrayList<>(procesorList);
    }


    public Procesor getRandomProcessor()
    {
        int index = Math.abs(rand.nextInt() % procesorList.size());
        return procesorList.get(index);
    }


    public void excuteProcessorsOnce(){ //TODO jezeli procesque size()==0 to nie dodawaj nawet xd
        int procesQueSize = procesQue.getKolejkaProcesowSize();
        if(procesQueSize!=0){
            ArrayList<Proces> newProcessList = procesQue.getProcesyDoPrzetworzenia();
            while(newProcessList.size()>0){
                procesorList.get(nextProcessorToGetNewProcess).dodajProces(newProcessList.get(0)); // tutaj maja miejsce trzy strategie wykonywania dodawania procesu
                newProcessList.remove(0);

                nextProcessorToGetNewProcess++;                         //TODO czy to sprawia, że zawsze pojawia sie na tym samym procesorze procses? - raczej tak
                nextProcessorToGetNewProcess %= procesorList.size();
//                System.out.println(nextProcessorToGetNewProcess);     // problem moze troche z liniowo wkazuja na procseory
            }
        }

//        if(procesorList.get(0).getObciazenie()>80.0){
//            System.out.println(procesorList.get(0).getObciazenie());
//        }
//        System.out.println(procesorList.size());
//        for(Procesor proc:procesorList){
//            if(proc.getObciazenie()>100.0){
//                System.out.println(proc);
//            }
//        }

        //TODO tutaj w tpetli sprawdzaj czas przeciazenia i dodawaj czas -> jezeli jest to dodaj czas / jezeli nie dod sprawdz z max czas i zeruj czas curr - done
        for(Procesor procesor:procesorList){
            //TODO jezeli procesQue jest pusta oraz size procesora jest 0 to finished procesor! jezeli wszystkie finished do procesormanager tez finished!
            // czy nie powinno byc wczesiej liczone obciazenia?
            if(!procesor.isFinished){
                procesor.wykonajProces();
                float currentObciazenia = procesor.getObciazenie();
                procesor.avg.addNewValue(currentObciazenia); // jednak nie , badzmy w czasie
                if(currentObciazenia>100f){
                    procesor.currentCzasPrzeciazenia++;
                } else {
                    if (procesor.currentCzasPrzeciazenia>0){
                        if(procesor.currentCzasPrzeciazenia > procesor.maxCzasPrzeciazenia){
                            procesor.maxCzasPrzeciazenia = procesor.currentCzasPrzeciazenia; //podmiana
                        }
                    }
                    procesor.currentCzasPrzeciazenia=0;
                }
                //chyba maxczas przeciazenia zalatwione
                // ktore wybiermy czy srednia na procesorze czy srednia ogolnie
//            stats.addNewObciazenie(procesor.getObciazenie()); //average na wszyskich procesorach
                //TODO w takim razie trzeba miec pole Average w kazdym procesorze tam miec srednia jego obciazenia i potem policzyc srednia z sredni. - w sumie nie trzeba bo srednia ze sredniej to to samo co srednia ze wszysckich chyba!!
                if(procesQueSize ==0 && procesor.procesList.size()==0){
                    procesor.setFinished(); // procesor jest skonczony
                }
            }
        }
        int counter =0;
        for(Procesor procesor: procesorList){
            if(procesor.isFinished){
                counter++;
            }
        }
        if(counter==procesorList.size()){
            isFinished=true;
        }
        //lub
//        stats.addNewObciazenie(procesorList.get(0).getObciazenie()); // average load on the first processor

        clock.tick();
    }

    public ArrayList<Procesor> getProcesorList(){
        return procesorList;
    }
}
//TODO ZROZUMIEC DZIALANIE SYMULACJI ORAZ ZACHOWAN WYNIKOW WOBEC PARAMETROW, PRZECZYTAC COS Z TEORII
//TODO POSPRAWDZAC POPRAWNOSC + ZAIMPLEMENTOAC JAKIS MIERNIK JAK DLUGO WYSTEPUJE PRZECIAZENIE OBCIAZENIE>100