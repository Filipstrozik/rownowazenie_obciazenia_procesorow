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


    public void excuteProcessorsOnce(){
        int procesQueSize = procesQue.getKolejkaProcesowSize();
        if(procesQueSize!=0){
            ArrayList<Proces> newProcessList = procesQue.getProcesyDoPrzetworzenia();
            while(newProcessList.size()>0){
//                System.out.println(newProcessList);
//                System.out.println(procesorList.get(nextProcessorToGetNewProcess));     // problem moze troche z liniowo wkazuja na procseory
                procesorList.get(nextProcessorToGetNewProcess).dodajProces(newProcessList.get(0)); // tutaj maja miejsce trzy strategie wykonywania dodawania procesu
                newProcessList.remove(0);
//                System.out.println(procesorList.get(nextProcessorToGetNewProcess));     // problem moze troche z liniowo wkazuja na procseory

                nextProcessorToGetNewProcess++;
                nextProcessorToGetNewProcess %= procesorList.size();
            }
        }

        for(Procesor procesor:procesorList){

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
