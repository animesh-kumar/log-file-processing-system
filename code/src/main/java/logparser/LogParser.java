package logparser;

import java.util.List;

/**
 * Created by Animesh on 2/5/2016.
 */
public class LogParser {
    public void generateLineNumbers(String folderName, int numberOfThreads)throws Exception{
        String dirFiles = Utils.createFileNamesFile(folderName);

        ThreadPool threadPool = new ThreadPool(numberOfThreads, folderName);

        List<Integer> ppList = threadPool.getParallelPrefix(dirFiles);
        threadPool.addLineNumberParallely(ppList, dirFiles);
        //new File(dirFiles).delete();
    }
}
