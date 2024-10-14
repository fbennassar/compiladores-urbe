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
    private String currentFileName;
    private JLabel lblArchivoActual;
    private JLabel lblContador; // Label que muestra el conteo de caracteres

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

        lblArchivoActual = new JLabel("Archivo actual: "); 
        lblContador = new JLabel("Caracteres: 0"); 

        
        // Document Listener para actualizar el conteo de caracteres
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

        // Action Listener para los items del menu
        menuNuevoArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String fileName = notepad.nuevoArchivo(""); // Crea un nuevo archivo
                    if (fileName != null) {
                        currentFileName = fileName; // Actualiza el nombre y ruta del archivo actual
                        // Pone en blanco el contenido del textArea
                        textArea.setText("");
                        // Coloca el item en el WorkSpace
                        JMenuItem fileItem = new JMenuItem(new File(fileName).getName());
                        fileItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                String content = notepad.getFileContent(fileName);
                                if (content != null) {
                                    textArea.setText(content);
                                    currentFileName = fileName;
                                    lblArchivoActual.setText("Archivo actual: " + new File(fileName).getName()); // Actualiza el label de archivo actual
                                    updateCharacterCount(); // Actualiza el contador de caracteres
                                }
                            }
                        });
                        menuWorkSpace.add(fileItem);
                        lblArchivoActual.setText("Archivo actual: " + new File(fileName).getName());
                        updateCharacterCount();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Funcion de abrir archivo
        menuAbrirArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String fileName = notepad.abrirArchivo();
                    if (fileName != null) {
                        textArea.setText(notepad.getFileContent(fileName));
                        currentFileName = fileName;
                        JMenuItem fileItem = new JMenuItem(new File(fileName).getName());
                        fileItem.addActionListener(event -> {
                            String content = notepad.getFileContent(fileName);
                            if (content != null) {
                                textArea.setText(content);
                                currentFileName = fileName;
                                lblArchivoActual.setText("Archivo actual: " + new File(fileName).getName());
                                updateCharacterCount();
                            }
                        });
                        menuWorkSpace.add(fileItem);
                        lblArchivoActual.setText("Archivo actual: " + new File(fileName).getName());
                        updateCharacterCount();
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        // Funcion para guardar archivo
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

        // Funcion de guardar como
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

        // Funcion para cerrar archivo
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
                        currentFileName = null; // Resetea el nombre del archivo actual
                        textArea.setText(""); // Borra el contenido TextArea
                        lblArchivoActual.setText("Archivo actual: "); // Actualiza el label de archivo actual
                        lblContador.setText("Caracteres: 0"); // Resetea el contador de caracteres
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