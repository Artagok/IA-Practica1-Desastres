package ProbDes;

/* --- IMPORTS --- */
/* Imports Classes Desastres */
import IA.Desastres.Grupos;
import IA.Desastres.Grupo;
import IA.Desastres.Centros;
import IA.Desastres.Centro;
/* Imports de Java */
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ProbDesEstat {
    
    //Representa una sortida concreta d'un helicopter
    //grupsRecollits es un array de ints de mida 3 on el valor es el num de grup [0..nGrups-1]
        // o be -1 si sobren espais en l'array de 3, -1 indica grup "null"
    //tempsEmpleat sera la suma total d'aquesta sortda
        //tempsEmpleat = tempsDesplaçament + tempsRecollidaGrups (10 min de descans els sumarem a un altre lloc)
    //kmViatjats es la suma total del recorregut des que surt del centre fins que torna
    public class Sortida {
        public int [] grupsRecollits = new int[3];
        public double tempsEmpleat;
        public double kmViatjats;

        public Sortida() {
            grupsRecollits = new int[]{-1,-1,-1};
            tempsEmpleat = 0.0;
            kmViatjats = 0.0;
        }
    }

    /* --- CONSTANTS --- */
    public static final int MIDA_MAPA = 50;
    public static final int MIDA_MAX_GRUP = 12;
    public static final int MAX_PERS_HELICOPTER = 15;
    public static final double COOLDOWN_HELICOPTER = 10; // 10 minuts de descansito
    public static final double VELOCITAT_HELICOPTER = 100; // 100km/h

    /* --- VARIABLES DE L'ESTAT --- */
    private static Centros centros;
    private static Grupos grupos;
    // i = helicopter | l'helicopter i pertany al centre (i/#centres)
    // j = array de Sortides
    // la matriu pot ser irregular, exemple: h0 -> 3 sortides | h1 -> 1 sortida
    // He triat ArrayList aixi de primeres perque te funcions de add i remove per index bastant guapes
    // Llista de metodes que te ArrayList: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
    // Si veiem que no cunde tornem a int [][]
    private ArrayList < ArrayList < Sortida > > estado;
    
    
    public ProbDesEstat (int seed, int g, int c, int h){
		centros = new Centros (c,h,seed);
		grupos = new Grupos (g,seed);
		
		//estado = new int [c*h][];   //ES POT FER AMB centros.size() i centros.get(0).NHelicopters() o algo aixi, pero teoricament es el mateix
   
		//maybe un vector d'helicopters ens podria anar bé
    }
    
    
    /* --- SOLUCIONS INICIALS --- */
    // solucio inicial 1:
   public void sol_ini1 () {
        for(int i = 0; i < grupos.size(); i++) {//S'HA DE MIRAR QUAN ES AIXI O grupos=new Grupos(ngrupos, seed)
			Sortida s;
	
			int XG = grupos.get(i).getCoordX();
			int YG = grupos.get(i).getCoordY();
			int np = grupos.get(i).getNPersonas();
			int prio = grupos.get(i).getPrioridad();
			
			int XC = centros.get(0).getCoordX();
			int YC = centros.get(0).getCoordY();
			
			double distancia = Math.sqrt((Math.pow(XG - XC, 2)) + (Math.pow(YG - YC, 2))); //distancia en km
		
			double temps = distancia / VELOCITAT_HELICOPTER;	//TEMPS en h que es triga en anar i tornar al lloc, sense recollir a ningu
			if(np == 1) temps += (np * 2)/60;	//TEMPS EN H QUE ES TRIGA EN RECOLLIR LES PERSONES D'UN GRUP de prioritat 1
			else temps += np/60;				//"" 2
			
			s.grupsRecollits.set(0, i); //s.grupsRecollits[0] = i
			s.tempsEmpleat = temps;
			s.kmViatjats = distancia;
		
			estado.get(0).add(s);	//estado[0].push_back(s)
        }
    }	
    //solucio inicial 2:
    public void sol_ini2 () {    
        int n = estat.size();
        for(int i = 0; i < grupos.size(); i++){
            estado[i%n].addLast(i);
            estado[i%n].addLast(-1);
            estado[i%n].addLast(-1);
        }
    }

 
 
 
 

    /* --- OPERADORS --- */
   
    public void moureGrupMHeli (int heli, int grup, int sortida) { //Moure Grup grup de Helicpoter heli a la Sortida sortida dins del mateix Helicopter heli
        
    }
    
	public void moureGrupDHeli (int heli1, int heli2, int g1, int sortida2){ //mou el grup g1 del heli1 a la sortida2 del heli2 | les comprovacions prèvies ja estàn fetes, s'ha de poder fer
	    
	}
	
	public void swapGrupMHeli (int heli, int g1, int g2){ //swap de g1 per g2 (i de g2 per g1) | les comprovacions ja estàn fetes
	    
	} 
	
	public void swapGrupDHeli (int heli1, int heli2, int g1, int g2){ //swap de g1 per g2 (i de g2 per g1), g1 era de heli1 i g2 de heli2, (despres del swap no) | les comprovacions ja estàn fetes
	    
	} 
	
	public void moureSortida (int heli1, int sortida1, int heli2){ //sortida1 era d'heli1, ara serà d'heli2
	    
	} 
	
	public void swapSortida (int heli1, int sortida1, int heli2, int sortida2){ //sortida1 era d'heli2 i sortida2 d'heli2, ara sortida1 d'heli2 i sortida2 d'heli1
	    
	} 
	

	
	
	
	
	   /* --- GETTERS --- */
	  
	public int getNHelicopters(){
	    return  estado.size(); 
	}
	
	public ArrayList<int> getGrupsHeli (int heli){//ArrayList d'id de grups que transporta l'helicopter heli
		ArrayList<int> idG = new ArrayList();
	    for(int i = 0; i < getNumSortides(heli); i++) {	//per totes les sortides del heli
	    
	        Sortida Saux = estado.get(heli).get(i);	//ho he posat aqui, abans estava despres del 2n for /// POTSER ESTA MAL, PERO DIRIA QUE ES AIXI
	    	for(int j = 0; j < 3; ++k){
	    		
	    		int x = Saux.grupsRecollits.get(j);
	    		if (x != -1) idG.add(x);														
	    	}
	    
	    	
	    }
	}
	
	public ArrayList<int> getViatgesHeli (int heli){//ArrayList d'indexs de les sortides que fa l'helicopter heli | si en fa 3 : 0 1 2
	    int numS =  getNumSortides(heli)
	    ArrayList<int> indexSortides = new ArrayList(numS);
	    for(int i = 0; i < numS; i++) {
	        indexSortides.set(i,i);     //esta inicialitzant el ArrayList amb el seu index. El que no se es si el set funciona quan ArrayList no te valors assignats
	    }
	    return indexSortides;
	    
	}
	
	public int getNumSortida (int heli, int grup){ //retorna l'index de la sortida de l'heli on apareix el grup | l'hauria de trobar si o si
	    
	}
	
	public int getNumSortides (int heli){
	    return estado.get(heli).size();
	}
	
	
	
	
	
	
	/* --- AUXILIARS --- */
	
	public bool noEsViolenRestriccions1(int heli, int g, int sortida){ //té algun -1 | no viola rollo mes de 3 grups, mes de nosequantes persones, etc (lo del enunciat)
	
	}
	
	public bool noEsViolenRestriccions2(int heli, int g1, int g2){ //la diferència és que en aquest es canvia el g1 pel g2. Comprova si fent aquest canvi es es viola alguna restriccio en la sortida on estava g1 (ara g2)
	
	}
	
	public void reorndena(int heli, int sortida){ //posa la millor combinació dels grups de la sortida, si no hi ha cap grup elimina la sortida
	    
	}
}
