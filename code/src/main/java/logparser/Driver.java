package logparser;

/**
 * Created by Animesh on 2/3/2016.
 */
public class Driver {
    public static void main(String [] args) throws Exception{
        if(args == null || args.length < 1){
            System.out.println("USAGE : java logparser.Driver <folder> <threadCount> <newFileExtension>" +
                    "\n folder is the input folder path, can be relative to run location" +
                    "\n threadCount, optional second parameter, default value 1, specifies the number of threads the program should use" +
                    "\n , optional third parameter, if present the output is stored in a new file in the format inputFileName_<newFileExtension>, if absent input file is overwritten");
        }
        String folderName = args[0];
        Integer numberOfThreads = args.length > 1 ? 1 : Integer.parseInt(args[1]);

        LogParser parser = new LogParser();
        parser.generateLineNumbers(folderName, numberOfThreads);
    }

}
