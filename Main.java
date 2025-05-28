import java.util.*;
import java.io.*;

class Game{
    private String name;
    private String category;
    private int price;
    private int quality;

    Game(String name, String category, int price, int quality){
        this.name = name;
        this.category = category;
        this.price = price;
        this.quality = quality;
    }

    public String getname() {
        return name;
    }public String getcategory() {
        return category;
    } public int getprice() {
        return price;
    }public int getquality() {
        return quality;
    }

    public void setname(String name) {
        this.name = name;
    } public void setcategory(String category) {
        this.category = category;

    } public void setprice(int price) {
        this.price = price;
    } public void setquality(int quality) {
        this.quality = quality;
    }

    public String imprimir(){
        return name + "-"+category +"-"+ price + "-"+quality;
    }
}


class DataSet{
    private ArrayList<Game>data;
    private String sortedByAttribute;

    private List<Game> games = new ArrayList<>();

    public void setGames(List<Game> juegos) {
        this.games = new ArrayList<>(juegos); // hace una copia de los juegos
    }

    //metodo 1
    DataSet(ArrayList<Game>data){
        this.data = data;
        this.sortedByAttribute = "";
    }



    public void sortByAttribute(String attribute) {
        if ("price".equals(attribute)) {
            data.sort((g1, g2) -> g1.getprice() - g2.getprice());
            sortedByAttribute = "price";
        } else if ("category".equals(attribute)) {
            data.sort((g1, g2) -> g1.getcategory().compareTo(g2.getcategory()));
            sortedByAttribute = "category";
        } else if ("quality".equals(attribute)) {
            data.sort((g1, g2) -> g1.getquality() - g2.getquality());
            sortedByAttribute = "quality";
        } else {
            // Por defecto ordena por price
            data.sort((g1, g2) -> g1.getprice() - g2.getprice());
            sortedByAttribute = "price";
        }
    }


    // m2 //se le agrego el boolean para hacer tiempos de medicion en el main
    public ArrayList<Game> getGamesByPrice(int price, boolean binarySearch) {
        ArrayList<Game> resultado = new ArrayList<>();

        if (binarySearch && "price".equals(sortedByAttribute)) {
            int inicio = 0;
            int fin = data.size() - 1;

            while (inicio <= fin) {
                int medio = inicio + (fin - inicio) / 2;
                int precioMedio = data.get(medio).getprice();

                if (precioMedio == price) {
                    // Buscar hacia la izquierda para encontrar todos los elementos iguales
                    int i = medio;
                    while (i >= 0 && data.get(i).getprice() == price) i--;
                    i++;
                    while (i < data.size() && data.get(i).getprice() == price) {
                        resultado.add(data.get(i));
                        i++;
                    }
                    break;
                } else if (precioMedio < price) {
                    inicio = medio + 1;
                } else {
                    fin = medio - 1;
                }
            }
        } else {
            // Búsqueda lineal
            for (Game a : data) {
                if (a.getprice() == price) {
                    resultado.add(a);
                }
            }
        }

        return resultado;
    }





    //metodo 3 //tambien se le agrega el boolean para hacer pruebas de medicion en main
    public ArrayList<Game> getGamesByPriceRange(int lowerPrice, int higherPrice,boolean binarySearch) {
        ArrayList<Game> resultado = new ArrayList<>();

        if (binarySearch &&"price".equals(sortedByAttribute)) {
            // Búsqueda binaria para encontrar el primer índice con price >= lowerPrice
            int inicio = 0;
            int fin = data.size() - 1;
            int x = -1;

            while (inicio <= fin) {
                int medio = inicio + (fin - inicio) / 2;
                if (data.get(medio).getprice() >= lowerPrice) {
                    x = medio;
                    fin = medio - 1; // sigue buscando a la izquierda
                } else {
                    inicio = medio + 1;
                }
            }
            // Si se encontró un índice válido, recorrer desde ahí hasta que price > higherPrice
            if (x != -1) {
                for (int i = x; i < data.size(); i++) {
                    Game a = data.get(i);
                    if (a.getprice() > higherPrice) break;
                    resultado.add(a);
                }
            }
        } else {
            // Si no está ordenado por price, hace la búsqueda lineal
            for (Game a : data) {
                if (a.getprice() >= lowerPrice && a.getprice() <= higherPrice) {
                    resultado.add(a);
                }
            }
        }
        return resultado;
    }



    // metodo 4 //agregado el boolean para las pruebas
    public ArrayList<Game> getGamesByCategory(String category,boolean binarySearch) {
        ArrayList<Game> resultado = new ArrayList<>();

        if (binarySearch && "category".equals(sortedByAttribute)) {
            int inicio = 0;
            int fin = data.size() - 1;

            while (inicio <= fin) {
                int medio = inicio + (fin - inicio) / 2;
                String cmedio = data.get(medio).getcategory();
                int c = cmedio.compareTo(category);

                if (c == 0) {
                    // Buscar a la izquierda
                    while (medio >= 0 && data.get(medio).getcategory().equals(category)) {
                        medio--;
                    }
                    medio++;
                    // Agrega todos los juegos con esa categoría
                    while (medio < data.size() && data.get(medio).getcategory().equals(category)) {
                        resultado.add(data.get(medio));
                        medio++;
                    }
                    break;
                } else if (c < 0) {
                    inicio = medio + 1;
                } else {
                    fin = medio - 1;
                }
            }
        } else {
            for (Game a : data) {
                if (a.getcategory().equals(category)) {
                    resultado.add(a);
                }
            }
        }
        return resultado;
    }



    // metodo 5
    public ArrayList<Game> getGamesByQuality(int quality) {
        ArrayList<Game> resultado = new ArrayList<Game>();
        if("quality".equals(sortedByAttribute)) {

            int inicio = 0;
            int fin = data.size() - 1;

            while (inicio <= fin) {
                int medio = inicio + (fin - inicio) / 2;
                int calidadMedio = data.get(medio).getquality();

                if (calidadMedio == quality) {
                    while (medio >= 0 && data.get(medio).getquality() == quality) {
                        medio--;
                    }
                    medio++;
                    while (medio < data.size() && data.get(medio).getquality() == quality) {
                        resultado.add(data.get(medio));
                        medio++;
                    }
                    break;
                } else if (calidadMedio < quality) {
                    inicio = medio + 1;
                } else {
                    fin = medio - 1;
                }
            }
        }else{
            for (Game a : data) {
                if (a.getquality() == quality) {
                    resultado.add(a);
                }
            }
        }
        return resultado;
    }


    // METODO 6

    private Comparator<Game> Comparar(String attribute) {
        switch (attribute) {
            case "price":
                return Comparator.comparingInt(g -> g.getprice());
            case "quality":
                return Comparator.comparingInt(g -> g.getquality());
            case "category":
                return Comparator.comparing(g -> g.getcategory());
            case "name":
                return Comparator.comparing(g -> g.getname());
            default:
                return Comparator.comparingInt(g -> g.getprice());
                //se hara por precio si o si, si no se elige otro
        }
    }

    // Metodo para elegir por cual algoritmo realizar el ordenamiento
    public void sortByAlgorithm(String algorithm, String attribute){
        if(!attribute.equals("price") && !attribute.equals("category") && !attribute.equals("quality") && !attribute.equals("name")){
            attribute = "price";
        }

        if(algorithm.equals("countingSort") && attribute.equals("quality")) {
            countingSortByQuality();
        } else if(algorithm.equals("bubbleSort")) {
            bubbleSort(data, attribute);
        } else if(algorithm.equals("insertionSort")) {
            data = insertionSort(data, attribute);
        } else if(algorithm.equals("selectionSort")) {
            data = selectionSort(data, attribute);
        } else if(algorithm.equals("mergeSort")) {
            data = mergeSort(data, attribute);
        } else if(algorithm.equals("quickSort")) {
            quickSort(data, attribute);
        } else {
            Collections.sort(data, Comparar(attribute));
        }

        sortedByAttribute = attribute;
    }

    // BubbleSort
    private void bubbleSort(ArrayList<Game>data,String attribute){
        for(int i=0;i<data.size();i++){
            for(int j=i+1;j<data.size();j++){
                boolean x =false;
                Game a=data.get(i);
                Game b=data.get(j);
                if(attribute.equals("price")){
                    x =data.get(i).getprice() > data.get(j).getprice();
                }else if(attribute.equals("category")){
                    x =data.get(i).getcategory().compareTo(data.get(j).getcategory())>0;
                }else if(attribute.equals("quality")){
                    x =data.get(i).getquality() > data.get(j).getquality();
                }
                if(x){
                    Game temp=data.get(i);
                    data.set(i,data.get(j));
                    data.set(j,temp);
                }
            }
        }
    }

    // InsertionSort

    private ArrayList<Game> insertionSort(ArrayList<Game> data, String attribute) {
        Comparator<Game> comparator = Comparar(attribute);

        for (int i = 1; i < data.size(); i++) {
            Game actual = data.get(i);
            int j = i - 1;

            // Aca mueve elementos > q el actual hacia adelante
            while (j >= 0 && comparator.compare(data.get(j), actual) > 0) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, actual);
        }
        return data;
    }

    // SelectionSort
    private ArrayList<Game> selectionSort(ArrayList<Game>data,String attribute){
        for (int i = 0; i < data.size()- 1; i++) {
            int min = i;

            for (int j = i + 1; j < data.size(); j++) {
                Game a = data.get(j);
                Game b = data.get(min);
                boolean intecambia = false;

                if (attribute.equals("price")) {
                    intecambia = a.getprice() < b.getprice();
                } else if (attribute.equals("quality")) {
                    intecambia = a.getquality()  < b.getquality();
                } else if (attribute.equals("category")) {
                    intecambia = a.getcategory().compareTo(b.getcategory()) < 0;
                } else if (attribute.equals("name")) {
                    intecambia = a.getname().compareTo(b.getname()) < 0;
                }

                if (intecambia) {
                    min= j;
                }
            }

            if (min!= i) {
                // Aca intercambia posiciones
                Game aux = data.get(i);
                data.set(i, data.get(min));
                data.set(min, aux);
            }
        }
        return data;
    }

    // Merge , tiene que llamar a otro metodo de manera recursiva
    private ArrayList<Game> mergeSort(ArrayList<Game> data, String attribute){
        if(data.size()<=1){
            return data;
        }
        int medio = data.size()/2;
        ArrayList<Game> izquierda=new ArrayList<>(data.subList(0,medio));
        ArrayList<Game> derecha=new ArrayList<>(data.subList(medio,data.size()));

        izquierda = mergeSort(izquierda,attribute);
        derecha = mergeSort(derecha,attribute);
        return merge(izquierda,derecha,attribute);
    }

    private ArrayList<Game> merge(ArrayList<Game> izquierda, ArrayList<Game> derecha, String attribute){
        ArrayList <Game>aux=new ArrayList<>();
        int i=0,j=0;
        while(i<izquierda.size() && j<derecha.size()){
            Game uno=izquierda.get(i);
            Game dos=derecha.get(j);
            boolean menor;
            if(attribute.equals("price")){
                menor=uno.getprice()<=dos.getprice();
            }else if(attribute.equals("quality")){
                menor=uno.getquality()<=dos.getquality();
            }else if(attribute.equals("category")){
                menor=uno.getcategory().compareTo(dos.getcategory())<=0;
            }else{
                menor=true;
            }
            if(menor){
                aux.add(uno);
                i++;
            }else{
                aux.add(dos);
                j++;
            }
        }
        while(i<izquierda.size()){
            aux.add(izquierda.get(i++));
        }
        while(j<derecha.size()){
            aux.add(derecha.get(j++));
        }
        return aux;
    }



    //QUICK simplemente llama a la funcion con los parametros y los otros metodos se encargan de todo
    private void quickSort(ArrayList<Game> data, String attribute) {
        quickSortHelper(data, 0, data.size() - 1, attribute);
    }
    // divide y ordena
    private void quickSortHelper(ArrayList<Game> data, int low, int high, String attribute) {
        while (low < high) {
            int pi = partition(data, low, high, attribute);
            if (pi - low < high - pi) {
                quickSortHelper(data, low, pi - 1, attribute);
                low = pi + 1;
            } else {
                quickSortHelper(data, pi + 1, high, attribute);
                high = pi - 1;
            }
        }
    }
    //este metodo se encarga de elegir el pivote, es decir de donde queremos que parta a ordenar
    private int partition(ArrayList<Game> data, int izquierda, int derecha, String attribute) {
        int pivote = izquierda + new Random().nextInt(derecha - izquierda + 1);
        Collections.swap(data, pivote, derecha); // mueve pivote al final
        Game pivoti = data.get(derecha);
        int i = izquierda - 1;

        for (int j = izquierda; j < derecha; j++) {
            boolean menor = false;
            Game actual = data.get(j);

            if (attribute.equals("price")) {
                menor = actual.getprice() <= pivoti.getprice() ;
            } else if (attribute.equals("quality")) {
                menor = actual.getquality()  <= pivoti.getquality() ;
            } else if (attribute.equals("category")) {
                menor = actual.getcategory() .compareTo(pivoti.getcategory() ) <= 0;
            } else if (attribute.equals("name")) {
                menor = actual.getname() .compareTo(pivoti.getname() ) <= 0;
            }

            if (menor) {
                i++;
                Collections.swap(data, i, j);
            }
        }

        Collections.swap(data, i + 1, derecha);
        return i + 1;
    }

    // COUTING SORT este es un metodo requerido
    public void countingSortByQuality() {
        int rango = 101; // el rango es de 0 a 100
        ArrayList<ArrayList<Game>> c = new ArrayList<>(rango); // Se inicia
        for (int i = 0; i < rango; i++) {
            c.add(new ArrayList<>());
        }// distribuye juegos por quality
        for (Game x : data) {
            c.get(x.getquality()).add(x);
        } // y  ya aca reconstruye en orden
        data.clear();
        for (int i = 0; i < rango; i++) {
            data.addAll(c.get(i));
        }sortedByAttribute = "quality";
    }
}

// Class generatedata esta clase se encargara de crear juegos
class GenerateData{
    private static String [] palabras = {"Dracula", "Empirity", "Quillota", "Mario", "Mar", "Campeones"};
    private static String[] categorias = {"Acción", "Aventura", "Estrategia", "Suspenso", "Deportes", "Educativos"};
    private static Random rand = new Random();

    public static ArrayList<Game>generar(int n){

        ArrayList<Game> juegos=new ArrayList<>();

        //se busco como utilizar random para asi generar la cantidad de juegos solicitados
        for(int i=0;i<n;i++){
            String nombre= palabras[rand.nextInt(palabras.length)]+ palabras[rand.nextInt(palabras.length)];
            String categoria = categorias[rand.nextInt(categorias.length)];
            int precio= 1000+rand.nextInt(69001);//genera entre 1000 y 70000
            int calidad= rand.nextInt(101); // genera entre 0 y 100
            juegos.add(new Game(nombre,categoria,precio,calidad));
        } return juegos;
    }

    //Es un met para guardar los juegos generados en un archivo csv
    public static void guardarCSV(ArrayList<Game> juegos, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new File(nombreArchivo))) { // Se usa try para obligar a procesar
            writer.println("Name,Category,Price,Quality");
            for (Game juego : juegos) {
                writer.printf("%s,%s,%d,%d%n", juego.getname(), juego.getcategory(), juego.getprice(), juego.getquality()); // Letras raras de retorno de tipo de variable o salto de linea
            }
        } catch (IOException e) { // manejo de errores
            e.printStackTrace();// muestra errores
        }
    }

}






public class Main {
    public static void main(String[] args) {

        //aca se realizan las pruebas y asi conseguir los tiempos de ejecucion

        DataSet date=new DataSet(GenerateData.generar(1000000));
        date.sortByAlgorithm("CollectionSort","price");
        date.sortByAttribute("price");

        long inicio1=System.currentTimeMillis();
        ArrayList<Game>resultado=date.getGamesByPrice(20,true);
        long fin1=System.currentTimeMillis();
        System.out.println("El tiempo en milisegundos del metodo getGamesByPrice por busqueda binaria es: " +(fin1 - inicio1) + "ms");

        long inicio2=System.currentTimeMillis();
        ArrayList<Game>resultado2=date.getGamesByPrice(20,false);
        long fin2=System.currentTimeMillis();
        System.out.println("El tiempo en milisegundos del metodo getGamesByPrice por busqueda lineal es: " +(fin2 - inicio2) + " ms\n");


        // Generar juegos aleatorios
        ArrayList<Game> juegos100 = GenerateData.generar(100);
        ArrayList<Game> juegos10000 = GenerateData.generar(10000);
        ArrayList<Game> juegos1000000 = GenerateData.generar(1000000);

        int repeticiones = 5;

        // Promedio para 100 juegos
        long totalTiempo100 = 0;
        for (int i = 0; i < repeticiones; i++) {
            DataSet dataset = new DataSet(new ArrayList<>(juegos100)); // copiar para evitar listas ya ordenadas
            dataset.sortByAttribute("quality");

            long inicio = System.nanoTime();
            dataset.sortByAlgorithm("Collections.sort", "quality");
            long fin = System.nanoTime();
            totalTiempo100 += (fin - inicio);
        }
        System.out.println("Promedio Collections.sort con 100 juegos: " + (totalTiempo100 / repeticiones / 1000000) + " ms");

        // Promedio para 10000 juegos
        long totalTiempo10000 = 0;
        for (int i = 0; i < repeticiones; i++) {
            DataSet dataset = new DataSet(new ArrayList<>(juegos10000));
            dataset.sortByAttribute("quality");

            long inicio = System.nanoTime();
            dataset.sortByAlgorithm("Collections.sort", "quality");
            long fin = System.nanoTime();
            totalTiempo10000 += (fin - inicio);
        }
        System.out.println("Promedio Collections.sort con 10000 juegos: " + (totalTiempo10000 / repeticiones / 1000000) + " ms");

        // Promedio para 1000000 juegos
        long totalTiempo1000000 = 0;
        for (int i = 0; i < repeticiones; i++) {
            DataSet dataset = new DataSet(new ArrayList<>(juegos1000000));
            dataset.sortByAttribute("quality");

            long inicio = System.nanoTime();
            dataset.sortByAlgorithm("Collections.sort", "quality");
            long fin = System.nanoTime();
            totalTiempo1000000 += (fin - inicio);
        }
        System.out.println("Promedio Collections.sort con 1000000 juegos: " + (totalTiempo1000000 / repeticiones / 1000000) + " ms");



        // Toma de tiempo con CoutingSort
        System.out.println("\n TIEMPOS COUNTING SORT \n");
        int rep = 5;

// Promedio Counting Sort con 100 juegos
        long tiempoCounting100 = 0;
        for (int i = 0; i < rep; i++) {
            DataSet dsCount100 = new DataSet(new ArrayList<>(juegos100)); // copia nueva cada vez
            long inicio = System.nanoTime();
            dsCount100.sortByAlgorithm("countingSort", "quality");
            long fin = System.nanoTime();
            tiempoCounting100 += (fin - inicio);
        }
        System.out.println("Promedio Counting Sort con 100 juegos: " + (tiempoCounting100 / rep / 1000000) + " ms");

// Promedio Counting Sort con 10000 juegos
        long tiempoCounting10000 = 0;
        for (int i = 0; i < rep; i++) {
            DataSet dsCount10000 = new DataSet(new ArrayList<>(juegos10000));
            long inicio = System.nanoTime();
            dsCount10000.sortByAlgorithm("countingSort", "quality");
            long fin = System.nanoTime();
            tiempoCounting10000 += (fin - inicio);
        }
        System.out.println("Promedio Counting Sort con 10000 juegos: " + (tiempoCounting10000 / rep / 1000000) + " ms");

// Promedio Counting Sort con 1000000 juegos
        long tiempoCounting1000000 = 0;
        for (int i = 0; i < rep; i++) {
            DataSet dsCount1000000 = new DataSet(new ArrayList<>(juegos1000000));
            long inicio = System.nanoTime();
            dsCount1000000.sortByAlgorithm("countingSort", "quality");
            long fin = System.nanoTime();
            tiempoCounting1000000 += (fin - inicio);
        }
        System.out.println("Promedio Counting Sort con 1000000 juegos: " + (tiempoCounting1000000 / rep / 1000000) + " ms");
    }
}