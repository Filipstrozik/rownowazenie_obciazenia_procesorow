import java.util.ArrayList;
import java.util.Random;

public class ProcesorTyp3 extends ProcesorTyp2 {
    public ProcesorTyp3(ProcesorManager procesorManager){
        super(procesorManager);
    }
    /*
	    Na procesorze x pojawia sie zadanie. Nastepnie:
	    3.Jak w pkt 2, z tym że procesory o obciążeniu mniejszym od minimalnego progu r
	    pytają losowo wybrane procesory i jesli obc. zapytanego jest większe od p,
	    pytający przejmuje część jego zadań (założyć jaką).
    */
    @Override
    public void dodajProces(Proces proces) {
        //tak jak w typie 2
        super.dodajProces(proces);
        //ponizej modyfikacja dzialania procesora typu 3
        if(getObciazenie()< config.prog_R){
            Procesor rng = procesorManager.getRandomProcessor();
            if(rng.getObciazenie() > config.progObciazenia_P){
                ArrayList<Proces> listaProcesowPrzechwyconych = rng.transferSomeProcesses();
                procesList.addAll(listaProcesowPrzechwyconych);
            }
        }


    }

}
