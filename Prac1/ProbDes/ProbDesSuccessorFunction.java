package ProbDes;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

import ProbDes.ProbDesEstat;
import ProbDes.ProbDesEstat.SortidaEsborrada;

public class ProbDesSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object state){
        
            //System.out.println("Arribo al principi de Successors");
            ArrayList <Successor> retval = new ArrayList<Successor>();
            ProbDesEstat PGBoard = (ProbDesEstat) state;
             ProbDesEstat PGBoardaux;
            
            int numHelicopters = PGBoard.getNHelicopters();
            
              System.out.println("-estat intermigPRINCIPI ORIGINAL-");
             for (int i=0;i<numHelicopters; i++){
                System.out.print("heli" + i + " => ");
                PGBoard.imprimeixHeli(i);
             }
             System.out.println();
            
            for (int heli = 0; heli < numHelicopters; heli++) {
    
                ArrayList<Integer> grups = PGBoard.getGrupsHeli(heli); //<----- id dels grups
                for (int g = 0; g < grups.size(); g++){
                    
                    int sortida_origen = PGBoard.getNumSortida(heli,grups.get(g));
                    int num_sortides = PGBoard.getNumSortides(heli);
                
                
                    for (int sortida = 0; sortida < num_sortides; sortida++){
                    
                        if (PGBoard.noEsViolenRestriccions1(heli,g,sortida) && sortida_origen != sortida) {
                           
                              System.out.println("-estat intermigORIGINAL despres getters-");
                                for (int i=0;i<numHelicopters; i++){
                                    System.out.print("heli" + i + " => ");
                                    PGBoard.imprimeixHeli(i);
                                }
                                System.out.println();
                                
                            PGBoardaux = new ProbDesEstat(PGBoard);
                            ProbDesEstat.SortidaEsborrada sE = PGBoardaux.new SortidaEsborrada(); // false,-1
                            PGBoardaux.moureGrupDHeli(heli,heli,grups.get(g),sortida); //int int int
                            PGBoardaux.reordena(heli,sortida_origen,sE);

                            

                            if (sE.esborrada_ && sortida >= sE.sortidaEsborrada) {
                                PGBoardaux.reordena(heli,sortida-1,sE); //sortida destí (-1 pq s'ha esborrat una que estava a la seva esquerra)
                            }
                            else {
                                PGBoardaux.reordena(heli,sortida,sE); //sortida destí
                            }

                            

                            retval.add (new Successor ("Moure Grup Mateix Helicopter", PGBoardaux)); //falta el cas d'afegir una nova sortida amb un sol grup
                            
                            System.out.println("-estat MOURE GRUP MATEIX HELI " + heli + " AUX");
                                    for (int ii=0;ii<numHelicopters; ii++){
                                        System.out.print("heli" + ii + " => ");
                                        PGBoardaux.imprimeixHeli(ii);
                                    }
                                    
                                       System.out.println("-estat intermigORIGINAL despres operador-");
                                    for (int i=0;i<numHelicopters; i++){
                                        System.out.print("heli" + i + " => ");
                                        PGBoard.imprimeixHeli(i);
                                    }
                                    System.out.println();
                                    }
                    }
                }
            } 
            System.out.println();
            
            System.out.println("-estat intermig ORIGINAL-");
             for (int i=0;i<numHelicopters; i++){
                System.out.print("heli" + i + " => ");
                PGBoard.imprimeixHeli(i);
             }
             System.out.println();
             
            //2n operador encara no funciona
            /*
            for (int heli1 = 0; heli1 < numHelicopters; heli1++){
                
                for (int heli2 = 0; heli2 < numHelicopters; heli2++){
                    
                    if (heli1 != heli2){
                        ArrayList<Integer> grups = PGBoard.getGrupsHeli(heli1); //<----- id dels grups
                        for (int g = 0; g < grups.size(); g++){
                            
                            int sortida_origen = PGBoard.getNumSortida(heli1,grups.get(g));
                            int num_sortides = PGBoard.getNumSortides(heli2);
                            for (int sortida = 0; sortida < num_sortides; sortida++){
                                
                                if (PGBoard.noEsViolenRestriccions1(heli2,g,sortida)){
                                    
                                    PGBoardaux = new ProbDesEstat(PGBoard);
                                    PGBoardaux.moureGrupDHeli(heli1,heli2,g,sortida); //int int int int | sortida_desti
                                    PGBoardaux.reordena(heli1,sortida_origen);
                                    PGBoardaux.reordena(heli2,sortida); //sortida destí
                                    retval.add (new Successor ("Moure Grup Diferent Helicopter", PGBoardaux)); //falta el cas d'afegir una nova sortida amb un sol grup
                                }
                            }
                        }
                    }
                }
            }
            */
             
            
            for (int heli = 0; heli < numHelicopters; heli++){
                 
                ArrayList<Integer> grups1 = PGBoard.getGrupsHeli(heli); //<----- id dels grups
                for (int g1 = 0; g1 < grups1.size(); g1++){
                    int sortidaG1 = PGBoard.getNumSortida(heli,grups1.get(g1));
                    
                    ArrayList<Integer> grups2 = PGBoard.getGrupsHeli(heli);
                    for (int g2 = 0; g2 < grups2.size(); g2++){
                        int sortidaG2 = PGBoard.getNumSortida(heli,grups2.get(g2));
                        
                        if (grups1.get(g1) != grups2.get(g2)){
                            
                            if (PGBoard.noEsViolenRestriccions2(heli,grups1.get(g1),grups2.get(g2)) && PGBoard.noEsViolenRestriccions2(heli,grups2.get(g2),grups1.get(g1))){
                             
            System.out.println("-estat intermig despres getters-");
             for (int i=0;i<numHelicopters; i++){
                System.out.print("heli" + i + " => ");
                PGBoard.imprimeixHeli(i);
             }
             System.out.println();
                                
                                    PGBoardaux = new ProbDesEstat(PGBoard);
                                    
                                    ProbDesEstat.SortidaEsborrada sE_noUtil = PGBoardaux.new SortidaEsborrada(); 
                                    
                                    PGBoardaux.swapGrupMHeli(heli,grups1.get(g1),grups2.get(g2)); //int int int
                                    PGBoardaux.reordena(heli,sortidaG1,sE_noUtil);
                                    PGBoardaux.reordena(heli,sortidaG2,sE_noUtil); //sortida destí
                                    retval.add (new Successor ("Swap Grup Mateix Helicopter", PGBoardaux));
                                    
                                    System.out.println("-estat SWAP GRUP M HELI");
                                    for (int ii=0;ii<numHelicopters; ii++){
                                        System.out.print("heli" + ii + " => ");
                                        PGBoardaux.imprimeixHeli(ii);
                                    }
                                     
            System.out.println("-estat intermig ORIGINAL despres operador-");
             for (int i=0;i<numHelicopters; i++){
                System.out.print("heli" + i + " => ");
                PGBoard.imprimeixHeli(i);
             }
             System.out.println();
                            }
                        }
                    }
                }
            }
           
             
             
            for (int heli1 = 0; heli1 < numHelicopters; heli1++){
                
                for (int heli2 = 0; heli2 < numHelicopters; heli2++){
                    
                    if (heli1 != heli2) {
                        ArrayList<Integer> grups1 = PGBoard.getGrupsHeli(heli1);
                    
                        
                        for (int g1 = 0; g1 < grups1.size(); g1++){ 
                            int sortidaG1 = PGBoard.getNumSortida(heli1,grups1.get(g1));
                        
                            ArrayList<Integer> grups2 = PGBoard.getGrupsHeli(heli2);
                            for (int g2 = 0; g2 < grups2.size(); g2++){   
                                int sortidaG2 = PGBoard.getNumSortida(heli2,grups2.get(g2));
                            
                            if ((PGBoard.noEsViolenRestriccions2(heli1,grups1.get(g1),grups2.get(g2))) && (PGBoard.noEsViolenRestriccions2(heli2,grups2.get(g2),grups1.get(g1)))){
														 
										//System.out.println("-estat intermig ORIGINAL despres getters-");
										 //for (int i=0;i<numHelicopters; i++){
										//	System.out.print("heli" + i + " => ");
										//	PGBoard.imprimeixHeli(i);
										 //}
										// System.out.println();
										
                                        PGBoardaux = new ProbDesEstat(PGBoard);
                                    
                                        ProbDesEstat.SortidaEsborrada sE_noUtil = PGBoardaux.new SortidaEsborrada(); 
                                       
                                        PGBoardaux.swapGrupDHeli(heli1,heli2,grups1.get(g1),grups2.get(g2)); //int int int int
                                        PGBoardaux.reordena(heli1,sortidaG1,sE_noUtil);
                                        PGBoardaux.reordena(heli2,sortidaG2,sE_noUtil); //sortida destí
                                        retval.add (new Successor ("Swap Grup Diferent Helicopter", PGBoardaux));
                                        
                                    //   System.out.println("-estat SWAP GRUP D HELICOP");
									//	for (int ii=0;ii<numHelicopters; ii++){
									//		System.out.print("heli" + ii + " => ");
									//		PGBoardaux.imprimeixHeli(ii);
									//	}
                                     
									//	System.out.println("-estat intermig ORIGINAL despres operador-");
									//	 for (int i=0;i<numHelicopters; i++){
									//		System.out.print("heli" + i + " => ");
									//		PGBoard.imprimeixHeli(i);
									//	 }
									//	 System.out.println();
										 
                                }
                            }
                        }
                    }
                }
            }  
            
            System.out.println("-estat intermig ORIGINAL-");
             for (int i=0;i<numHelicopters; i++){
                System.out.print("heli" + i + " => ");
                PGBoard.imprimeixHeli(i);
             }
             System.out.println();
                 
             System.out.println();
             
            
            for (int heli1 = 0; heli1 < numHelicopters; heli1++){
                
                for (int heli2 = 0; heli2 < numHelicopters; heli2++){ 
                    
                    if (heli1 != heli2) {

                        ArrayList<Integer> viatgesH1 = PGBoard.getViatgesHeli(heli1);
                        for (int v = 0; v < viatgesH1.size(); v++){
                            
                           
							System.out.println("-estat intermig ORIGINAL despres getters-");
                            for (int i=0;i<numHelicopters; i++){
								System.out.print("heli" + i + " => ");
								PGBoard.imprimeixHeli(i);
							 }
							 System.out.println();
							 
							PGBoardaux = new ProbDesEstat(PGBoard);
							PGBoardaux.moureSortida(heli1,viatgesH1.get(v),heli2); //int int int 
							retval.add (new Successor ("Moure Sortida", PGBoardaux));
							
							System.out.println("-estat MOURE SORTIDA");
									for (int ii=0;ii<numHelicopters; ii++){
										System.out.print("heli" + ii + " => ");
										PGBoardaux.imprimeixHeli(ii);
									}
													 
							System.out.println("-estat intermig ORIGINAL despres operador-");
                            for (int i=0;i<numHelicopters; i++){
								System.out.print("heli" + i + " => ");
								PGBoard.imprimeixHeli(i);
							 }
							 System.out.println();
                           
                        }
                    }
                     
                                    
                                
                }
            }
             
            
            
            for (int heli1 = 0; heli1 < numHelicopters; heli1++){
                
                for (int heli2 = 0; heli2 < numHelicopters; heli2++){ 
                    
                    if (heli1 != heli2) {

                        ArrayList<Integer> viatgesH1 = PGBoard.getViatgesHeli(heli1);
                        for (int v1 = 0; v1 < viatgesH1.size(); v1++){
                            
                            ArrayList<Integer> viatgesH2 = PGBoard.getViatgesHeli(heli2);
                            for (int v2= 0; v2 < viatgesH2.size(); v2++){
                            
                                //System.out.println("entra al swap sortida");
                                
                                PGBoardaux = new ProbDesEstat(PGBoard);
                                PGBoardaux.swapSortida(heli1,viatgesH1.get(v1),heli2,viatgesH2.get(v2)); //int int int int
                                retval.add (new Successor ("Swap Sortida", PGBoardaux));
														   
										System.out.println("-estat intermig ORIGINAL despres getters-");
										 for (int i=0;i<numHelicopters; i++){
											System.out.print("heli" + i + " => ");
											PGBoard.imprimeixHeli(i);
										 }
										 System.out.println();
														  
														  System.out.println("-estat SWAP SORTIDA");
																for (int ii=0;ii<numHelicopters; ii++){
																	System.out.print("heli" + ii + " => ");
																	PGBoardaux.imprimeixHeli(ii);
																}
																 
										System.out.println("-estat intermig ORIGINAL despres operador-");
										 for (int i=0;i<numHelicopters; i++){
											System.out.print("heli" + i + " => ");
											PGBoard.imprimeixHeli(i);
										 }
										 System.out.println();
										 
                            }			
                        }
                    }
                }
            }
         
            
            
        System.out.println("-estat intermigFINAL ORIGINAL-");
             for (int i=0;i<numHelicopters; i++){
                System.out.print("heli" + i + " => ");
                PGBoard.imprimeixHeli(i);
             }
      
             System.out.println();
              System.out.println();
               System.out.println();
        
      
        //System.out.println("He arribat al final de Successors");    
        return retval;
    }
}
