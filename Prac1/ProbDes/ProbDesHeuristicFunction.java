package ProbDes;

import IA.Desastres.Grupos;
import IA.Desastres.Grupo;
import IA.Desastres.Centros;
import IA.Desastres.Centro;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.HeuristicFunction;

//Suma de todos los tiempos empleados por los helicópteros en rescatar a todos los grupos (no
//consideramos que todo se hace en paralelo).

public class ProbDesHeuristicFunction implements HeuristicFunction { 
	public double getHeuristicValue(Object n){
		ProbDesEstat board = (ProbDesEstat) n; // ???WHY declarat però no s'utilitza mai
	
	    ArrayList < ArrayList < Sortida > > estat = ProbDesEstat.getEstado();
	    //Centros centres = ProbDesEstat.getCentros();
	    //Grupos grups = ProbDesEstat.getGrupos();
	    double SumaTempsTotal = 0.0;
	    
		// Un helicopter tarda 1 minut en recollir una persona, 2 si és un grup de prioritat 1
		// Entre sortida i sortida un heli s'està 10 mins esperant a la base
		// Un Helicopter es desplaça a una velocitat de 100 km/h
	    // Un helicopter ha de rescatar a tots els integrants del grup de una
    	
    	for (int heli = 0; heli < estat.size(); heli++){
    	    
    	    boolean primer = true;
    	    for (int sortida = 0; sortida < estat.get(heli).size(); sortida++){
    	        
    	        if (primer) primer = false;
    	        else SumaTempsTotal += (10.0/60.0); //suma del cooldown de l'heli
    	        SumaTempsTotal += ProbDesEstat.getTempsEmpleatSortida(heli,sortida);
    	        
    	    }
    	}
    	return -SumaTempsTotal;
	}
}