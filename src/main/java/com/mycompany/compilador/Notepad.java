package com.mycompany.compilador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notepad {

    private Map<String, String> fileContents;

    public Notepad() {
        // Initialize the map to store file contents
        fileContents = new HashMap<>();
    }

    public void guardarArchivo(String fileName, String content) throws IOException {
        System.out.println("Saving file...");

        // Create the file with the provided name
        File file = new File(fileName);
        System.out.println("Full file path: " + file.getAbsolutePath());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            writer.flush(); // Ensure the content is written to the file
            // Update the file content in the map
            fileContents.put(fileName, content);
            System.out.println("File saved: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving the file: " + e.getMessage());
            throw e;
        }
    }

    public void guardarArchivoConSeleccion(String content) throws IOException {
        System.out.println("Save file with selection...");

        JFileChooser fileChooser = new JFileChooser();
        // Crear el filtro de archivos
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        // Agregar el filtro al file chooser
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            guardarArchivo(selectedFile.getAbsolutePath(), content);
        }
    }

    public void guardarComo(String content) throws IOException {
        System.out.println("Save as...");

        JFileChooser fileChooser = new JFileChooser();
        // Crear el filtro de archivos
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        // Agregar el filtro al file chooser
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            guardarArchivo(selectedFile.getAbsolutePath(), content);
        }
    }

    public String abrirArchivo() throws IOException {
        System.out.println("Opening file...");

        JFileChooser fileChooser = new JFileChooser();
        // Crear el filtro de archivos
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        // Agregar el filtro al file chooser
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            StringBuilder fileContent = new StringBuilder();
            try (Scanner sc = new Scanner(selectedFile)) {
                while (sc.hasNextLine()) {
                    fileContent.append(sc.nextLine()).append("\n");
                }
            }
            fileContents.put(selectedFile.getAbsolutePath(), fileContent.toString());
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    public String getFileContent(String fileName) {
        return fileContents.get(fileName);
    }

    public String nuevoArchivo(String content) throws IOException {
        System.out.println("New file...");

        JFileChooser fileChooser = new JFileChooser();
        // Crear el filtro de archivos
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        // Agregar el filtro al file chooser
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            guardarArchivo(selectedFile.getAbsolutePath(), content);
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    public void cerrarArchivo(String fileName) {
        fileContents.remove(fileName);
    }
}