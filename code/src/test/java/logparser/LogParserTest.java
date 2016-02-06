package logparser;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogParserTest {

    private static int NO_OF_THREADS = 2;

    @Test
    public void testDirectoryListing() throws Exception{
        String currentDirName = System.getProperty("user.dir");
        File currentDir = new File(currentDirName);
        String [] filesInCurrentDir = currentDir.list();
        Arrays.sort(filesInCurrentDir);
        System.out.println(Arrays.toString(filesInCurrentDir));

        String dirListFile = Utils.createFileNamesFile(currentDirName);
        System.out.println(dirListFile);
        File dirFileList = new File(dirListFile);
        String [] filesInFileList;
        try(
                BufferedReader br = new BufferedReader(new FileReader(dirFileList))
                ){
            ArrayList<String> arr = new ArrayList<>();
            String line;
            while((line = br.readLine())!= null){
                if(line.equals(dirFileList.getName())) continue;
                arr.add(line);
            }
            filesInFileList = Arrays.copyOf(arr.toArray(), arr.size(), String[].class);
        }
        System.out.println(Arrays.toString(filesInFileList));
        dirFileList.delete();
        Assert.assertArrayEquals(filesInCurrentDir, filesInFileList);

    }

    @Test
    public void testLineCount() throws Exception{
        String inputFile = "src\\main\\resources\\testInput\\logtest-2016-01-01.log";
        int numberOfLines = Utils.getLineCountForFile(inputFile);
        System.out.println(numberOfLines);
        Assert.assertEquals(3, numberOfLines);
    }

    @Test
    public void testLineCountDir() throws Exception{
        String folderName = "src\\main\\resources\\testInput";
        String dirFiles = Utils.createFileNamesFile(folderName);
        List<Integer> expectedValues = new ArrayList<>(Arrays.asList(0, 3, 5, 5));
        ThreadPool threadPool = new ThreadPool(NO_OF_THREADS, folderName);
        List<Integer> filesLineCount = threadPool.getParallelPrefix(dirFiles);
        new File(dirFiles).delete();
        Assert.assertEquals(expectedValues, filesLineCount);
    }

    @Test
    public void testFileNameRegex(){
        String validFileName = "logtest-2016-01-02.log";
        String invalidFileName = "test";
        boolean valid = Constants.LOG_PATTERN.matcher(validFileName).matches();
        boolean inValid = Constants.LOG_PATTERN.matcher(invalidFileName).matches();
        System.out.println(valid);
        System.out.println(inValid);
    }

    @Test
    public void testLineAddition() throws Exception{
        String folderName = "src\\main\\resources\\testInput";
        LogParser.generateLineNumbers(folderName, NO_OF_THREADS);
    }

    @Test
    public void testGenerateLineNumbers() throws Exception{
        String folderName = "src\\main\\resources\\testInput";
        String newFileExtension = "";
        String [] files = new File(folderName).list();
        LogParser.generateLineNumbers(folderName, NO_OF_THREADS);
        int lineNumber = 1;
        Arrays.sort(files);
        for(String fileName : files){
            if(!Constants.LOG_PATTERN.matcher(fileName).matches()) continue;
            String fullFileName = folderName + File.separator + fileName;
            File inputFile = new File(fullFileName);
            File outputFile = new File(fullFileName);
            try(BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
                BufferedReader outputReader = new BufferedReader(new FileReader(outputFile))){
                String inLine;
                String outLine;
                while((inLine = inputReader.readLine()) != null && !inLine.isEmpty()){
                    outLine = outputReader.readLine();
                    Assert.assertEquals(inLine, outLine);
                    lineNumber++;
                }
                Assert.assertNull(outputReader.readLine());

            }
            outputFile.delete();
        }
    }
}
