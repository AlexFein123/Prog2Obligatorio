package um.edu.uy;

import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.ListaEnlazada;

public class CSVUtils {

    public static String arreglarJsonFalso(String input) {
        if (input == null || input.isEmpty()) return input;

        StringBuilder sb = new StringBuilder();
        boolean dentroCampo = false;
        char tipoComilla = 0;
        char prevChar = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '\'' || c == '"') {
                if (!dentroCampo) {
                    dentroCampo = true;
                    tipoComilla = c;
                    sb.append('"');
                } else if (c == tipoComilla && prevChar != '\\') {
                    dentroCampo = false;
                    sb.append('"');
                } else {
                    sb.append('\\').append(c);
                }
            } else if (c == '\\') {
                if (prevChar != '\\') {
                    sb.append("\\\\");
                } else {
                    sb.append('\\');
                }
            } else if (c == '\n' || c == '\r') {
                if (dentroCampo) {
                    sb.append(' ');
                }
            } else {
                sb.append(c);
            }

            prevChar = c;
        }

        if (dentroCampo) {
            sb.append('"');
        }

        return sb.toString();
    }

    public static String corregirEncoding(String nombre) {
        nombre = java.text.Normalizer.normalize(nombre, java.text.Normalizer.Form.NFKC);

        return nombre.replace("Ã©", "é")
                .replace("Ã¨", "è")
                .replace("Ã¡", "á")
                .replace("Ã", "à")
                .replace("â€™", "'")
                .replace("â€“", "-")
                .replace("â€œ", "\"")
                .replace("â€�", "\"")
                .replace("â€˜", "'")
                .replace("â€", "\"")
                .replace("Ã³", "ó")
                .replace("Ã±", "ñ")
                .replace("Ã¼", "ü")
                .replace("�", "");
    }

    public static String[] parseCSVLine(String linea) {
        if (linea == null || linea.isEmpty()) {
            return new String[0];
        }

        ListaEnlazada<String> campos = new ListaEnlazada<>();
        StringBuilder campo = new StringBuilder();
        boolean enComillas = false;

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (c == '"') {
                if (enComillas && i + 1 < linea.length() && linea.charAt(i + 1) == '"') {
                    campo.append('"');
                    i++;
                } else {
                    enComillas = !enComillas;
                }
            } else if (c == ',' && !enComillas) {
                campos.agregarAlFinal(campo.toString());
                campo.setLength(0);
            } else {
                campo.append(c);
            }
        }
        campos.agregarAlFinal(campo.toString());

        String[] resultado = new String[campos.tamanio()];
        for (int i = 0; i < campos.tamanio(); i++) {
            try {
                resultado[i] = campos.obtenervalorposicion(i);
            } catch (FueraDeRango e) {
                resultado[i] = "";
            }
        }

        return resultado;
    }
}
