import java.util.ArrayList;
import java.util.Random;

public class ProcesorManager {

    private ArrayList<Procesor> procesorList;
    private KolejkaProcesow = procesQue;
    private GlobalClock clock = GlobalClock.getInstance();
    private SimulationStats stats = SimulationStats.getInstance();
    private Random rand;
    private int nextProcessorToGetNewProcess = 0;

    public ProcessorManager(KolejkaProcesow procQue)
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
}
