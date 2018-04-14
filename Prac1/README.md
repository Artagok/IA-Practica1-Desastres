# TO DO
1. ✔ Arreglar Reordena (FET, QUAN VULGUEU HO REPASSEM) 
2. ✔ Arreglar Operador 2
3. ✔ Print Estat final
4. Heuristic 2 (que impliquen canvis al Main)
5. Simulated Annealing (que implica canvis al Main i a Successors)
6. Crear sol_ini_3 (una mica mes currada) (estic pensant, o fer qua cada heli comensa amb 2 grups per sortida, o algo molt tocho en plan que compara quin grup esta mes aprop i fa que comencin amb aquests asignats)
7. Experiments + graficar 
8. Memòria

No creem Sortides noves en els 2 primers operadors (Justificar)

 ## Llegiu aquest README per entendre la estructura i tal, germans

Mateixa estructura de fitxers que [Montoya](https://github.com/trenete97/IA-Practica1-Gasolina/tree/master/Practica1)

* __ProbDes__ -> .java que cal fer (GoalState, Estat, Generar Successors, Heurístics...)

* __javadoc__ -> documentació (DOXY) de Centro | Centros | Grupo | Grupo

* __lib__ -> llibreries vàries (entenc que per la UI i merdes, crec que és molt per quan ja està molt acabat)

* __sources__ -> .zips que en principi ja no necessitem però els tenim tots centralitzats per si de cas

* __AIMA.jar__ -> classes de AIMA: principalment aima.search.framework i aima.search.informed (HillClimbing i SimulatedAnnealing)

* __Desastres.jar__ -> Classes: Centro | Centros | Grupo | Grupos (import IA.Desastres.* per importar les 4)

* __Main.java__ -> El Main, haurem de fer enginyeria inversa amb el Main del Montoya, serveix per triar paràmetres com per exemple si es fa servir HC o SA, i triar diferents Heuristics, etc

També hi ha el pdf enunciat i aquest README

*Creuem els dits perquè no sé com ens en sortirem d'aquesta. __RIP__*

seed = 1234
solucio incial = sol1 (tots els grups a heli0 amb un 1 grup per sortida)
No aplicar cap operador!
nGrups = 100
nCentres = 5 
ha de donar 5462,83 (suposem que esta en minuts)
