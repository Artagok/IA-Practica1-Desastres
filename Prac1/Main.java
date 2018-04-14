// imporem totes les classes de ProbDes
import ProbDes.*;

//imports de aima
import aima.search.framework.Problem;
import aima.search.framework.GraphSearch;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch; 

//imports de java
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;         
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        int seed = 1912; 
        int nGrups = 100;
        int nCentres = 5;
        int nHeliXCentre = 1;
        int algorisme = 1; 
        
        System.out.println("Select mode: 1 automatic, 2 manual");
        Scanner reader = new Scanner(System.in);  
        int modo = reader.nextInt();
        if (modo == 2){
            System.out.println("Ordre parametres: seed, nGrups, nCentres, nHeliXCentre");
            seed=reader.nextInt();
            nGrups=reader.nextInt();
            nCentres=reader.nextInt();
            nHeliXCentre=reader.nextInt();
            System.out.println("Algoritme de busqueda: 1 HC, 2 SA");
            algorisme=reader.nextInt();
        }
        
        ProbDesEstat board = new ProbDesEstat (seed, nGrups , nCentres , nHeliXCentre);
        Problem p;
        Search alg;

        if (algorisme == 1){ //HC
            p = new Problem (board, new ProbDesSuccessorFunction(), new ProbDesGoalTest(), new ProbDesHeuristicFunction());
            alg = new HillClimbingSearch();
        }
        else {
            int maxit=10; 
            int iter=maxit/10; 
            int k=5;
            double l=0.002;
            p = new Problem (board, new ProbDesSuccessorFunctionSA(), new ProbDesGoalTest(), new ProbDesHeuristicFunction());
            alg =new SimulatedAnnealingSearch(maxit,iter,k,l);
        }
    
        SearchAgent agent = new SearchAgent(p, alg);
        
        if (algorisme==1) printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        board = (ProbDesEstat) alg.getGoalState();
        
        System.out.println();
        System.out.println("---LLISTA D'ACCIONS---");
        System.out.println();
       
        for (int i = 0; i < board.getEstado().size(); ++i) {
            System.out.print("Heli " + i + " =>   ");
            board.imprimeixHeli(i);
        }
        
        ProbDesHeuristicFunction hf = new ProbDesHeuristicFunction();
        double heuristicValue = hf.getHeuristicValue(board);
        
        System.out.println("Temps Total: " + heuristicValue * 60.0);
        System.out.println();
        
        /*
        System.out.print('\n');
                System.out.print("temps total: ");
                
                double tempsTotal = 0.0;
                int numSortides = 0;
                for (int u = 0 ; u < PGBoard.getEstado().size(); u++) {
                    numSortides += PGBoard.getNumSortides(u);
                    for (int sort = 0; sort < PGBoard.getEstado().get(u).size();sort++) {
                        tempsTotal += PGBoard.getTempsEmpleatSortida(u, sort);
                    }
                }
                
                if(numSortides > 0)  tempsTotal += ((numSortides-1)*10)/60.0;
                tempsTotal *= 60; 
                System.out.print(tempsTotal + "\n");
                */
        
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
