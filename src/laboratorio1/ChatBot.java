/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
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

/**
 *
 * @author jlaza
 */
public class ChatBot extends javax.swing.JFrame {

    /**
     * Creates new form ChatBot
     */
    String[] preguntas = new String[100];
    String[] respuestas = new String[100];
    int[][] conv = new int[100][10];
    int contp = 0;
    int contc = 0;

    public ChatBot() {

        initComponents();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        txtPregunta = new javax.swing.JTextField();
        Enviar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        newchatbutton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Listachats = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("   Ollama");

        Enviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laboratorio1/send.png"))); // NOI18N
        Enviar.setText("Enviar");
        Enviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EnviarMouseClicked(evt);
            }
        });

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane4.setViewportView(chatArea);

        newchatbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laboratorio1/greenbutton.png"))); // NOI18N
        newchatbutton.setText("Nuevo chat");
        newchatbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newchatbuttonActionPerformed(evt);
            }
        });

        Listachats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListachatsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Listachats);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Historial");

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laboratorio1/broom.png"))); // NOI18N
        btnEliminar.setText("Borrar Chat ");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(96, 96, 96))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnEliminar)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newchatbutton, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(Enviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(84, 84, 84))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newchatbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Enviar)
                    .addComponent(btnEliminar))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mostrarEnChat(String usuario, String mensaje) {
        chatArea.append(usuario + ": " + mensaje + "\n");
    }
    private void EnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnviarMouseClicked

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
    }//GEN-LAST:event_EnviarMouseClicked

    //Crear un nuevo chat
    private void newchatbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newchatbuttonActionPerformed
        chatArea.setText("");
        txtPregunta.setText("");
        contp = 0;

        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < contc; i++) {
            model.addElement("Chat " + (i + 1));
        }
        Listachats.setModel(model);
    }//GEN-LAST:event_newchatbuttonActionPerformed

    private void ListachatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListachatsMouseClicked
        
    }//GEN-LAST:event_ListachatsMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
      String op = JOptionPane.showInputDialog("Si selecciona este boton, se borrara el chat y no se guardara en el historial, Escriba la palabra Sí en caso de realizar esta acción");
      if (op.equalsIgnoreCase("si")){
          chatArea.setText(" ");
          txtPregunta.setText(" ");
      }
    }//GEN-LAST:event_btnEliminarMouseClicked
   


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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Enviar;
    private javax.swing.JList<String> Listachats;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton newchatbutton;
    private javax.swing.JTextField txtPregunta;
    // End of variables declaration//GEN-END:variables
}
