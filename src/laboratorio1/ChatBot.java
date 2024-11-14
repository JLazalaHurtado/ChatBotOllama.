package laboratorio1;

import com.formdev.flatlaf.FlatLightLaf;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatBot extends javax.swing.JFrame {

    String[][] matrizConv = new String[200][301]; // Matriz para almacenar conversaciones
    String[] index = new String[100];
    int indice = 0;
    int mensajeActual = 0;
    int conversacionActual = 0;

    String[] preguntas = new String[100];
    String[] respuestas = new String[100];
    int[][] conv = new int[100][10];
    int contp = 0;
    int contc = 0;

    public ChatBot() {
        initComponents();
        inicializarListaChats();
        
        // Seleccionar el primer chat por defecto
        Listachats.setSelectedIndex(0);
    }

    public ChatBot(String[] chat, String[][] x) {
        Listachats.setListData(chat);
        matrizConv = x;
    }

    private void inicializarListaChats() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Chat 1");
        Listachats.setModel(listModel);
        index[0] = "Chat 1";
    }

    private String enviarSolicitudAPI(String prompt) throws IOException {
        String botResponse = "";
        URL url = new URL("http://localhost:11434/v1/chat/completions");

        // Configurar la conexión HTTP
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        // Construir el cuerpo de la solicitud con el prompt del usuario
        String jsonInputString = "{\n"
                + "  \"model\": \"llama3.2\",\n"
                + "  \"messages\": [\n"
                + "    {\n"
                + "      \"role\": \"user\",\n"
                + "      \"content\": \"" + prompt + "\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"stream\": false\n"
                + "}";

        // Enviar la solicitud
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Leer y procesar la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner responseScanner = new Scanner(conn.getInputStream());
            StringBuilder respuesta = new StringBuilder();
            while (responseScanner.hasNext()) {
                respuesta.append(responseScanner.nextLine());
            }
            responseScanner.close();

            // Extraer la respuesta del bot desde el JSON
            botResponse = extraerRespuestaBot(respuesta.toString());
        } else {
            System.out.println("Error en la solicitud: " + responseCode);
        }

        // Cerrar la conexión
        conn.disconnect();
        return botResponse;
    }

    private String extraerRespuestaBot(String jsonResponse) {
        String botResponse = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.has("choices")) {
                JSONArray choicesArray = jsonObject.getJSONArray("choices");
                if (choicesArray.length() > 0) {
                    JSONObject choiceObject = choicesArray.getJSONObject(0);
                    if (choiceObject.has("message")) {
                        JSONObject messageObject = choiceObject.getJSONObject("message");
                        botResponse = messageObject.getString("content");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al extraer la respuesta del bot: " + e.getMessage());
        }
        return botResponse;
    }

    private void actualizarChatArea() {
        indice = Listachats.getSelectedIndex();
        if (indice != -1) {
            chatArea.setText(""); // Limpiar el área de chat
            StringBuilder chatContent = new StringBuilder();

            for (int i = 0; i < 301; i++) {
                if (matrizConv[indice][i] != null && !matrizConv[indice][i].isEmpty()) {
                    chatContent.append(matrizConv[indice][i]).append("\n\n");
                }
            }

            chatArea.setText(chatContent.toString());
        }
    }

    private void EnviarMouseClicked(java.awt.event.MouseEvent evt) {                                    
        if (!txtPregunta.getText().isEmpty()) {
            String pregunta = txtPregunta.getText();
            mostrarEnChat("Tu", pregunta);  // Muestra la pregunta en el JTextArea
            txtPregunta.setText("");  // Limpia el campo de texto

            try {
                String respuesta = enviarSolicitudAPI(pregunta);  // Envía la solicitud a la API
                mostrarEnChat("Ollama", respuesta);  // Muestra la respuesta en el JTextArea
                // Guardamos la pregunta y respuesta
                preguntas[contp] = pregunta;
                respuestas[contp] = respuesta;

                // Agregar al historial de conversaciones
                if (contc == 0 || contp == 0) {
                    conv[contc][0] = contp; // Primera interacción
                    contc++;
                }
                conv[contc - 1][contp] = contp; // Índice de la pregunta
                contp++;

            } catch (IOException ex) {
                Logger.getLogger(ChatBot.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese una pregunta antes de enviar.", "", JOptionPane.WARNING_MESSAGE);
        }
    }                                    

    private void mostrarEnChat(String usuario, String mensaje) {
        chatArea.append(usuario + ": " + mensaje + "\n");
    }

    private void newchatbuttonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        chatArea.setText("");
        txtPregunta.setText("");
        contp = 0;

        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < contc; i++) {
            model.addElement("Chat " + (i + 1));
        }
        Listachats.setModel(model);
    }                                             

    private void ListachatsMouseClicked(java.awt.event.MouseEvent evt) {                                        
        actualizarChatArea();
    }                                       

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {                                         
        String op = JOptionPane.showInputDialog("Si selecciona este boton, se borrara el chat y no se guardara en el historial. Escriba la palabra Sí en caso de realizar esta acción");
        if (op.equalsIgnoreCase("si")) {
            indice = Listachats.getSelectedIndex();
            if (indice != -1) {
                // Borrar el chat seleccionado
                DefaultListModel<String> model = (DefaultListModel<String>) Listachats.getModel();
                model.removeElementAt(indice);

                // Limpiar la matriz de conversaciones
                for (int i = 0; i < 301; i++) {
                    matrizConv[indice][i] = null;
                }

                // Limpiar la vista
                chatArea.setText("");
                txtPregunta.setText("");
            }
        }
    }                                        

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatBot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton Enviar;
    private javax.swing.JList<String> Listachats;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton newchatbutton;
    private javax.swing.JTextField txtPregunta;
    // End of variables declaration
}
Cambios principales:
Botón de eliminar:

Se implementó la funcionalidad para eliminar un chat. Cuando el usuario confirma la eliminación (escribiendo "Sí"), el chat seleccionado en la lista se borra y también se limpia su contenido en la matriz de conversaciones.
La eliminación también afecta la vista en el chatArea, limpiando tanto el área de texto como el campo de pregunta.
Integración del procedimiento del botón "Enviar":

He fusionado el proceso de enviar una pregunta y obtener la respuesta del chatbot utilizando la API, además de guardar las preguntas y respuestas en el historial y




