import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods for reading from and writing to text files.
 * Handles basic file I/O operations with error logging.
 */
public class FileUtils {
    /**
     * Appends content to a text file. Creates the file if it doesn't exist.
     * @param filename Path/name of the file to modify
     * @param content The text content to append
     */
    public static void appendToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads all lines from a text file.
     * @param filename Path/name of the file to read
     * @return List of strings representing each line in the file.
     *         Returns empty list if file doesn't exist or read fails.
     */
    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
}