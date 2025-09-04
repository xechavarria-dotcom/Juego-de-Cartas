import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private static final int[] PUNTOS_POR_CARTA = {
        1,  //AS
        2,  //DOS
        3,  //TRES
        4,  //CUATRO
        5,  //CINCO
        6,  //SEIS
        7,  //SIETE
        8,  //OCHO
        9,  //NUEVE
        10, //JACK
        10, //QUEEN
        10, //KING
    };

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
        int posicion = MARGEN + TOTAL_CARTAS * SEPARACION;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= SEPARACION;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String resultado = "No se encontraron grupos";
        
        // Fase 1: Calcular los contadores de todas las cartas
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }
        
        // Fase 2: Validar si hubo grupos y calcular el puntaje
        boolean hayGrupos = false;
        int puntajeTotal = 0;
        
        for (int i = 0; i < contadores.length; i++) {
            int contador = contadores[i];
            
            // Si el contador es 1, es una carta única que no forma grupo
            if (contador == 1) {
                puntajeTotal += PUNTOS_POR_CARTA[i];
            }
            
            // Si el contador es 2 o más, hay un grupo
            if (contador >= 2) {
                hayGrupos = true;
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
        
        // obtener puntaje
        resultado += "\nPuntaje total por cartas únicas: " + puntajeTotal;
        
        return resultado;
    }
    // FUNCIONALIDAD: Escaleras de la misma pinta
public String getEscaleras() {
    String resultado = "No se encontraron escaleras";
    String escaleras = "";

    // arreglo para mostrar cartas con símbolos y numeros correspondientes
    String[] nombresCartas = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

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
                if (inicio == -1) {
                    inicio = i;
                }
                longitud++;
            } else {
                if (longitud >= 3) {
                    // armar texto con las cartas encontradas
                    String cartasEscalera = "";
                    for (int j = inicio; j < inicio + longitud; j++) {
                        cartasEscalera += nombresCartas[j] + " ";
                    }

                    if (longitud == 3) {
                        escaleras += "Se halló una tercia de " + pinta + ": " + cartasEscalera + "\n";
                    } else if (longitud == 4) {
                        escaleras += "Se halló una cuarta de " + pinta + ": " + cartasEscalera + "\n";
                    } else if (longitud == 5) {
                        escaleras += "Se halló una quinta de " + pinta + ": " + cartasEscalera + "\n";
                    } else {
                        escaleras += "Se halló una escalera de " + pinta + " de " + longitud + " cartas: " + cartasEscalera + "\n";
                    }
                }
                inicio = -1;
                longitud = 0;
            }
        }

        // verificar al final
        if (longitud >= 3) {
            String cartasEscalera = "";
            for (int j = inicio; j < inicio + longitud; j++) {
                cartasEscalera += nombresCartas[j] + " ";
            }

            if (longitud == 3) {
                escaleras += "Se halló una tercia de " + pinta + ": " + cartasEscalera + "\n";
            } else if (longitud == 4) {
                escaleras += "Se halló una cuarta de " + pinta + ": " + cartasEscalera + "\n";
            } else if (longitud == 5) {
                escaleras += "Se halló una quinta de " + pinta + ": " + cartasEscalera + "\n";
            } else {
                escaleras += "Se halló una escalera de " + pinta + " de " + longitud + " cartas: " + cartasEscalera + "\n";
            }
        }
    }

    // aquí ya no usamos equals
    if (escaleras != "") {
        resultado = "\nSe hallaron las siguientes escaleras:\n" + escaleras;
    }

    return resultado;
 }
}