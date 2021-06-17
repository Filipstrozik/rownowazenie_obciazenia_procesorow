public class ProcessorTyp1 extends Procesor{

    public ProcessorTyp1(ProcesorManager procesorManager){
        super(procesorManager);
    }


    /*
	Na procesorze x pojawia sie zadanie. Nastepnie:
	1. x pyta losowo wybr. procesor y o aktualne obciążenie.
	Jeśli jest mniejsze od progu p, proces jest tam wysyłany.
	Jeśli nie, losujemy i pytamy następny, próbując co najwyżej z razy.
	Jeśli wszystkie wylosowane są obciążone powyżej p, proces wykonuje się na x.
    */

    @Override
    public void dodajProces(Proces proces) {
        for (int i = 0; i < config.iloscProbZapytan_Z; i++) {
            Procesor rangProc = procesorManager.getRandomProcessor();
            stats.incrementProcessorZapytania();
            if(rangProc.getObciazenie()<config.progObciazenia_P){
                rangProc.dodajProcesMimoWszystko(proces);
                stats.incrementProcessorMigracje();
                return;
            }
        }
        procesList.add(proces);
    }
}
