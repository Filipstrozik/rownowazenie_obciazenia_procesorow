import java.util.ArrayList;

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
    }


    public Average procesorSrednieObciazenie;
    public Average processorLoadVariationAverage;
    ArrayList<Float> processorLoadsList;
    private int processorQuerriesCounter;

    public void addNewObciazenie(float processorLoad) {
        // average load
        procesorSrednieObciazenie.addNewValue(processorLoad);

        // average load variation
        processorLoadsList.add(processorLoad);
    }

    public void incrementProcessorQueriesCounter() {
        processorQuerriesCounter++;
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


    public int getAmtOfProcessorQueries() {
        return processorQuerriesCounter;
    }

    public void reset(){
        procesorSrednieObciazenie.reset();
        processorLoadVariationAverage.reset();
        processorLoadsList.clear();
        processorQuerriesCounter = 0;
    }

}
