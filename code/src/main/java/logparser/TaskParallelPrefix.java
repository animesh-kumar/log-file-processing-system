package logparser;

import java.util.concurrent.Callable;

/**
 * Created by Animesh on 2/5/2016.
 */
public class TaskParallelPrefix implements Callable<Pair> {

    private int fileNumber;
    private String filePath;

    TaskParallelPrefix(int fileNumber, String filePath) {
        this.fileNumber = fileNumber;
        this.filePath = filePath;
    }

    @Override
    public Pair call() {
        int lineCount = Utils.getLineCountForFile(filePath);
        return new Pair(fileNumber, lineCount);
    }
}
