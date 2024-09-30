package com.mycompany.views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.mycompany.compilador.Notepad;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Principal extends JFrame {
	private void updateCharacterCount() {
        int characterCount = textArea.getText().length();
        lblContador.setText("Caracteres: " + characterCount);
    }

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textArea;
    private JMenu menuWorkSpace;
    private Notepad notepad;
    private String currentFileName; // Variable to store the full path of the current file
    private JLabel lblArchivoActual; // Label to display the current file name
    private JLabel lblContador; // Label to display the character count

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal frame = new Principal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Principal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1044, 511);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuArchivo = new JMenu("Archivo");
        menuBar.add(menuArchivo);

        JMenuItem menuNuevoArchivo = new JMenuItem("Nuevo archivo");
        menuArchivo.add(menuNuevoArchivo);

        JMenuItem menuAbrirArchivo = new JMenuItem("Abrir archivo");
        menuArchivo.add(menuAbrirArchivo);

        JMenuItem menuGuardarArchivo = new JMenuItem("Guardar archivo");
        menuArchivo.add(menuGuardarArchivo);
        
        JMenuItem menuGuardarComo = new JMenuItem("Guardar como");
        menuArchivo.add(menuGuardarComo);
        
        JMenuItem menuCerrarArchivo = new JMenuItem("Cerrar archivo");
        menuArchivo.add(menuCerrarArchivo);

        menuWorkSpace = new JMenu("WorkSpace");
        menuBar.add(menuWorkSpace);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        lblArchivoActual = new JLabel("Archivo actual: "); // Initialize the label
        lblContador = new JLabel("Caracteres: 0"); // Initialize the character count label

        
        // Add a DocumentListener to the text area to update the character count
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateCharacterCount();
            }

            public void removeUpdate(DocumentEvent e) {
                updateCharacterCount();
            }

            public void changedUpdate(DocumentEvent e) {
                updateCharacterCount();
            }

            
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(lblArchivoActual)
                            .addGap(18)
                            .addComponent(lblContador)))
                    .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblArchivoActual)
                        .addComponent(lblContador))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(29, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);

        notepad = new Notepad();

        // Function to create a new file
        menuNuevoArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String fileName = notepad.nuevoArchivo(""); // Create the new file in the file system
                    if (fileName != null) {
                        currentFileName = fileName; // Update the full path of the current file
                        // Update the text area and workspace
                        textArea.setText("");
                        // Add a new item to the workspace menu
                        JMenuItem fileItem = new JMenuItem(new File(fileName).getName());
                        fileItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                String content = notepad.getFileContent(fileName);
                                if (content != null) {
                                    textArea.setText(content);
                                    currentFileName = fileName; // Update the full path of the current file
                                    lblArchivoActual.setText("Archivo actual: " + new File(fileName).getName()); // Update the label
                                    updateCharacterCount(); // Update the character count
                                }
                            }
                        });
                        menuWorkSpace.add(fileItem);
                        lblArchivoActual.setText("Archivo actual: " + new File(fileName).getName()); // Update the label
                        updateCharacterCount(); // Update the character count
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Function to open a file
        menuAbrirArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String fileName = notepad.abrirArchivo();
                    if (fileName != null) {
                        textArea.setText(notepad.getFileContent(fileName));
                        currentFileName = fileName; // Update the full path of the current file
                        // Add a new item to the workspace menu
                        JMenuItem fileItem = new JMenuItem(new File(fileName).getName());
                        fileItem.addActionListener(event -> {
                            String content = notepad.getFileContent(fileName);
                            if (content != null) {
                                textArea.setText(content);
                                currentFileName = fileName; // Update the full path of the current file
                                lblArchivoActual.setText("Archivo actual: " + new File(fileName).getName()); // Update the label
                                updateCharacterCount(); // Update the character count
                            }
                        });
                        menuWorkSpace.add(fileItem);
                        lblArchivoActual.setText("Archivo actual: " + new File(fileName).getName()); // Update the label
                        updateCharacterCount(); // Update the character count
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        // Function to save a file
        menuGuardarArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String content = textArea.getText();
                if (content != null) {
                    try {
                        if (currentFileName == null) {
                            notepad.guardarArchivoConSeleccion(content);
                        } else {
                            notepad.guardarArchivo(currentFileName, content);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(Principal.this, "The content is null.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Function to save as a file
        menuGuardarComo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String content = textArea.getText();
                if (content != null) {
                    try {
                        notepad.guardarComo(content);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        // Function to close the current file
        menuCerrarArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentFileName != null) {
                    int confirm = JOptionPane.showConfirmDialog(
                        Principal.this,
                        "¿Estás seguro de que deseas cerrar el archivo actual?",
                        "Confirmar cierre",
                        JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Find and remove the JMenuItem corresponding to the current file
                        for (int i = 0; i < menuWorkSpace.getItemCount(); i++) {
                            JMenuItem item = menuWorkSpace.getItem(i);
                            if (item != null && item.getText().equals(new File(currentFileName).getName())) {
                                menuWorkSpace.remove(item);
                                break;
                            }
                        }
                        currentFileName = null; // Clear the current file name
                        textArea.setText(""); // Clear the text area
                        lblArchivoActual.setText("Archivo actual: "); // Update the label
                        lblContador.setText("Caracteres: 0"); // Reset the character count
                    }
                } else {
                    JOptionPane.showMessageDialog(
                        Principal.this,
                        "No hay ningún archivo abierto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
        
    }
}