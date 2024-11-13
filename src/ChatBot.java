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

    String[][] matrizConv = new String[200][301]; // Matriz para almacenar conversaciones
    String[] index = new String[100];
    int indice = 0;
    int mensajeActual = 0;
    int conversacionActual = 0;

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
    //
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

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Ollama");

        Enviar.setText("Enviar");
        Enviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EnviarMouseClicked(evt);
            }
        });

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane4.setViewportView(chatArea);

        newchatbutton.setText("Nuevo chat");
        newchatbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newchatbuttonActionPerformed(evt);
            }
        });

        Listachats.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        Listachats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListachatsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Listachats);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Historial");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(96, 96, 96)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(158, 158, 158)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(txtPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(Enviar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(newchatbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(119, 119, 119))
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
                    .addComponent(Enviar))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void EnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnviarMouseClicked
               if (!txtPregunta.getText().isEmpty()) {
            try {
                String pregunta = txtPregunta.getText();
                indice = Listachats.getSelectedIndex();
                
                // Buscar el próximo índice disponible en la matriz
                int nextIndex = 0;
                while (nextIndex < 301 && matrizConv[indice][nextIndex] != null) {
                    nextIndex += 2; // Saltar de 2 en 2 para mantener pregunta-respuesta juntos
                }
                
                if (nextIndex < 301) {
                    // Guardar la pregunta
                    matrizConv[indice][nextIndex] = "Tú: " + pregunta;
                    mensajeActual++;
                    
                    // Obtener y guardar la respuesta
                    String respuesta = enviarSolicitudAPI(pregunta);
                    matrizConv[indice][nextIndex + 1] = "Ollama: " + respuesta;
                    mensajeActual++;
                    
                    // Actualizar la visualización
                    actualizarChatArea();
                    txtPregunta.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Se ha alcanzado el límite de mensajes para esta conversación.", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ChatBot.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, 
                    "Error al comunicarse con el servidor: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese una pregunta antes de enviar.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_EnviarMouseClicked

    //Crear un nuevo chat
    private void newchatbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newchatbuttonActionPerformed
       if (mensajeActual > 0 && conversacionActual < 199) {
            conversacionActual++;
            mensajeActual = 0;
            
            // Actualizar el modelo de la lista
            DefaultListModel<String> model = (DefaultListModel<String>) Listachats.getModel();
            String NombreChat = "Chat " + (conversacionActual + 1);
            model.addElement(NombreChat);
            index[conversacionActual] = NombreChat;
            
            // Seleccionar el nuevo chat
            Listachats.setSelectedIndex(conversacionActual);
            
            // Limpiar el área de chat
            chatArea.setText("");
            txtPregunta.setText("");
        } else {
            String mensaje = mensajeActual == 0 ? 
                "No hay mensajes para guardar en el chat actual." :
                "Se ha alcanzado el límite máximo de chats.";
            
            JOptionPane.showMessageDialog(this,
                mensaje,
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_newchatbuttonActionPerformed

    private void ListachatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListachatsMouseClicked
        actualizarChatArea();
    }//GEN-LAST:event_ListachatsMouseClicked

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
