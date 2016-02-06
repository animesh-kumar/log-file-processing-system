package logparser;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

/**
 * Created by Animesh on 2/4/2016.
 */
public class Utils {

    public static String createFileNamesFile(String folderName) throws Exception{
        File dir = new File(folderName);
        if(!dir.exists() || !dir.isDirectory()){
            throw new Exception("LogParserUtil : createFileNamesFile : Folder name is not a directory");
        }
        String outputFileName = folderName + File.separator + "dirFileList.txt";
        String command;
        if(System.getProperty("os.name").startsWith("Windows")) {
            command = "cmd /c dir \"" + folderName + "\" /b >> \"" + outputFileName + "\"";
        }else{
            command = "bash - c ls " + folderName + " >> " + outputFileName;
        }

        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();

        return outputFileName;
    }

    public static int getLineCountForFile(String filePath) {
        int lineCount = 0;
        try (LineNumberReader reader = new LineNumberReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null && !line.isEmpty()) lineCount++;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return lineCount;
    }
}
