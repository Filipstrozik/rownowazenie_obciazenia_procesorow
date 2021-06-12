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
//        int counter=0;
         if(getObciazenie()< config.prog_R ){ //TODO lepiej dodefinowac dzialanie to co na kartce -> odzcielic migracje od zapytan -> zrobic tak ze bierze po jednym z kazdego procesorze o >P dopoki przejdziemy z >R na aktualnym
            //TODO migracje kazda na ilosc transferowanych procesow 1:1. odzielic ilosc zapytan

            Procesor rng = procesorManager.getRandomProcessor();
            if(rng.getObciazenie()==0.0){
//                System.out.println("skip");
            }
            stats.incrementProcessorZapytania();
//             System.out.println("INFO PRZED MIGRACJA");
//            System.out.println("X: "+ toString() + "  RNG: "+ rng);
            while(rng.getObciazenie() > config.progObciazenia_P && getObciazenie()<config.prog_R){ //zamieniono na while
//                System.out.println("MIGRACJA...");
                ArrayList<Proces> listaProcesowPrzechwyconych = rng.transferSomeProcesses();
                procesList.addAll(listaProcesowPrzechwyconych);
                stats.incrementProcessorMigracje(listaProcesowPrzechwyconych.size());
//                System.out.println("Ile migruje: "+listaProcesowPrzechwyconych.size());
//                System.out.println("INFO PO MIGRACJI");
//                System.out.println("X: "+ toString() + "  RNG: "+ rng);
            }
//            counter++;
//            System.out.println("TRANSFER PROCESOW");
        }


    }

}
