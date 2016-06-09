/*
TP 1
==========
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
2. Minimizar F(x) = x4 – 10 x3 – 8 x2  + 150 x + 250, en el intervalo [-√8, √11], 
con mínimo en x=-2, 185038 con F(x)= 11,1662 y x= 7,350071 con F(x)= -131,9032.

La implementación se hizo utiizando un mapeamiento entre números enteros y
los valores x de la función. Se utilizó los valores enteros para convertirlos 
a valores binarios y asi procesar el cromosoma de la solucion



*/
package tpalgoritmosevolutivos1;
import java.util.Random;
import java.text.DecimalFormat;
import java.util.Scanner;


public class Tpalgoritmosevolutivos1 {
    //-----------------------------------------------
    //------------ CONFIGURACION DEL ALGORITMO ---------------------
    //configuracion de la poblacion     
    public static Double inicioIntervalo=-1*Math.sqrt(8);
    public static Double finIntervalo=Math.PI*Math.sqrt(11);

        
    public static int cantidadIndividuos=30;    
    public static int MINIMIZAR=0;
    public static int MAXIMIZAR=1;
    public static int MAXGENERACIONES=100;    

//    public static int FLAG=MAXIMIZAR;
    public static int FLAG=MINIMIZAR;    
    
    
    // -------- COEFICIENTES DEL POLINOMIO -------------

//    public static Double [] coeficientes={0.0,6.0,-1.0}; //coeficientes del polinomio
    public static Double [] coeficientes={250.0,150.0,-8.0,-10.0,1.0}; //coeficientes del polinomio    
    public static int Npolinomio=coeficientes.length; //grado del polinomio
           
    //----------- CONFIGURACION DE POBLACION ----------------------
    public static FilaTabla [] tabla=new FilaTabla [30];
    public static FilaTabla [] tablaHijos=new FilaTabla [30];
    //variable del optimo
    public static FilaTabla elMejor=new FilaTabla();

    
    // ---- CONFIGURACION DE MAPEO DE BINARIO -----
    public static int inicioMapeoBinario=0;
    public static int finMapeoBinario=65535;
    
    //----------------------- METODOS DE LA CLASE ------------
    /*
    Recibiendo un flag de bandera,
    seprocede a buscar la mejor solucion.
    
    El mejor siempre suplanta al más debil, de una generacion a otra
    
    FlagOptimizacion=0 es minimizar
    FlagOptimizacion=1 es maximizar
    
    Luego de haber tenido cruzamiento y mutaciones, viene la seleccoin
    El enfoque es tener un mejor y un peor. El peor es suplantado por el mejor
    En la posicion del peor

    Es muy agresiva la seleccion
    */
    public static void SeleccionarPoblacionElitista(int flagOptimizacion){
        
        int N=cantidadIndividuos;        

        /*Busqueda del mejor en forma secuencial*/
        for(int i=0;i<N;i++){
            if(flagOptimizacion==MINIMIZAR){                
                if(tabla[i].y<elMejor.y){
                    elMejor=tabla[i];
                    elMejor.index=i; //guarda posicion para no procrear consigo mismo
                }//fin                                
            }//MINIMIZAR
            //------------------------------------
            if(flagOptimizacion==MAXIMIZAR){
                if(tabla[i].y>elMejor.y){
                    elMejor=tabla[i];
                    elMejor.index=i; //guarda posicion para no procrear consigo mismo
                }//fin                
            }//MAXIMIZAR                                    
        }//for
        
        /*Poco politico el asunto, el mejor se clona jajaja
        Le saca el puesto al peor de la tabla
        */
        System.out.println("ACTUALIZANDO el mejor");      
        System.out.println("LINDO Y BONITO-> f("+ elMejor.x+")="+ elMejor.y+" posicion="+elMejor.index);
         
    }//SeleccionarPoblacion
    
    
    
    
    /* Recorre la tabla de poblacion y guarda los nuevos hijos despues
    de haberlos cruzado
    Tiene que cruzar, crear la nueva tabla de hijos
    calular su fitness
    calcular su x
    calcular su valor
    */
    public static void CruzarPoblacion(Double probabilidad){
        int N=cantidadIndividuos;
        
        Hijos h=new Hijos();
        //inicializar a cero tabla hijos
        for(int i=0;i<N;i++){
            //--- cargar ---
            tablaHijos[i]=new FilaTabla();
        }//for


        System.out.println("ANTES DEL CICLO");
        for(int i=0;i<N;i++){
            
            //evita cruzar consigo mismo
            if(i!=elMejor.index){
                h=new Hijos();
                System.out.println("-----antes del cruzamiento individual");
                System.out.println("-----antes del cruzamiento elMejor "+elMejor.repBinaria+" x"+elMejor.x+" y"+elMejor.y+" index"+elMejor.index);                
                System.out.println("-----tabla["+i+"]"+tabla[i].repBinaria+" x"+tabla[i].x+" y"+tabla[i].y+" index"+tabla[i].index);                
                h=Cruzamiento(elMejor.repBinaria, tabla[i].repBinaria);
                //guarda los nuevos hijos
                tablaHijos[i].index=i;
                tablaHijos[i].repBinaria=MutacionIndividual(h.hijo1,probabilidad);
                tablaHijos[i].pos=Integer.parseInt(tablaHijos[i].repBinaria,2);
                tablaHijos[i].x=BinarioAValor(tablaHijos[i].repBinaria);
                tablaHijos[i].y=valorFuncion(coeficientes,Npolinomio,tablaHijos[i].x);
            }else{
                tablaHijos[i].index=i;
                tablaHijos[i].repBinaria=elMejor.repBinaria;
                tablaHijos[i].pos=Integer.parseInt(tablaHijos[i].repBinaria,2);
                tablaHijos[i].x=BinarioAValor(tablaHijos[i].repBinaria);
                tablaHijos[i].y=valorFuncion(coeficientes,Npolinomio,tablaHijos[i].x);                
            }//
            //cruzamiento por parejas

            
            System.out.println("Esperar tecla: ");
            Scanner leerX2 = new Scanner(System.in);

            System.out.println(i+") "+tablaHijos[i].pos+" "+tablaHijos[i].repBinaria+" "+tablaHijos[i].x+" "+tablaHijos[i].y+" index"+tablaHijos[i].index);                        

        }//for
        
        //se guarda la tabla hijos
        //tabla=tablaHijos;
        
        //inicializar a cero
        for(int i=0;i<N;i++){
            //--- cargar ---
            tabla[i]=new FilaTabla();
            tabla[i]=tablaHijos[i];            
        }//for
        
    }//fin cruzar poblacion
    
    
    /*Convierte cadenas binarias a un valor de mapeo especifico */
    public static Double BinarioAValor(String cadenaBinaria){
        int valorBinarioCadena=0;
        Double valorDevolver=0.0;
        
        //conversion
        valorBinarioCadena=Integer.parseInt(cadenaBinaria,2);
        valorDevolver=(valorBinarioCadena*finIntervalo)/finMapeoBinario;
        
        System.out.println("--- FUNCION BinarioAValor ---");
        System.out.println("valorBinario="+valorBinarioCadena+" -> "+cadenaBinaria+" -> "+valorDevolver);
        return valorDevolver;
    }//fin MostrarPoblacion
    
    

    //-----------------------------------------------
    
    
    
    public static void MostrarHijos(){
        System.out.println("----------------MOSTRAR HIJOS--------------------------");        
        for(int i=0;i<tablaHijos.length;i++){
            //--- mostrar ---
            System.out.println(i+") "+tablaHijos[i].pos+" "+tablaHijos[i].repBinaria+" "+tablaHijos[i].x+" "+tablaHijos[i].y+" index"+tablaHijos[i].index);            
            //--- fin de mostrar ---
        }//for
        System.out.println("----------------FIN MOSTRAR HIJOS--------------------------");        
    }//fin MostrarPoblacion
    
    
    
    public static void MostrarPoblacion(){
        System.out.println("----------------MOSTRAR POBLACION--------------------------");        
        for(int i=0;i<tabla.length;i++){
            //--- mostrar ---
            System.out.println(i+") "+tabla[i].pos+" "+tabla[i].repBinaria+" "+tabla[i].x+" "+tabla[i].y+" index"+tabla[i].index);            
            //--- fin de mostrar ---
        }//for
        System.out.println("----------------FIN MOSTRAR POBLACION--------------------------");        
    }//fin MostrarPoblacion
    
    public static void IniciarPoblacion(int N, Double a, Double b ){
        Double distancia=b-a;
        Double paso=distancia/N;
        Double nuevoX=a;
        String cadenaBinaria="";
        
        //--------------------------------------
        //a pesar de todo se deja de manera provisoria a 16 bits
        //una coma puede influir
        int mapeo=0;
        int mapeoPaso=(int)(paso*finMapeoBinario/finIntervalo);
        int nuevoMapeo=0;
        //--------------------------------------

        
        //inicializar a cero
        for(int i=0;i<N;i++){
            //--- cargar ---
            tabla[i]=new FilaTabla();
            tablaHijos[i]=new FilaTabla();            
        }//for
        
        
        /* 
        Se genera la población uniformemente distribuida, podría haber sido haciendo random
        */
        
        for(int i=0;i<N;i++){
            //estblece el paso real de la variable x
            nuevoX=nuevoX+paso;
            
            //estblece el paso del mapeo
//            nuevoMapeo=nuevoMapeo+mapeoPaso;
              nuevoMapeo=(int)(nuevoX*finMapeoBinario/finIntervalo);
            
            //conversion a cadena binaria y relleno con ceros
            //para alcanzar 32 bits
            cadenaBinaria=String.format("%16s", Integer.toBinaryString(nuevoMapeo)).replace(' ', '0');        
                        
            //--- cargar tabla ---

            tabla[i].pos=nuevoMapeo;
            tabla[i].repBinaria=cadenaBinaria;
            tabla[i].x=nuevoX;
            tabla[i].y=valorFuncion(coeficientes,Npolinomio,nuevoX);
            //--- fin de cargar ---
                    
            //Double resu=valorFuncion(coeficientes,4,2.0);                    
            //System.out.println("pos ->"+i+" nuevoX->"+nuevoX);            
        }//for
        
    }//fin iniciarPoblacion



        public static void IniciarPoblacionMejorado(int N, Double a, Double b ){
        Double distancia=b-a;
        Double paso=distancia/N;
        Double nuevoX=a;
        String cadenaBinaria="";
        
        //--------------------------------------
        //a pesar de todo se deja de manera provisoria a 16 bits
        //una coma puede influir
        int mapeo=0;
        int mapeoPaso=(int)(paso*finMapeoBinario/finIntervalo);
        int nuevoMapeo=0;
        //--------------------------------------

        
        //inicializar a cero
        for(int i=0;i<N;i++){
            //--- cargar ---
            tabla[i]=new FilaTabla();
            tablaHijos[i]=new FilaTabla();            
        }//for
        
        
        /* 
        Se genera la población distribuida al azar
        */

        //--- uso random para inicializar
        Random rnd=new Random();        
        rnd.setSeed(System.currentTimeMillis());
        
        
        for(int i=0;i<N;i++){
            //estblece el paso real de la variable x
            //tirando ruleta
            nuevoX=(rnd.nextDouble()*(finIntervalo)+inicioIntervalo);
            
            //estblece el paso del mapeo
//            nuevoMapeo=nuevoMapeo+mapeoPaso;
              nuevoMapeo=(int)(nuevoX*finMapeoBinario/finIntervalo);
            
            //conversion a cadena binaria y relleno con ceros
            //para alcanzar 32 bits
            cadenaBinaria=String.format("%16s", Integer.toBinaryString(nuevoMapeo)).replace(' ', '0');        
                        
            //--- cargar tabla ---

            tabla[i].pos=nuevoMapeo;
            tabla[i].repBinaria=cadenaBinaria;
            tabla[i].x=nuevoX;
            tabla[i].y=valorFuncion(coeficientes,Npolinomio,nuevoX);
            //--- fin de cargar ---
                    
            //Double resu=valorFuncion(coeficientes,4,2.0);                    
            //System.out.println("pos ->"+i+" nuevoX->"+nuevoX);            
        }//for
        
    }//fin iniciarPoblacionMejorado
    
    
    
    
    /*
    En el cruzamiento se asume que se tienen a dos progenitores.
    La cantidad de hijos que engendran se mantiene fija, es dos.
    Los hijos reemplazan a los padres en sus posiciones.
    */       
    public static Hijos Cruzamiento(String papa, String mama){
        //se asume que ambas cadenas son del mismo tamano
        int LARGO=papa.length();
        int LARGOM=mama.length();

        
        int aleatorio=0;
        //--- uso random
        Random rnd=new Random();        
        rnd.setSeed(System.currentTimeMillis());
        aleatorio=(int)(rnd.nextDouble()*(LARGO-1)+1);
        //--- fin uso de random
        int CORTE=aleatorio;
        
        //devolver el resultado
        Hijos resultadoHijos=new Hijos();
        
        System.out.println("Funcion cruzamiento->");
        System.out.println("LARGO=->"+LARGO);        

        //bucle para cambiar valores
        for(int i=0;i<LARGO;i++){
//try{
            if(i<=CORTE){
                System.out.println("i <= CORTE");
                //primera parte del padre y madre
                resultadoHijos.hijo1=resultadoHijos.hijo1+papa.charAt(i);
                resultadoHijos.hijo2=resultadoHijos.hijo2+mama.charAt(i);            
            }else{
                //segunda parte del padre y madre
                System.out.println("i > CORTE");
                resultadoHijos.hijo2=resultadoHijos.hijo2+papa.charAt(i);
                resultadoHijos.hijo1=resultadoHijos.hijo1+mama.charAt(i);                        
            }//if
//}catch(Exception e){}
            System.out.println("-- valor i="+i+" CORTE="+CORTE+"  LARGO="+LARGO+" LARGOM="+LARGOM);
            System.out.println("         Padre="+papa);
            System.out.println("         Madre="+mama);         

            System.out.println("parcial hijo 1="+resultadoHijos.hijo1);
            System.out.println("parcial hijo 2="+resultadoHijos.hijo2);            
            
        }//for

        System.out.println("Padre="+papa);
        System.out.println("Madre="+mama);         
        System.out.println("hijo1="+resultadoHijos.hijo1);
        System.out.println("hijo2="+resultadoHijos.hijo2);        
        System.out.println("CORTE="+CORTE);        
        System.out.println("largo hijo1="+resultadoHijos.hijo1.length());                
        System.out.println("largo hijo2="+resultadoHijos.hijo2.length());                        
        return resultadoHijos;
    }//Cruzamiento
    
    
    /*
    Se encarga de mutar una sola cadena de ADN
    */
    public static String MutacionIndividual(String cadenaMutar, Double probabilidadMutar){
        //se obtiene el largo de la cadena a mutar
        int LARGO=cadenaMutar.length();

        Double aleatorio=0.0;
        //--- uso random
        Random rnd=new Random();        
        rnd.setSeed(System.currentTimeMillis());
//        aleatorio=rnd.nextDouble();
        //--- fin uso de random
        
        String cadenaMutada="0000000000000000";
        
        //---------------------------
        //bit que va a mutar dentro de la cadena
        //hasta el largo de la cadena
        //---------------------------
        int posicion=(int)((rnd.nextDouble()*(LARGO-2)+0));
        
        //pura semilla
//        rnd.setSeed(System.currentTimeMillis());
        int bitMutar=(int)(rnd.nextDouble()*1);
        
        System.out.println("Funcion MutacionIndividual->");
        
//        if(aleatorio <= probabilidadMutar){
            //no muta
//            System.out.println("No muta");
//        }else{
            //muta
            System.out.println("Muta posicion  =>"+posicion);
            System.out.println("Cadena recibida=>"+cadenaMutar);
            System.out.println("Bit mutar      =>"+bitMutar);            
            
            //------------------------------------------------            
            //---reemplazar en una posicion especifica el bit mutado
            char[] conversion = cadenaMutar.toCharArray();
            String digito=Integer.toString(bitMutar);
            char[] digitoChar=digito.toCharArray();

            System.out.println("digito      =>"+digito);            
            System.out.println("posicion      =>"+posicion);             

//            conversion[posicion] = digitoChar[0];
            cadenaMutada = String.valueOf(conversion);            
            //------------------------------------------------
            
            System.out.println("Cadena mutada  =>"+cadenaMutada);                        
//        }//fin if


        return cadenaMutada;
    }//MutacionIndividual
    
    public static Double valorFuncion(Double [] filaCoeficientes, int grado ,Double X){
     Double resultadoValorFuncion=0.0;
     
     Double suma=0.0;
     
     //------------------------------------------------------------------------
     for(int i=0;i<grado;i++){
       suma=suma+(filaCoeficientes[i]*Math.pow(X, i));
       
//       //System.out.println("pos-> "+i+" suma="+suma);
//       System.out.println("pos-> "+i+"  "+filaCoeficientes[i]);       
//       System.out.println("pos-> "+i+"potencia="+Math.pow(X, i));       
     }//for
     //------------------------------------------------------------------------     
     resultadoValorFuncion=suma;
     
     return resultadoValorFuncion;
    }//end valorFuncion
    
    
    
    
    public static void main(String[] args) {


        System.out.println("--- INICIO --");
        //arranca el proceso generando poblacion
//        IniciarPoblacion(cantidadIndividuos, inicioIntervalo, finIntervalo );
        IniciarPoblacionMejorado(cantidadIndividuos, inicioIntervalo, finIntervalo );
        System.out.println("--- DESPUES DE INICIALIZAR POBLACION--");


        
        //-----inicializa ---

        //fin iniciaiza --

        elMejor=tabla[0];
        
        for(int generacion=0;generacion<MAXGENERACIONES;generacion++){
//        for(int generacion=0;generacion<2;generacion++){            

        elMejor=tabla[0];
            
        System.out.println(generacion+")--- INICIO GENERACION -------------------------");                            

        MostrarPoblacion();                
        System.out.println("---------------SELECCIONANDO----------------------");        

        SeleccionarPoblacionElitista(FLAG);        
        System.out.println("---------------DESPUES DE SELECCION----------------------");        
        MostrarPoblacion();  
        System.out.println("---------------ANTES DEL CRUZAMIENTO----------------------");        
        //necesita al de mayor firness
        CruzarPoblacion(0.4);        
        System.out.println("---------------FIN DEL CRUZAMIENTO----------------------");        
        MostrarPoblacion();  

        System.out.println(generacion+"----------------- FIN GENERACION -------------------------");                
        }//fin generaciones

        System.out.println("--- RESULTADO FINAL ---");      
        System.out.println("INTERVALO a="+inicioIntervalo+"  "+"b="+finIntervalo);

        if(FLAG==MAXIMIZAR){
            System.out.println("BUSQUEDA DE MÁXIMO");                  
        }else{
            System.out.println("BUSQUEDA DE MINIMO");                          
        }
        System.out.println("LINDO Y BONITO-> f("+ elMejor.x+")="+ elMejor.y+" posicion="+elMejor.index);

        
    }//main
    
}//clase

