package ProbDes;

import IA.Desastres.Grupos;
import IA.Desastres.Grupo;
import IA.Desastres.Centros;
import IA.Desastres.Centro;
import ProbDes.ProbDesEstat;
import ProbDes.ProbDesEstat.Sortida;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.HeuristicFunction;

//Suma de todos los tiempos empleados por los helicópteros en rescatar a todos los grupos (no
//consideramos que todo se hace en paralelo).

public class ProbDesHeuristicFunction2 implements HeuristicFunction { 
	public double getHeuristicValue(Object n){
		ProbDesEstat board = (ProbDesEstat) n; // 
	
	    ArrayList < ArrayList < Sortida > > estat = board.getEstado();
	    double SumaTempsTotal = 0.0;
	    
		// Un helicopter tarda 1 minut en recollir una persona, 2 si és un grup de prioritat 1
		// Entre sortida i sortida un heli s'està 10 mins esperant a la base
		// Un Helicopter es desplaça a una velocitat de 100 km/h
	    // Un helicopter ha de rescatar a tots els integrants del grup de una
        
        // Calcula el temps de totes les sortides de tots els helicopters 
    	for (int heli = 0; heli < estat.size(); heli++){
    	    
    	    boolean primer = true;
    	    for (int sortida = 0; sortida < estat.get(heli).size(); sortida++){
    	        
    	        if (primer) primer = false;
    	        else if (sortida != estat.get(heli).size()-1) SumaTempsTotal += (10.0/60.0); //suma del cooldown de l'heli
    	        SumaTempsTotal += board.getTempsEmpleatSortida(heli,sortida);
    	        
    	    }
        }
        
        // Calcula el temps que ha passat des de l'inici fins a que es rescata l'ultim grup de prio = 1
        ArrayList<Double> tempsPrio1xHeli = new ArrayList<Double> (0); //size = c*h
        // Inicialitzar tots a 0
        for (int t = 0; t < estat.size(); ++t)
            tempsPrio1xHeli.add(new Double(0.0));
        // Recorrer tots els helis
        for (int heli = 0; heli < estat.size(); ++heli) {
            
            double tempsHeliAux = 0.0;
            
            for (int sort = 0; sort < estat.get(heli).size(); ++sort) {
                
                tempsHeliAux += board.getTempsEmpleatSortida(heli, sort);
                
                if (estat.get(heli).get(sort).grupsRecollits[0] != -1) 
                    if (board.getGrup(estat.get(heli).get(sort).grupsRecollits[0]).getPrioridad() == 1)
                        tempsPrio1xHeli.add(heli, new Double (tempsHeliAux));
                
                if (estat.get(heli).get(sort).grupsRecollits[1] != -1) 
                    if (board.getGrup(estat.get(heli).get(sort).grupsRecollits[1]).getPrioridad() == 1)
                        tempsPrio1xHeli.add(heli, new Double (tempsHeliAux));

                if (estat.get(heli).get(sort).grupsRecollits[2] != -1) 
                    if (board.getGrup(estat.get(heli).get(sort).grupsRecollits[2]).getPrioridad() == 1)
                        tempsPrio1xHeli.add(heli, new Double (tempsHeliAux));
            }
        }
        
        double maxTempsGrupPrio1 = 0.0;
        for (int i = 0; i < tempsPrio1xHeli.size(); ++i) {
            if (tempsPrio1xHeli.get(i).doubleValue() > maxTempsGrupPrio1)
                maxTempsGrupPrio1 = tempsPrio1xHeli.get(i).doubleValue();
        }

        //Ponderar com es vulgui, per ara 50%/50%
    	return (0.5*SumaTempsTotal + 0.5*maxTempsGrupPrio1);
	}
}	

