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
        int index = rand.nextInt() % procesorList.size();
        return procesorList.get(index);
    }

    //TODO executeProcesOnce

    public void excuteProcessorsOnce(){
        ArrayList<Proces> newProcessList = procesQue.getProcesyDoPrzetworzenia();
        while(newProcessList.size()>0){
            procesorList.get(nextProcessorToGetNewProcess).dodajProces(newProcessList.get(0));
            newProcessList.remove(0);

            nextProcessorToGetNewProcess++;
            nextProcessorToGetNewProcess %= procesorList.size();
        }

        for(Procesor procesor:procesorList){
            procesor.wykonajProces();
        }
        stats.addNewObciazenie(procesorList.get(0).getObciazenie());

        clock.tick();
    }
}
