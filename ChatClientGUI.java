import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI extends JFrame {
  private JTextArea messageArea;
  private JTextField textField;
  private ChatClient client;
  private JButton exitButton;


  public ChatClientGUI(String userName) {
      super("Chat Application");
      setSize(400, 500);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      messageArea = new JTextArea();
      messageArea.setEditable(false);
      add(new JScrollPane(messageArea), BorderLayout.CENTER);

      textField = new JTextField();
      textField.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + userName + ": " + textField.getText();
            client.sendMessage(message);
            textField.setText("");
          }
      });
      add(textField, BorderLayout.SOUTH);

        // Initialize the exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
        // Send a departure message to the server
        String departureMessage = userName + " has left the chat.";
        client.sendMessage(departureMessage);
        
        // Delay to ensure the message is sent before exiting
            try {
                Thread.sleep(1000); // Wait for 1 second to ensure message is sent
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            
            // Exit the application
            System.exit(0);
            });
            
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

      // Initialize and start the ChatClient
      try {
          this.client = new ChatClient("127.0.0.1", 15000, this::onMessageReceived);
          client.startClient();
      } catch (IOException e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(this, "Error connecting to the server", "Connection error",
                  JOptionPane.ERROR_MESSAGE);
          System.exit(1);
      }
  }

  private void onMessageReceived(String message) {
      SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
  }

  public static void main(String[] args) {

    if (args.length == 0 ){
        System.out.println("Please Give your name as a argument");
        System.exit(1);   
    }
    else{
      SwingUtilities.invokeLater(() -> {
          new ChatClientGUI(args[0]).setVisible(true);
      });
    }  
  }
}