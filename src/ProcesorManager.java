import java.util.ArrayList;
import java.util.Random;

public class ProcesorManager {

    private ArrayList<Procesor> procesorList;
    private KolejkaProcesow procesQue;
    private Clock clock = Clock.getInstance();
    private Stats stats = Stats.getInstance();
    private Random rand;
    private int nextProcessorToGetNewProcess = 0;

    public ProcesorManager(KolejkaProcesow procQue)
    {
        procesorList = new ArrayList<>();
        this.procesQue = procQue;
        rand = new Random();
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
        ArrayList<Proces> newProcessList = procesQue.getProcesyDoPrzetworzenia();
        while(newProcessList.size()>0){
            procesorList.get(nextProcessorToGetNewProcess).dodajProces(newProcessList.get(0));
            newProcessList.remove(0);

            nextProcessorToGetNewProcess++;
            nextProcessorToGetNewProcess %= procesorList.size();
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


        for(Procesor procesor:procesorList){
            procesor.wykonajProces();
        }
        stats.addNewObciazenie(procesorList.get(0).getObciazenie()); // average load on the first processor

        clock.tick();
    }

    public ArrayList<Procesor> getProcesorList(){
        return procesorList;
    }
}
//TODO ZROZUMIEC DZIALANIE SYMULACJI ORAZ ZACHOWAN WYNIKOW WOBEC PARAMETROW, PRZECZYTAC COS Z TEORII
//TODO POSPRAWDZAC POPRAWNOSC + ZAIMPLEMENTOAC JAKIS MIERNIK JAK DLUGO WYSTEPUJE PRZECIAZENIE OBCIAZENIE>100