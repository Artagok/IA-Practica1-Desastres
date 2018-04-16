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
        
        int seed = 1234;
        int nGrups = 100;
        int nCentres = 5;
        int nHeliXCentre = 1;
        int algorisme = 1;
        int heuristic = 1; 
        
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
            System.out.println("Heurístic 1 (temps total) o Heurístic 2 (prioritzar ferits)"); 
            heuristic = reader.nextInt();
        }
        
        ProbDesEstat board = new ProbDesEstat (seed, nGrups , nCentres , nHeliXCentre);
        Problem p;
        Search alg;

        if (algorisme == 1){
            if (heuristic == 1) {
                p = new Problem (board, new ProbDesSuccessorFunction(), new ProbDesGoalTest(), new ProbDesHeuristicFunction());
                alg = new HillClimbingSearch();
            }
            else {
                p = new Problem (board, new ProbDesSuccessorFunction(), new ProbDesGoalTest(), new ProbDesHeuristicFunction2());
                alg = new HillClimbingSearch();
            }
        }
        
        else {
            int maxit = 50;
            int iter = 25;
            int k = 2;
            double l = 0.002;
            
            if (heuristic == 1) {
                p = new Problem (board, new ProbDesSuccessorFunctionSA(), new ProbDesGoalTest(), new ProbDesHeuristicFunction());
                alg =new SimulatedAnnealingSearch(maxit,iter,k,l);
            }
            else {
                p = new Problem (board, new ProbDesSuccessorFunctionSA(), new ProbDesGoalTest(), new ProbDesHeuristicFunction2());
                alg =new SimulatedAnnealingSearch(maxit,iter,k,l);
            }
        }  
        
        double tini = System.currentTimeMillis();
        
        SearchAgent agent = new SearchAgent(p, alg);
        
        if (algorisme==1) printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        board = (ProbDesEstat) alg.getGoalState();
        
        System.out.println();
        System.out.println("---ESTAT FINAL---");
        System.out.println();
            
        for (int i = 0; i < board.getEstado().size(); ++i) {
            System.out.print("Heli " + i + " =>   ");
            board.imprimeixHeli(i);
        }

        ProbDesHeuristicFunction hf = new ProbDesHeuristicFunction();
        double heuristicValue = hf.getHeuristicValue(board);
        
        System.out.println("Temps Total: " + heuristicValue * 60.0);
        System.out.println();
        System.out.println("Temps d'execució: " +(System.currentTimeMillis()-tini)/1000);
        System.out.println();
        
        
    }
    
    private static void printInstrumentation(Properties properties) { 
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }

    private static void printActions(List actions) { 
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}                                                                              
