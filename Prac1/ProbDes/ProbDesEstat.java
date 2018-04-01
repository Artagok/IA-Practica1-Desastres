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
    
    /* --- INICIALITZACIONS --- */
    //inicialitzacio 1:
    public void sol_ini1 () {
        for(int i = 0; i < grupos.size(); i++) {//S'HA DE MIRAR QUAN ES AIXI O grupos=new Grupos(ngrupos, seed)
            estado[0].addLast(i);
            estado[0].addLast(-1);
            estado[0].addLast(-1);
        }
    }	
    //inicialitzacio 2:
    public void sol_ini2 () {    
        int n = estat.size();
        for(int i = 0; i < grupos.size(); i++){
            estado[i%n].addLast(i);
            estado[i%n].addLast(-1);
            estado[i%n].addLast(-1);
        }
    }

    /* --- OPERADORS --- */
    public void moureGrupMHeli (){} //diferent viatge (+afegir viatge)
	public void moureGrupDHeli (){} //1 cop x cada viatge (+ afegir viatge)
	public void swapGrupMHeli (){} //tots amb tots
	//aquestes dos poder podiren ser la mateixa
	public void swapGrupDHeli (){} //tots amb tots
	public void moureViatge (){} //diferent h
	public void swapViatge (){} //diferent h
}