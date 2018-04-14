// imporem totes les classes de ProbDes
import ProbDes.*;

//imports de aima
import aima.search.framework.Problem;
import aima.search.framework.GraphSearch;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch; //<--- aquest no fa falta de moment, ja però no passa res, és com un include q no es fa servir

//imports de java
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;         // d'aquests no se quins fem servir, pero sembla que no gaires, de moment deixa'ls, ja els traruem al final
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        int seed = 1187;
        int nGrups = 100;
        int nCentres = 10;
        int nHeliXCentre = 1;
        
        ProbDesEstat board = new ProbDesEstat (seed, nGrups , nCentres , nHeliXCentre);

        System.out.println("--- ESTAT INICIAL ---");
        System.out.print('\n');
        for (int i = 0; i < board.getEstado().size(); ++i) {
            System.out.print("Heli " + i + " =>   ");
            board.imprimeixHeli(i);
        }
        System.out.print('\n');
        
        Problem p;
        Search alg;
        
        /* Mirar aquestes creadores que no son ProbDesEstat (Montoya) */
        p = new Problem (board, new ProbDesSuccessorFunction(), new ProbDesGoalTest(), new ProbDesHeuristicFunction());
        alg = new HillClimbingSearch();
        
        SearchAgent agent = new SearchAgent(p, alg);
        
        System.out.print('\n');
        System.out.println("--- LLISTA D'ACCIONS ---");
        System.out.print('\n');
        
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        
        System.out.print('\n');
        System.out.print("temps total: ");
        
        double tempsTotal = 0.0;
        int numSortides = 0;
        for (int u = 0 ; u < board.getEstado().size(); u++) {
			numSortides += board.getNumSortides(u);
			for (int sort = 0; sort < board.getEstado().get(u).size();sort++) {
				tempsTotal += board.getTempsEmpleatSortida(u, sort);
			}
		}
		
		if(numSortides > 0)  tempsTotal += ((numSortides-1)*10)/60.0;
		tempsTotal *= 60; 
		System.out.print(tempsTotal + "\n");
        
    }
    
    private static void printInstrumentation(Properties properties) { //<----- Aquestes funcions ens les donen
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }

    private static void printActions(List actions) { //<-----Aquestes funcions ens les donen | Aquest em sembla que es per SA
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}

                                                    
                                                    
                                                    /* --- Experiment Especial 8 opcional --- */
/* el tema de System.currentTimeMillis es per l'Experiment Especial 8 (opcional, 1 punt extra) --> pàg 6 del pdf, tenim aquesta setmana si el volem fer */
