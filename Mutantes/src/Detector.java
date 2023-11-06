
import java.util.Scanner;

public class Detector {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Ingresamos los valores de la matriz
            String[] adn = new String[6];
            System.out.println("Recuerde que solo deben ingresarses los caracteres 'ACGT en mayusculas'");
            System.out.println("Ingresa las filas de la matriz de ADN (6 filas, cada una con no más de 6 caracteres):");
            for (int i = 0; i < 6; i++) {
                String fila = scanner.nextLine();
                if (fila.length() > 6) {
                    System.out.println("Error: La fila contiene más de 6 caracteres.");
                    break; // Salir del bucle
                }
                try {
                    adn[i] = validarFila(fila);
                } catch (InvalidDNAException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Carácter incorrecto en la posición: " + e.getPosition());
                    break; // Salir del bucle si se ingresa un valor no válido
                }
            }

            if (adn[0] == null) {
                continue; // Reiniciar el bucle si se ingresó una fila demasiado larga
            }


            // Mostrar la matriz resultante con espacios
            System.out.println("Matriz de ADN ingresada:");
            for (String fila : adn) {
                for (int i = 0; i < fila.length(); i++) {
                    System.out.print(fila.charAt(i) + " ");
                }
                System.out.println(); // Nueva línea para la siguiente fila
            }

            // Verificamos si el humano es mutante
            if (isMutant(adn)) {
                System.out.println("El humano es mutante.");
            } else {
                System.out.println("El humano no es mutante.");
            }

            // Preguntar al usuario si desea ingresar otra matriz
            System.out.println("¿Deseas ingresar otra matriz? (s/n)");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                break; // Salir del programa si la respuesta no es "s"
            }
        }
    }

    //Validamos que en la fila no haya un caracter que no corresponda
    public static String validarFila(String fila) throws InvalidDNAException {
        for (int i = 0; i < fila.length(); i++) {
            if ("ACGT".indexOf(fila.charAt(i)) == -1) {
                throw new InvalidDNAException("La fila contiene un carácter no válido en la posición " + i + ": " + fila.charAt(i), i);
            }
        }
        return fila;
    }

    public static boolean isMutant(String[] adn) {
        int rowCount = adn.length;
        int columnCount = adn[0].length();

        // Horizontalmente
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount - 3; j++) {
                if (adn[i].charAt(j) == adn[i].charAt(j + 1) &&
                        adn[i].charAt(j) == adn[i].charAt(j + 2) &&
                        adn[i].charAt(j) == adn[i].charAt(j + 3)) {
                    return true;
                }
            }
        }

        // Verticalmente
        for (int i = 0; i < rowCount - 3; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (adn[i].charAt(j) == adn[i + 1].charAt(j) &&
                        adn[i].charAt(j) == adn[i + 2].charAt(j) &&
                        adn[i].charAt(j) == adn[i + 3].charAt(j)) {
                    return true;
                }
            }
        }

        // Diagonalmente (izquierda a derecha)
        for (int i = 0; i < rowCount - 3; i++) {
            for (int j = 0; j < columnCount - 3; j++) {
                if (adn[i].charAt(j) == adn[i + 1].charAt(j + 1) &&
                        adn[i].charAt(j) == adn[i + 2].charAt(j + 2) &&
                        adn[i].charAt(j) == adn[i + 3].charAt(j + 3)) {
                    return true;
                }
            }
        }

        // Diagonalmente (derecha a izquierda)
        for (int i = 0; i < rowCount - 3; i++) {
            for (int j = 3; j < columnCount; j++) {
                if (adn[i].charAt(j) == adn[i + 1].charAt(j - 1) &&
                        adn[i].charAt(j) == adn[i + 2].charAt(j - 2) &&
                        adn[i].charAt(j) == adn[i + 3].charAt(j - 3)) {
                    return true;
                }
            }
        }

        return false;
    }
}
class InvalidDNAException extends Exception {
    private int position;

    public InvalidDNAException(String message, int position) {
        super(message);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}