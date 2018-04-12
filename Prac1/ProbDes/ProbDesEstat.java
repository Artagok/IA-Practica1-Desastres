package ProbDes;

/* ------ IMPORTS ------ */
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

	public class SortidaEsborrada {
		public boolean esborrada_;	// _ = ? --> esborrada? : Determina si s'ha esborrat la sortida
		public int sortidaEsborrada; // conte la sortida esborrada, ni mirar aquest camp si esborrada_ es fals

		public SortidaEsborrada() {
			esborrada_ = false;
			sortidaEsborrada = -1;
		}
	}

	public class Sortida {
		public int [] grupsRecollits = new int[3];
		public double tempsEmpleat;
		public double kmViatjats;

		public Sortida() {
			grupsRecollits = new int[]{-1,-1,-1};
			tempsEmpleat = 0.0;
			kmViatjats = 0.0;
		}
		
		//Creadora per còpia, retorna una Sortida igual que la Sortida s
		public Sortida(Sortida s) {
			this.grupsRecollits = s.grupsRecollits.clone();
			this.tempsEmpleat = s.tempsEmpleat;
			this.kmViatjats = s.kmViatjats;
		}
		
		//Retorna 0 | 1 | 2 depenent de en quina posicio de grupsRecollits de la Sortida es trobi el Grup grup
		public int indexGrupDinsDeSortida (int grup) {
			if (grupsRecollits[0] == grup) return 0;
			if (grupsRecollits[1] == grup) return 1;
			if (grupsRecollits[2] == grup) return 2;
			System.out.println("grup no pertany a la sortida");
			return -1; //en principi no s'hi ha d'arribar mai. Cal estar segur que el grup es troba a la Sortida
		}
	}

	/* ------ CONSTANTS ------ */
	public static final int MIDA_MAPA = 50;
	public static final int MIDA_MAX_GRUP = 12;
	public static final int MAX_PERS_HELICOPTER = 15;
	public static final double COOLDOWN_HELICOPTER = 10; // 10 minuts de descansito
	public static final double VELOCITAT_HELICOPTER = 100; // 100km/h

	/* ------ VARIABLES DE L'ESTAT ------ */
	private static Centros centros;
	private static Grupos grupos;
	// i = helicopter | l'helicopter i pertany al centre (i/#HELIxCENTRE )
	// j = array de Sortides
	// la matriu pot ser irregular, exemple: h0 -> 3 sortides | h1 -> 1 sortida
	// He triat ArrayList aixi de primeres perque te funcions de add i remove per index bastant guapes
	// Llista de metodes que te ArrayList: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
	// Si veiem que no cunde tornem a int [][]
	private ArrayList < ArrayList < Sortida > > estado;
	
	/* ------ CONSTRUCTORES ------ */
	public ProbDesEstat (int seed, int g, int c, int h) {
		centros = new Centros (c,h,seed);
		grupos = new Grupos (g,seed);
		System.out.println("seed: " + seed);
		System.out.println("nGrups: " + g);
		System.out.println("nCentres: " + c);
		System.out.println("nHelicoptersXCentre: " + h);
		System.out.println("--------------------------------");
		estado = new ArrayList < ArrayList < Sortida > > (c*h);
		sol_ini2(c,h);
	}
	
	/* Constructora per copia */
	public ProbDesEstat(ProbDesEstat estatOriginal) {
		
		/* Copiem tots els centres a centros */ /*Perdona Java, sóc retrassat, eren variables static !!! */
   		/*
   		int n = estatOriginal.getNumCentres();
		System.out.println(centros.size());
		centros = new ArrayList<Centro> (0);
		for (int i = 0; i < estatOriginal.getNumCentres(); ++i) {
		    Centro centreOr = estatOriginal.getCentre(i);
		    Centro centreAux = new Centro(centreOr.getCoordX(),centreOr.getCoordY(),centreOr.getNHelicopteros());
			centros.add(centreAux);
		} */

		 //System.out.println("He acabat de copiar centros");
		/* Copiem tots els grups a grupos */
		/*for (int i = 0; i < estatOriginal.getNumGrups(); ++i) {
			Grupo grupOr = estatOriginal.getGrup(i);
			Grupo grupAux = new Grupo(grupOr.getCoordX(),grupOr.getCoordY(),grupOr.getNPersonas(),grupOr.getPrioridad());
			grupos.add(grupAux);
		} */
		
		/* Copiem tota la matriu de Sortides estado */
		estado = new ArrayList < ArrayList < Sortida > > (0);
		for (int i = 0; i < estatOriginal.getNHelicopters(); ++i) {
			ArrayList < Sortida > inner = new ArrayList < Sortida > (0);
			for (int j = 0; j < estatOriginal.getNumSortides(i); ++j) {
				Sortida s = new Sortida(estatOriginal.getSortida(i,j));
				inner.add(s);
			}
			estado.add(inner);
		}
	}
	
	/* ------ SOLUCIONS INICIALS ------ */
	// solucio inicial 1:
	public void sol_ini1 (int c, int h) {
		
		int n = c * h;
		for (int i = 0; i < n; ++i) {
		    estado.add(new ArrayList<Sortida>(0));  
		}
		// Fora del bucle pq sempre s'agafen les coordenades del centre 0
		int XC = centros.get(0).getCoordX();
		int YC = centros.get(0).getCoordY();
		System.out.println(XC);
		System.out.println(YC);
		for(int i = 0; i < grupos.size(); i++) {//S'HA DE MIRAR QUAN ES AIXI O grupos=new Grupos(ngrupos, seed)
			Sortida s = new Sortida();
	
			int XG = grupos.get(i).getCoordX();
			int YG = grupos.get(i).getCoordY();
			int np = grupos.get(i).getNPersonas();
			int prio = grupos.get(i).getPrioridad();
			System.out.println(XG);
			System.out.println(YG);
			System.out.println(np);
			System.out.println(prio);
			double distancia = Math.sqrt((Math.pow(XG - XC, 2)) + (Math.pow(YG - YC, 2))); //distancia en km
			System.out.println(distancia);
			double temps = ((distancia * 2 )/ VELOCITAT_HELICOPTER);	//TEMPS en h que es triga en anar i tornar al lloc, sense recollir a ningu
			if(prio == 1) temps += (np * 2)/60.0;	//TEMPS EN H QUE ES TRIGA EN RECOLLIR LES PERSONES D'UN GRUP de prioritat 1
			else temps += np/60.0;				//TEMPS EN H QUE ES TRIGA EN RECOLLIR LES PERSONES D'UN GRUP de prioritat 2
			s.grupsRecollits = new int[] {i,-1,-1}; //s.grupsRecollits[0]=i
			s.tempsEmpleat = temps;
			s.kmViatjats = distancia + distancia; //dist anada + dist tornada
		
			estado.get(0).add(s);	//estado[0].push_back(s)
		}
	}	
	//solucio inicial 2:
	public void sol_ini2 (int c, int h) {    
		
	
		int n = c * h;
		
		for (int i = 0; i < n; ++i) {
		    estado.add(new ArrayList<Sortida>(0));  
		}
		
		for(int i = 0; i < grupos.size(); i++){
		
			Sortida s = new Sortida();
			//System.out.println(estado.size());
			//System.out.println(i);
			int XC = centros.get(i%c).getCoordX();
			int YC = centros.get(i%c).getCoordY();
			
			int XG = grupos.get(i).getCoordX();
			int YG = grupos.get(i).getCoordY();
			int np = grupos.get(i).getNPersonas();
			int prio = grupos.get(i).getPrioridad();
			
			double distancia = Math.sqrt((Math.pow(XG - XC, 2)) + (Math.pow(YG - YC, 2)));
			
			double temps = (distancia / VELOCITAT_HELICOPTER)*2;	//TEMPS en h que es triga en anar i tornar al lloc, sense recollir a ningu
			if(prio == 1) temps += (np * 2)/60.0;	//TEMPS EN H QUE ES TRIGA EN RECOLLIR LES PERSONES D'UN GRUP de prioritat 1
			else temps += np/60.0;				//TEMPS EN H QUE ES TRIGA EN RECOLLIR LES PERSONES D'UN GRUP de prioritat 2
			
			s.grupsRecollits = new int[] {i,-1,-1};
			s.tempsEmpleat = temps;
			s.kmViatjats = distancia + distancia; //dist anada + dist tornada
			
			estado.get(i%n).add(s);
		}
		//System.out.println("Surto del for de ini2");
	}

 
	/* ------ OPERADORS ------ */
   
	public void moureGrupMHeli (int heli, int grup, int sortida) { // Moure Grup grup de Helicpoter heli a la Sortida sortida dins del mateix Helicopter heli
		int indexSortidaOrigen = getNumSortida(heli,grup);
		int indexGrupDinsDeS = (estado.get(heli).get(indexSortidaOrigen)).indexGrupDinsDeSortida(grup);
		/* Anulem el grup que movem a la Sortida Origen */
		estado.get(heli).get(indexSortidaOrigen).grupsRecollits[indexGrupDinsDeS] = -1;
		/* Escrivim el grup a la Sortida destí */
		if		(estado.get(heli).get(sortida).grupsRecollits[0] == -1) { estado.get(heli).get(sortida).grupsRecollits[0] = grup; }
		else if (estado.get(heli).get(sortida).grupsRecollits[1] == -1)	{ estado.get(heli).get(sortida).grupsRecollits[1] = grup; }
		else															{ estado.get(heli).get(sortida).grupsRecollits[2] = grup; }
	}
	
	public void moureGrupDHeli (int heli1, int heli2, int grup, int sortida2) { // Mou el Grup grup del heli1 a la sortida2 del heli2 | les comprovacions prèvies ja estàn fetes, s'ha de poder fer
		int indexSortidaOrigen = getNumSortida(heli1,grup);
		int indexGrupDinsDeS = (estado.get(heli1).get(indexSortidaOrigen)).indexGrupDinsDeSortida(grup);
		/* Anulem el grup que movem a la Sortida Origen de heli1*/
		estado.get(heli1).get(indexSortidaOrigen).grupsRecollits[indexGrupDinsDeS] = -1;
		/* Escrivim el grup a la Sortida destí de heli2 */
		if		(estado.get(heli2).get(sortida2).grupsRecollits[0] == -1)	{ estado.get(heli2).get(sortida2).grupsRecollits[0] = grup; }
		else if (estado.get(heli2).get(sortida2).grupsRecollits[1] == -1)	{ estado.get(heli2).get(sortida2).grupsRecollits[1] = grup; }
		else																{ estado.get(heli2).get(sortida2).grupsRecollits[2] = grup; }
	}
	
	public void swapGrupMHeli (int heli, int g1, int g2) { //swap de g1 per g2 (i de g2 per g1) | les comprovacions ja estàn fetes
		int indexSortida1 = getNumSortida(heli,g1);
		int indexSortida2 = getNumSortida(heli,g2);
		int indexGrup1 = (estado.get(heli).get(indexSortida1)).indexGrupDinsDeSortida(g1);
		int indexGrup2 = (estado.get(heli).get(indexSortida2)).indexGrupDinsDeSortida(g2);
		estado.get(heli).get(indexSortida1).grupsRecollits[indexGrup1] = g2;
		estado.get(heli).get(indexSortida2).grupsRecollits[indexGrup2] = g1;
	} 
	
	public void swapGrupDHeli (int heli1, int heli2, int g1, int g2) { //swap de g1 per g2 (i de g2 per g1), g1 era de heli1 i g2 de heli2, (despres del swap no) | les comprovacions ja estàn fetes
		int indexSortida1 = getNumSortida(heli1,g1); // index de la sortida on es troba g1 a heli1
		int indexSortida2 = getNumSortida(heli2,g2); // "" heli2 
		Sortida sx1 = (estado.get(heli1).get(indexSortida1));
		/* System.out.println("Ensenya sortida heli1, index sortida= " + indexSortida1);
		for (int k=0; k<3; k++){
			System.out.print(sx1.grupsRecollits[k]+ " ");
		}
		System.out.println("Grup heli1 " + g1);
        */
		Sortida sx2 = (estado.get(heli2).get(indexSortida2));
		
		/* System.out.println("Ensenya sortida heli2");
		for (int k=0; k<3; k++){
			System.out.print(sx2.grupsRecollits[k]+ " ");
		}
		System.out.println("Grup heli1 " + g2);
        */

		int indexGrup1 = (estado.get(heli1).get(indexSortida1)).indexGrupDinsDeSortida(g1); 
		int indexGrup2 = (estado.get(heli2).get(indexSortida2)).indexGrupDinsDeSortida(g2); // no(g2 pertany a la sortida)
		estado.get(heli1).get(indexSortida1).grupsRecollits[indexGrup1] = g2;
		estado.get(heli2).get(indexSortida2).grupsRecollits[indexGrup2] = g1;
	}
	
	public void moureSortida (int heli1, int sortida, int heli2) { //sortida era d'heli1, ara serà d'heli2
		// Copiem en una aux la sortida de heli1
		Sortida sortidaAux = new Sortida(estado.get(heli1).get(sortida));
		// Esborrem la sortida al vector de sortides de heli1
		estado.get(heli1).remove(sortida);
		// Si la sortida te grups de prio=1 l'afegirem pel principi, si no l'afegirem pel final (al vector de sortides de heli2)
		if (sortidaTePrio1(sortidaAux)) {
			estado.get(heli2).add(0,sortidaAux);
		}
		else {
			estado.get(heli2).add(sortidaAux);
		}
	} 
	
	public void swapSortida (int heli1, int sortida1, int heli2, int sortida2) { //sortida1 de heli1 & sortida2 de heli2 --> sortida1 de heli2 & sortida2 de heli1
        
        //System.out.println("entra al swap sortida");
		Sortida sortidaAux1 = new Sortida(estado.get(heli1).get(sortida1));
		Sortida sortidaAux2 = new Sortida(estado.get(heli2).get(sortida2));
                /*
                System.out.print("heli1 abans swap: ");
                imprimeixHeli(heli1);
                System.out.print("heli2 abans swap: ");
				imprimeixHeli(heli2);
				*/
		estado.get(heli1).remove(sortida1);
                //System.out.print("heli1 despres remove sortida1: ");
                //imprimeixHeli(heli1);
                //System.out.print("heli2 despres remove sortida1: ");
                //imprimeixHeli(heli2);
		estado.get(heli2).remove(sortida2);
                //System.out.print("heli2 despres remove sortida2: ");
				//imprimeixHeli(heli2);
		
		if (sortidaTePrio1(sortidaAux1)) {
			estado.get(heli2).add(0,sortidaAux1);
		}
		else {
			estado.get(heli2).add(sortidaAux1);
		}
		if (sortidaTePrio1(sortidaAux2)) {
			estado.get(heli1).add(0,sortidaAux2);
		}
		else {
			estado.get(heli1).add(sortidaAux2);
		}
		/*
		System.out.println("	  ------------------------------");
		System.out.print("heli1 despr swap: ");	imprimeixHeli(heli1);
		System.out.print("heli2 despr swap: ");	imprimeixHeli(heli2);
		System.out.println("surt del swap sortida");
		System.out.print('\n');
		*/
	} 






	   /* ------ GETTERS ------ */
	  
	public int getNHelicopters(){
		return  estado.size(); 
	}
	
	public ArrayList<Integer> getGrupsHeli (int heli){//ArrayList d'id de grups que transporta l'helicopter heli
		ArrayList<Integer> idG = new ArrayList<Integer>(0);
		for(int i = 0; i < getNumSortides(heli); i++) {	//per totes les sortides del heli
		
			Sortida Saux = estado.get(heli).get(i);	//ho he posat aqui, abans estava despres del 2n for /// POTSER ESTA MAL, PERO DIRIA QUE ES AIXI
			for(int j = 0; j < 3; ++j){
				int x = Saux.grupsRecollits[j];
				if (x != -1) idG.add(new Integer(x));
			}
		}
		return idG;
	}
	
	public ArrayList<Integer> getViatgesHeli (int heli){//ArrayList d'indexs de les sortides que fa l'helicopter heli | si en fa 3 : 0 1 2
		int numS =  getNumSortides(heli);
		ArrayList<Integer> indexSortides = new ArrayList<Integer>(numS);
		for(int i = 0; i < numS; i++) {
			indexSortides.add(i);     //esta inicialitzant el ArrayList amb el seu index. El que no se es si el set funciona quan ArrayList no te valors assignats
		}
		return indexSortides;
	}
	
	public int getNumSortida (int heli, int grup){ //retorna l'index de la sortida de l'heli on apareix el grup | l'hauria de trobar si o si
		int nS = getNumSortides(heli);
		int i;
		for(i = 0; i < nS; i++){
			Sortida Saux = estado.get(heli).get(i);

			if (grup == Saux.grupsRecollits[0]) return i;
			if (grup == Saux.grupsRecollits[1]) return i;
			if (grup == Saux.grupsRecollits[2]) return i;
			
		}
		/*System.out.println("heli:" + heli);
		imprimeixHeli(heli);
		System.out.println("no s'ha trobat el grup " + grup); */
		return -1;
	}
	
	/* Retorna #Sortides de l'Helicopter heli */
	public int getNumSortides (int heli) {
		return estado.get(heli).size();
	}
	
	/* Retorna #centres */ 
	public int getNumCentres () {
		return centros.size();
	}
	
	/* Retorna el Centro a la posicio index de Centros*/
	public Centro getCentre (int index) {
		return centros.get(index);
	}
	
	/* Retorna #grups */
	public int getNumGrups () {
		return grupos.size();
	}
	
	/* Retorna el Grup a la posicio index de Grupos */
	public Grupo getGrup (int index) {
		return grupos.get(index);
	}
	
	/* Retorna la Sortida a la posicio j de l'helicopter a la posicio i a estado */
	public Sortida getSortida (int i, int j) {
		return estado.get(i).get(j);
	}

	public void imprimeixHeli(int heli){
		for (int s = 0; s < getNumSortides(heli); s++){
			if (estado.get(heli).get(s).grupsRecollits[0] == -1) System.out.print("_ ");
			else System.out.print(estado.get(heli).get(s).grupsRecollits[0] + " ");
			
			if (estado.get(heli).get(s).grupsRecollits[1] == -1) System.out.print("_ ");
			else System.out.print(estado.get(heli).get(s).grupsRecollits[1] + " ");
			
			if (estado.get(heli).get(s).grupsRecollits[2] == -1) System.out.print("_   ");
			else System.out.print(estado.get(heli).get(s).grupsRecollits[2] + "  ");
		}
		System.out.println();
	}
	
	public ArrayList < ArrayList < Sortida > > getEstado () {
		return estado;
	}

	
	public double getTempsEmpleatSortida (int heli, int sortida){
		return estado.get(heli).get(sortida).tempsEmpleat;
	}
	
	
	/* ------ AUXILIARS ------ */
	/* Determina si la Sortida s té algun grup de prioritat 1*/
	public boolean sortidaTePrio1 (Sortida s) {
		for (int i = 0; i < 3; i++) {
			int grupIndex = s.grupsRecollits[i];
			if (grupIndex != -1) {
				if (grupos.get(grupIndex).getPrioridad() == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean noEsViolenRestriccions1(int heli, int g, int sortida){ //té algun -1 | no viola rollo mes de 3 grups, mes de 15 persones, etc (lo del enunciat)
		//System.out.println("entra a R1");
        //imprimeixHeli(heli);
		int [] grups = estado.get(heli).get(sortida).grupsRecollits;
		boolean hiHaEspai = false;
		for (int i = 0 ; i < 3; i++){
			if (grups[i] == -1) hiHaEspai = true;
		}
		if (!hiHaEspai) return false;
        
		int NpersonesG = grupos.get(g).getNPersonas();
		int NpersonesSortida = 0;
		for (int i = 0; i < 3; i++){
			if (grups[i] != -1) NpersonesSortida += grupos.get(grups[i]).getNPersonas();
		}
		//System.out.println("acaba a R1");
        //imprimeixHeli(heli);
		
		if (NpersonesG + NpersonesSortida > MAX_PERS_HELICOPTER) return false;
		else return true;
	}
	
	public boolean noEsViolenRestriccions2(int heli, int g1, int g2){ //la diferència és que en aquest es canvia el g1 pel g2. Comprova si fent aquest canvi es es viola alguna restriccio en la sortida on estava g1 (ara g2)

        if (getNumSortida(heli,g1) == -1) return false;
        
		int personesg1 = grupos.get(g1).getNPersonas();
		int personesg2 = grupos.get(g2).getNPersonas();
		int personesSortida = 0;
		int s = getNumSortida(heli,g1);
		for (int i = 0; i < 3; i++){
            if (estado.get(heli).get(s).grupsRecollits[i] != -1){
                personesSortida += grupos.get(estado.get(heli).get(s).grupsRecollits[i]).getNPersonas();
            }
        }
        if ((personesSortida - personesg1 + personesg2) <= 15) return true;
        else return false;
	}
	
	   public void reordena(int heli, int sortida, SortidaEsborrada sE){ //posa la millor combinació dels grups de la sortida, si no hi ha cap grup elimina la sortida
		int [] indexGrups = new int[3];
		int [] grupsRescatats = new int[3];
		//System.out.println("------ Entro a reordena ------");
		//imprimeixHeli(heli); 
		//System.out.println("Sortida a ordernar:" + sortida);
		indexGrups = estado.get(heli).get(sortida).grupsRecollits;
		int count = 0;
		int apuntador = 0;
		int idCentre = heli/centros.get(0).getNHelicopteros(); //agafar el centre a partir del id helicopter!! (i/#HELIxCENTRE )

		int XC = centros.get(idCentre).getCoordX();
		int YC = centros.get(idCentre).getCoordY();

		for(int k=0;k<3;k++){
		    if(indexGrups[k] != -1) {
			count++;
			grupsRescatats[apuntador] = indexGrups[k];
			apuntador++;
		    }
		}
			// Si esborrem cal avisar al següent reordena l'index de la sortida que hem esborrat!
		if(count == 0) { 
			estado.get(heli).remove(sortida); 
			sE.esborrada_ = true; 
			sE.sortidaEsborrada = sortida; 
		}
		else { 
			int XG1 = grupos.get(grupsRescatats[0]).getCoordX();
			int YG1 = grupos.get(grupsRescatats[0]).getCoordY();
			double km;
			int np;
			int prio;
			double dist;
			double temps;
			if(count == 1) {
				estado.get(heli).get(sortida).grupsRecollits = new int[]{grupsRescatats[0],-1,-1};
				dist = (Math.sqrt (Math.pow(XG1-XC,2) + Math.pow(YG1-YC,2)))*2;
				temps = dist/VELOCITAT_HELICOPTER;
				np = grupos.get(grupsRescatats[0]).getNPersonas();
				prio = grupos.get(grupsRescatats[0]).getPrioridad();
				if(prio == 1) temps += (np * 2)/60.0;
				else temps += np/60.0;
				estado.get(heli).get(sortida).tempsEmpleat = temps;
				estado.get(heli).get(sortida).kmViatjats = dist;			
			}
			else {
				int XG2 = grupos.get(grupsRescatats[1]).getCoordX();
				int YG2 = grupos.get(grupsRescatats[1]).getCoordY();

				if(count == 2) {
					estado.get(heli).get(sortida).grupsRecollits = new int[]{grupsRescatats[0],grupsRescatats[1],-1};
					dist = (Math.sqrt (Math.pow(XG1-XC,2) + Math.pow(YG1-YC,2))) + (Math.sqrt (Math.pow(XG2-XG1,2) + Math.pow(YG2-YG1,2))) + (Math.sqrt (Math.pow(XC-XG2,2) + Math.pow(YC-YG2,2)));
					temps = dist/VELOCITAT_HELICOPTER;
					for(int j=0;j<2;++j) {
						prio = grupos.get(grupsRescatats[j]).getPrioridad();
						np = grupos.get(grupsRescatats[j]).getNPersonas();
						if (prio == 1) temps += (np * 2)/60;
						else temps += np/60;
					}
					estado.get(heli).get(sortida).tempsEmpleat = temps;
					estado.get(heli).get(sortida).kmViatjats = dist;

				}
				else {	
					int XG3 = grupos.get(grupsRescatats[2]).getCoordX();
					int YG3 = grupos.get(grupsRescatats[2]).getCoordY();

					double op1 = quantskm (XC, YC, XG1, YG1, XG2, YG2, XG3, YG3);
					double op2 = quantskm (XC, YC, XG1, YG1, XG3, YG3, XG2, YG2);
					double op3 = quantskm (XC, YC, XG2, YG2, XG1, YG1, XG3, YG3);
					double op4 = quantskm (XC, YC, XG2, YG2, XG3, YG3, XG1, YG1);
					double op5 = quantskm (XC, YC, XG3, YG3, XG1, YG1, XG2, YG2);
					double op6 = quantskm (XC, YC, XG3, YG3, XG2, YG2, XG1, YG1);

					double bestop = minimkm(op1,op2,op3,op4,op5,op6);
					int [] grupsordreFinal = new int[3];

					if (bestop == op1)  grupsordreFinal = new int[]{grupsRescatats[0],grupsRescatats[1],grupsRescatats[2]};
					else if (bestop == op2)  grupsordreFinal = new int[]{grupsRescatats[0],grupsRescatats[2],grupsRescatats[1]};
					else if (bestop == op3)  grupsordreFinal = new int[]{grupsRescatats[1],grupsRescatats[0],grupsRescatats[2]};
					else if (bestop == op4)  grupsordreFinal = new int[]{grupsRescatats[1],grupsRescatats[2],grupsRescatats[0]};
					else if (bestop == op5)  grupsordreFinal = new int[]{grupsRescatats[2],grupsRescatats[0],grupsRescatats[1]};
					else if (bestop == op6)  grupsordreFinal = new int[]{grupsRescatats[2],grupsRescatats[1],grupsRescatats[0]};

					estado.get(heli).get(sortida).grupsRecollits = grupsordreFinal;
					temps = bestop/VELOCITAT_HELICOPTER;
					for(int j=0;j<3;++j) {
						prio = grupos.get(grupsordreFinal[j]).getPrioridad();
						np = grupos.get(grupsordreFinal[j]).getNPersonas();
						if (prio == 1) temps += (np * 2)/60;
						else temps += np/60;
					}
					estado.get(heli).get(sortida).tempsEmpleat = temps;
					estado.get(heli).get(sortida).kmViatjats = bestop;
				}
			}   	
		}
        
	}
	
	public double quantskm (int xc, int yc, int x1, int y1, int x2, int y2, int x3, int y3) { //calcula quants km triga un helicopter en sortir del centre, recollir els grups en ordre 1,2,3 i tornar
		
		// del centre al primer
		double kmTotal = Math.sqrt (Math.pow(x1-xc,2) + Math.pow(y1-yc,2));
		
		// del primer al segon
		kmTotal += Math.sqrt (Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
		
		// del segon al tercer
		kmTotal += Math.sqrt (Math.pow(x3-x2,2) + Math.pow(y3-y2,2));
		
		// del tercer al centre
		kmTotal += Math.sqrt (Math.pow(xc-x3,2) + Math.pow(yc-y3,2));
		
		return kmTotal;
	}
	
	public double minimkm (double op1, double op2, double op3, double op4, double op5, double op6){ //simplement compara qui te un valor mes baix i el retorna
		double minim;
		minim = Math.min(op1,op2);
		minim = Math.min(minim, op3);
		minim = Math.min(minim, op4);
		minim = Math.min(minim, op5);
		minim = Math.min(minim, op6);
		return minim;
	}
}


/*
comptar #-1
	--> reordenar 1 --> busques quin != -1 i fas Sortida.grupsRecollits = {x,-1,-1} 
	--> reordenar 2 --> buscar els 2 que son != -1 
					--> valor(x,y,-1)
					--> valor(y,x,-1)
						--> Sortida.grupsRecollits = "best"({x,y,-1},{y,x,-1})
		
	--> reordenar 3
		--> 6 combinacions
			best(x,y,z
				 x,z,y
				 y,x,z
				 y,z,x
				 z,x,y
				 z,y,x
*/

