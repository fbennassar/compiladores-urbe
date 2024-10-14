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
        // Aca se mapean los nombres de los archivos junto con su contenido
        fileContents = new HashMap<>();
    }

    /**
     * Guarda un archivo con el nombre fileName y el contenido content
     * @param fileName
     * @param content
     * @throws IOException
     */
    public void guardarArchivo(String fileName, String content) throws IOException {
        System.out.println("Saving file...");

        // Crea el archivo con el fileName que recibe como parametro
        File file = new File(fileName);
        System.out.println("Full file path: " + file.getAbsolutePath());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            writer.flush();
            // Actualiza el mapeo de archivos y su contenido
            fileContents.put(fileName, content);
            System.out.println("File saved: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving the file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Guarda un archivo con el nombre fileName y el contenido content
     * @param content
     * @throws IOException
     */
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

    /**
     * Guarda una copia del archivo con el mismo u otro nombre
     * @param content
     * @throws IOException
     */
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

    /**
     * Abre un archivo y retorna su contenido
     * @return
     * @throws IOException
     */
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

	/**
	 * Retorna el contenido de un archivo
	 * 
	 * @param fileName
	 * @return
	 */
    public String getFileContent(String fileName) {
        return fileContents.get(fileName);
    }

	/**
	 * Crea un nuevo archivo
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
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

    /**
     * Cierra un archivo
     * @param fileName
     */
    public void cerrarArchivo(String fileName) {
        fileContents.remove(fileName);
    }
}