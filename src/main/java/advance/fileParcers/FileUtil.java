package advance.fileParcers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class FileUtil {

    public static String getText(String fileName, String encoding) {
        StringBuilder sb = new StringBuilder();
        Scanner scanner;
        try {
            scanner = new Scanner(new File(fileName), encoding);
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Cannot read " + fileName);
        }

        return sb.toString();
    }
}
