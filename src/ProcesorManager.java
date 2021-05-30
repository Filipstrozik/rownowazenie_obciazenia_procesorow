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
}
