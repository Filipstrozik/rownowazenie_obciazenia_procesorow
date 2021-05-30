public class Clock {

    private static Clock instance = null;
    public static Clock getInstance(){
        if(instance == null){
            instance = new Clock();
        }
        return instance;
    }
    private Clock(){
        reset();
    }

    private int currentTime;

    public int getCurrentTime(){
        return currentTime;
    }

    public void Tick(){
        currentTime++;
    }

    public void reset(){
        currentTime =0;
    }
}
