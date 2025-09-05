import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 10;
    private final int SEPARACION = 40;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        /*
         * for (int i = 0; i < TOTAL_CARTAS; i++) {
         * Carta carta=cartas[i];
         * carta.mostrar(pnl, , );
         * }
         */
        int posicion = MARGEN + TOTAL_CARTAS * SEPARACION;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= SEPARACION;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String resultado = "No se encontraron grupos";
        // calcular los contadores de las cartas
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        // validar si hubo grupos
        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupos = true;
                break;
            }
        }

        // obtener los grupos
        if (hayGrupos) {
            resultado = "Se hallaron los siguientes grupos:\n";

            for (int i = 0; i < contadores.length; i++) {
                int contador = contadores[i];
                if (contador >= 2) {
                    resultado += Grupo.values()[contador] + " de " + NombreCarta.values()[i] + "\n";
                }
            }
        }
        return resultado;
    }
//NUEVA FUNCIONLIDAD: Escaleras de la misma pinta 

    public String getEscaleras() {
    String resultado = "No se encontraron escaleras";
    String escaleras = "";

    String[] nombresCartas = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    Grupo[] grupos = Grupo.values(); // usamos el enum Grupo

    for (Pinta pinta : Pinta.values()) {
        boolean[] numeros = new boolean[13];
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (cartas[i].getPinta() == pinta) {
                numeros[cartas[i].getNombre().ordinal()] = true;
            }
        }

        int inicio = -1;
        int longitud = 0;

        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i]) {
                if (inicio == -1) inicio = i;
                longitud++;
            }

            // Procesar escalera si encontramos un false o estamos al final
            if (!numeros[i] || i == numeros.length - 1) {
                if (longitud >= 3) {
                    String cartasEscalera = "";
                    for (int j = inicio; j < inicio + longitud; j++) {
                        cartasEscalera += nombresCartas[j] + ((j < inicio + longitud - 1) ? ", " : "");
                    }

                    Grupo grupoEncontrado = (longitud < grupos.length) ? grupos[longitud] : Grupo.DECIMA;
                    escaleras += "Se halló un " + grupoEncontrado + " de " + pinta + ": " + cartasEscalera + "\n";
                }
                inicio = -1;
                longitud = 0;
            }
        }
    }

    if (escaleras != "") {
        resultado = "Se hallaron las siguientes escaleras:\n" + escaleras;
    }

    return resultado;
}

    // NUEVA FUNCIONALIDAD: Obtener puntaje de los jugadores.
    public int getPuntajeCartasSolas() {
        int puntaje = 0;

        // 1. Contar cuántas veces aparece cada nombre de carta
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        // 2. Recorrer las cartas de la mano
        for (Carta carta : cartas) {
            // Solo sumamos si esa carta está exactamente UNA vez en la mano
            if (contadores[carta.getNombre().ordinal()] == 1) {
                puntaje += carta.getValor();
            }
        }

        return puntaje;
    }
}
