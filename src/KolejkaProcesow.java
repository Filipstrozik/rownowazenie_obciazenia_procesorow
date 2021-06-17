import java.util.ArrayList;
import java.util.Random;

public class KolejkaProcesow {

    private ArrayList<Proces> procesList;
    private ArrayList<Integer> procesTimeList;
    private Clock clock = Clock.getInstance();
    private KonfiguracjaSymulacji config = KonfiguracjaSymulacji.getInstance();


    public KolejkaProcesow(){
        procesList = new ArrayList<>();
        procesTimeList = new ArrayList<>();
    }

    public void setProcesList(ArrayList<Proces> procList, ArrayList<Integer> procesTimeList){
        this.procesList = procList;
        this.procesTimeList = procesTimeList;
//        this.procesTimeList.clear();

//        int randomBound = procList.size() * config.czasMiedzyProcesami; // // zakomentowanie bo blad
//        Random rng = new Random();
//
//        for (int i = 0; i < procesList.size(); i++) {
//            procesTimeList.add(rng.nextInt(randomBound)); //
//        }
    }

    public ArrayList<Proces> getProcesyDoPrzetworzenia(){
        ArrayList<Proces> retLista = new ArrayList<>();
        for (int i = 0; i < procesList.size(); i++) {
            if(procesTimeList.get(i)<= clock.getCurrentTime()){
                retLista.add(procesList.get(i));

                procesList.remove(i);
                procesTimeList.remove(i);
                i--;
            }
        }
        return retLista;
    }

    public int getKolejkaProcesowSize()
    {
        return procesList.size();
    }
}
