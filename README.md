# AE-tp1

TRABAJOS PRÁCTICOS (TP)
ALGORITMOS EVOLUTIVOS - 2016


TP 1
Diseñar un algoritmo Genético que optimice el polinomio paramétrico:
F(x) = a0 + a1 x + a2 x2 + ….. + an xn
Entradas: 
Grado del polinomio: n
Coeficientes del polinomio: a0 ….. an
Flag de optimización: 0 = minimizar  y  1 = maximizar.
Dominio de definición / Intervalo de búsqueda: [a, b]
Número de bits por cromosoma: 32
Tamaño de la población: 30
Número Máximo de generaciones: 100
Salida
Última Población, incluyendo los valores de la incógnita x y la función optimizada F(x)

POLINOMIOS DE PRUEBA:
1. Maximizar F(x) = 6x – x2 en el intervalo [0, 10], con máximo en x=3.
2. Minimizar F(x) = x4 – 10 x3 – 8 x2  + 150 x + 250, en el intervalo [-√8, √11], con mínimo en x=-2, 185038 con F(x)= 11,1662 y x= 7,350071 con F(x)= -131,9032.


TP 1 - Extra
Algoritmo Genético del TP 1, con Fitness Sharing

1. Minimizar F(x) = x4 – 10 x3 – 8 x2  + 150 x + 250, en el intervalo [-√8, √11], con mínimo en x=-2, 185038 con F(x)= 11,1662 y x= 7,350071 con F(x)= -131,9032.
2. Minimizar f xx sen10x1,0 en el intervalo -1,0 x 2,0. Existen múltiples mínimos, siendo la solución óptima x= 1,85055.


TP 2
Un paradigma en la solución de problemas combinatorios es el problema del Cajero Viajante, más conocido como TSP (Traveling Salesman Problem), como puede verse en Wikipedia:
http://es.wikipedia.org/wiki/Problema_del_viajante

Este trabajo práctico consiste en resolver el TSP utilizando la heurística de Colonia de Hormigas o ACO (Ant Colony Optimization), estudiado en clase.

Los problemas tipos a ser resueltos pueden ser bajados del TSPLIB, disponible en:
http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/tsp/
En particular, se verificará si el programa funciona utilizando las 2 instancias del TSP que se citan a continuación:
1. Burma14
2. Berlin 52

Ejemplo del archivo Berlin 52 (archivo: berlin52.tsp.gz)
NAME: berlin52
TYPE: TSP
COMMENT: 52 locations in Berlin (Groetschel)
DIMENSION: 52
EDGE_WEIGHT_TYPE: EUC_2D
NODE_COORD_SECTION
1 565.0 575.0
2 25.0 185.0
3 345.0 750.0
:
51 1340.0 725.0
52 1740.0 245.0
EOF

NOTA: eventualmente, se podrá verificar si el algoritmo funciona con la instancia: pcb1173


TP 3
Una generalización del problema TSP es el TSP multi-objetivo. En particular, se viene utilizando el paradigma bi-objetivo (Biobjective TSP), que utiliza 2 matrices de costos diferentes (por ejemplo distancia y tiempo_de_viaje).

Este trabajo práctico propone resolver el Biobjective TSP utilizando el algoritmo evolutivo multiobjetivo (Multi-Objective Evolutionary Algorithm - MOEA) de su preferencia. Por ejemplo, puede optar por implementar el SPEA, SPEA2, NSGA, NSGA-II, o cualquier otro que escoja o decida proponer. Solo se verificará la capacidad de resolver los siguientes problemas tipos publicados en http://eden.dei.uc.pt/~paquete/tsp/
1. kroAB100
2. kroAB150

Los datos de cada problema kroAB están disponibles en 2 archivos: kroA y kroB, uno por cada objetivo. Por ejemplo, del archivo kroA100 (archivo: kroA100.tsp.gz):
NAME: kroA100
TYPE: TSP
COMMENT: 100-city problem A (Krolak/Felts/Nelson)
DIMENSION: 100
EDGE_WEIGHT_TYPE : EUC_2D
NODE_COORD_SECTION
1 1380 939
2 2848 96
:
99 198 1810
100 3950 1558
EOF


TP 4
Este trabajo práctico propone resolver el Biobjective TSP utilizando un algoritmo de Colonias de Hormigas (Multi-Objective Ant Colony Optimization - MOACO) de su preferencia. Solo se verificará la capacidad de resolver correctamente los siguientes problemas tipos publicados en http://eden.dei.uc.pt/~paquete/tsp/
1. kroAB100
2. kroAB150


TP 5 (Examen Final)
Este trabajo práctico propone resolver problemas del Benchmark DTLZ propuesto por Deb, Thiele, Laumanns y Zitzler (http://www.tik.ee.ethz.ch/sop/publicationListFiles/dtlz2002a.pdf) utilizando un algoritmo evolutivo multiobjetivo (Multi-Objective Evolutionary Algorithm - MOEA) de su preferencia, para el caso Many-Objective - MaOP, es decir, cuando el número de objetivos es al menos 4. Por ejemplo, puede optar por implementar el SPEA, SPEA2, NSGA, NSGA-II, o cualquier otro que escoja o decida proponer, debidamente optimizado para resolver problemas MaOP. Solo se verificará la capacidad de resolver el problema DTLZ 2, con 4 y 8 objetivos, con una variable de decisión x (variable independiente) de dimensión 20.
Enlaces de Referencia:
http://people.ee.ethz.ch/~sop/download/supplementary/testproblems/dtlz2/index.php

AYUDA: 
Se podrán verificar los resultados utilizando el entorno jMetal (http://jmetal.sourceforge.net/) 


