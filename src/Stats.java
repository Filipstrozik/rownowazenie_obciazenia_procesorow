import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Stats {
    //singleton

    private static Stats instance = null;

    public static Stats getInstance() {
        if (instance == null) {
            instance = new Stats();
        }
        return instance;
    }

    private Stats() {
        procesorSrednieObciazenie = new Average();
        processorLoadVariationAverage = new Average();
        processorLoadsList = new ArrayList<>();
        processorQuerriesCounter = 0;
        processorZapytaniaCounter =0;
        procesorMigracjeCounter =0;
    }


    public Average procesorSrednieObciazenie;
    public Average processorLoadVariationAverage;
    ArrayList<Float> processorLoadsList;
    private int processorQuerriesCounter;
    private int processorZapytaniaCounter;
    private int procesorMigracjeCounter;

    public void addNewObciazenie(float processorLoad) {
        // average load
        procesorSrednieObciazenie.addNewValue(processorLoad);

        // average load variation
        processorLoadsList.add(processorLoad);
    }

    public void incrementProcessorQueriesCounter() {
        processorQuerriesCounter++;
    }

    public void incrementProcessorZapytania(){
        processorZapytaniaCounter++;
    }

    public void incrementProcessorMigracje(){
        procesorMigracjeCounter++;
    }
    public void incrementProcessorMigracje(int ile){
        procesorMigracjeCounter+=ile;
    }

    public float getAveragePorcessorLoading() {
        return (float) procesorSrednieObciazenie.getAverage();
    }


    public float getAverageLoadVariation() {
        // calculate average variation
        float averageLoad = getAveragePorcessorLoading();
        processorLoadVariationAverage.reset();
        for( float value :processorLoadsList)
        {
            float curVariation = Math.abs(value - averageLoad);
            processorLoadVariationAverage.addNewValue(curVariation);
        }

        return (float) processorLoadVariationAverage.getAverage();
    }

    public float getMediana(){
        Collections.sort(processorLoadsList);
        return processorLoadsList.get(processorLoadsList.size()/2);
    }

    public int getProcessorZapytaniaCounter(){
        return processorZapytaniaCounter;
    }

    public int getProcesorMigracjeCounter(){
        return procesorMigracjeCounter;
    }

    public int getAmtOfProcessorQueries() {
        return processorQuerriesCounter;
    }

    public void reset(){
        procesorSrednieObciazenie.reset();
        processorLoadVariationAverage.reset();
        processorLoadsList.clear();
        processorQuerriesCounter = 0;

        procesorMigracjeCounter=0;
        processorZapytaniaCounter=0;
    }

}
