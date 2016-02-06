package logparser;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.Callable;

/**
 * Created by Animesh on 2/5/2016.
 */
public class TaskAddLineNumbers implements Callable<Void>  {

    private String filePath;
    private int lineNumber;

    TaskAddLineNumbers(String filePath, int lineNumber) {
        this.filePath = filePath;
        this.lineNumber = lineNumber;
    }

    @Override
    public Void call() throws Exception {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        try(
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ){
            String line;
            while((line = br.readLine()) != null){
                bw.write(lineNumber + "," + line);
                bw.newLine();
                lineNumber++;
            }

        }catch (FileNotFoundException e){
            throw new Exception(e.getMessage());
        }catch (IOException e){
            throw new Exception(e.getMessage());
        }
        Files.deleteIfExists(inputFile.toPath());
        Files.move(tempFile.toPath(), new File(filePath).toPath());
        return null;
    }
}
