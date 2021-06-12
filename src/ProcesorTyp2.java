public class ProcesorTyp2 extends Procesor{

    public ProcesorTyp2(ProcesorManager procesorManager){
        super(procesorManager);
    }

    /*
	Na procesorze x pojawia sie zadanie. Nastepnie:
	2.Jesli obciążenie x przekracza wartość progową p,
	proces zostaje wysłany na losowo wybrany procesor y o obciążeniu mniejszym od p
	(jeśli wylosowany y ma obc.>p, losowanie powtarza się do skutku).
	Jeśli nie przekracza - proces wykonuje się na x.
    */

    @Override
    public void dodajProces(Proces proces) {
        //sprawdz czy procsor może przyjac proces czyli czy próg p jest
//        System.out.println("X: " + toString());
        if(getObciazenie()>config.progObciazenia_P){
            for (int i = 0; i < config.iloscProcesorow_N; i++) {
                Procesor rng = procesorManager.getRandomProcessor();
                stats.incrementProcessorZapytania();
//                stats.incrementProcessorQueriesCounter();
                if(rng.getObciazenie()< config.progObciazenia_P){
                    rng.dodajProcesMimoWszystko(proces);
                    stats.incrementProcessorMigracje();
//                    stats.incrementProcessorQueriesCounter();
                    return; // czy ten return niszczy?//TODO w sumie nie niszczy niby
                }
            }
        }
        procesList.add(proces);
    }
}
