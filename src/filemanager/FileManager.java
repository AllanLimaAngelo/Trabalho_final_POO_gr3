package filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class FileManager {
	// manages different files needed for the project

	// prevents instantiation
	private FileManager() {
		throw new AssertionError();
	}
	
	public static boolean dbPropertiesExist() {
        // Specify the relative path to your src folder
        String path = "src/db.properties";

        // Create a File object for the specified path
        File file = new File(path);

        // Check if the file exists
        return file.exists();
    }
	
	public static void createDBProperties(String dbUsername, String dbPassword) {
        // Specify the relative path to your src folder
        String path = "src/db.properties";

        try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
            // Write database properties to the file
        	out.write("db.url=jdbc:postgresql://localhost:5433/grupo3db" + "\n");
            out.write("db.username=" + dbUsername + "\n");
            out.write("db.password=" + dbPassword + "\n");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static String readDBCreateFile() {
        // Specify the relative path to your src folder
        String path = "src/db.create";

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }
	
	
	
	

}
