package ProbDes;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class ProbDesSuccesorFunction implements SuccessorFunction {
    public List getSuccessors(Object state){
        
            ArrayList retval = new ArrayList();
            ProbDesEstat PGBoard = (ProbDesEstat) state;
            ProbDesEstat PGBoardaux;
            
            int numHelicopters = PGBoard.getNHelicopters();
            
            for (int heli = 0; heli < numHelicopters; heli++){
                
                ArrayList<Integer> grups = PGBoard.getGrupsHeli(heli); //<----- id dels grups
                for (int g = 0; g < grups.size(); g++){
                    
                    int sortida_origen = PGBoard.getNumSortida(heli,grups.get(g));
                    int num_sortides = PGBoard.getNumSortides(heli);
                    for (int sortida = 0; sortida < num_sortides; sortida++){
                    
                        if (PGBoard.noEsViolenRestriccions1(heli,g,sortida)  && sortida_origen != sortida){
                            
                            PGBoardaux = new ProbDesEstat(PGBoard);
                            PGBoardaux.moureGrupMHeli(heli,grups.get(g),sortida); //int int int
                            PGBoardaux.reordena(heli,sortida_origen);
                            PGBoardaux.reordena(heli,sortida); //sortida destí
                            retval.add (new Successor ("Moure Grup Mateix Helicopter", PGBoardaux)); //falta el cas d'afegir una nova sortida amb un sol grup
                        }
                    }
                }
            }
                            
            
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
            
            
            for (int heli = 0; heli < numHelicopters; heli++){
                 
                ArrayList<Integer> grups = PGBoard.getGrupsHeli(heli); //<----- id dels grups
                for (int g1 = 0; g1 < grups.size(); g1++){
                    int sortidaG1 = PGBoard.getNumSortida(heli,grups.get(g1));
                    
                    for (int g2 = 0; g2 < grups.size(); g2++){
                        int sortidaG2 = PGBoard.getNumSortida(heli,grups.get(g2));
                        
                        if (grups.get(g1) != grups.get(g2)){
                            
                            if (PGBoard.noEsViolenRestriccions2(heli,g1,g2) && PGBoard.noEsViolenRestriccions2(heli,g2,g1)){
                                
                                    PGBoardaux = new ProbDesEstat(PGBoard);
                                    PGBoardaux.swapGrupMHeli(heli,g1,g2); //int int int ctrl+c ctrl+v guapo eh
                                    PGBoardaux.reordena(heli,sortidaG1);
                                    PGBoardaux.reordena(heli,sortidaG2); //sortida destí
                                    retval.add (new Successor ("Swap Grup Mateix Helicopter", PGBoardaux));
                            }
                        }
                    }
                }
            }
                        
                        
            for (int heli1 = 0; heli1 < numHelicopters; heli1++){
                
                for (int heli2 = 0; heli2 < numHelicopters; heli2++){
                    
                    ArrayList<Integer> grups1 = PGBoard.getGrupsHeli(heli1);
                    for (int g1 = 0; g1 < grups1.size(); g1++){ 
                    int sortidaG1 = PGBoard.getNumSortida(heli1,grups.get(g1));
                    
                        ArrayList<Integer> grups2 = PGBoard.getGrupsHeli(heli2);
                        for (int g2 = 0; g2 < grups2.size(); g2++){   
                        int sortidaG2 = PGBoard.getNumSortida(heli2,grups.get(g2));
                        
                        if (PGBoard.noEsViolenRestriccions2(heli1,g1,g2) && PGBoard.noEsViolenRestriccions2(heli2,g2,g1)){
                                
                                    PGBoardaux = new ProbDesEstat(PGBoard);
                                    PGBoardaux.swapGrupDHeli(heli1,heli2,g1,g2); //int int int int
                                    PGBoardaux.reordena(heli1,sortidaG1);
                                    PGBoardaux.reordena(heli2,sortidaG2); //sortida destí
                                    retval.add (new Successor ("Swap Grup Diferent Helicopter", PGBoardaux));
                            }
                        }
                    }
                }
            }
                        
                            
            for (int heli1 = 0; heli1 < numHelicopters; heli1++){
                
                for (int heli2 = 0; heli2 < numHelicopters; heli2++){ 
                    
                    ArrayList<Integer> viatgesH1 = PGBoard.getViatgesHeli(heli1);
                    for (int v = 0; v < viatgesH1.size(); v++){
                        
                        PGBoardaux = new ProbDesEstat(PGBoard);
                        PGBoardaux.moureSortida(heli1,viatgesH1.get(v),heli2); //int int int 
                        retval.add (new Successor ("Moure Sortida", PGBoardaux));
                    }
                }
            }
                     
             
            for (int heli1 = 0; heli1 < numHelicopters; heli1++){
                
                for (int heli2 = 0; heli2 < numHelicopters; heli2++){ 
                    
                    ArrayList<Integer> viatgesH1 = PGBoard.getViatgesHeli(heli1);
                    for (int v1 = 0; v1 < viatgesH1.size(); v1++){
                        
                        ArrayList<Integer> viatgesH2 = PGBoard.getViatgesHeli(heli2);
                        for (int v2= 0; v2 < viatgesH1.size(); v2++){
                        
                            PGBoardaux = new ProbDesEstat(PGBoard);
                            PGBoardaux.swapSortida(heli1,viatgesH1.get(v1),heli2,viatgesH2.get(v2)); //int int int int
                            retval.add (new Successor ("Moure Sortida", PGBoardaux));
                        }
                    }
                }
            }
            
        return retval;
    }
}
