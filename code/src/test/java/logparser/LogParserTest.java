/*
package logparser;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

*/
/**
 * Unit test cases
 *//*

public class LogParserTest {

    @Test
    public void testDirectoryListing() throws Exception{
        String currentDirName = System.getProperty("user.dir");
        File currentDir = new File(currentDirName);
        String [] filesInCurrentDir = currentDir.list();
        Arrays.sort(filesInCurrentDir);
        System.out.println(Arrays.toString(filesInCurrentDir));

        String dirListFile = LogParserUtil.createFileNamesFile(currentDirName);
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
        int numberOfLines = LogParserUtil.lineCount(null, inputFile);
        System.out.println(numberOfLines);
        Assert.assertEquals(3, numberOfLines);
    }

    @Test
    public void testLineCountDir() throws Exception{
        String folderName = "src\\main\\resources\\testInput";
        String dirFiles = LogParserUtil.createFileNamesFile(folderName);
        List<Integer> expectedValues = new ArrayList<>(Arrays.asList(0, 3, 5, 5));
        List<Integer> filesLineCount = LogParserUtil.getLineCount(folderName, dirFiles);
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
        String fileName = "src\\main\\resources\\testInput\\logtest-2016-01-01.log";
        String newFileExtension = "tmp";
        LogParserUtil.addLineNumbers(null, fileName, 1, newFileExtension);
    }

    @Test
    public void testGenerateLineNumbers() throws Exception{
        String folderName = "src\\main\\resources\\testInput";
        String newFileExtension = "tmp";
        String [] files = new File(folderName).list();
        LogParserUtil.generateLineNumbers(folderName, newFileExtension, 1);
        int lineNumber = 1;
        Arrays.sort(files);
        for(String fileName : files){
            if(!Constants.LOG_PATTERN.matcher(fileName).matches()) continue;
            String fullFileName = folderName + File.separator + fileName;
            File inputFile = new File(fullFileName);
            File outputFile = new File(fullFileName + "_" + newFileExtension);
            try(BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
                BufferedReader outputReader = new BufferedReader(new FileReader(outputFile))){
                String inLine;
                String outLine;
                while((inLine = inputReader.readLine()) != null){
                    outLine = outputReader.readLine();
                    Assert.assertEquals(lineNumber + "," + inLine, outLine);
                    lineNumber++;
                }
                Assert.assertNull(outputReader.readLine());

            }
            outputFile.delete();
        }

    }

}
*/
