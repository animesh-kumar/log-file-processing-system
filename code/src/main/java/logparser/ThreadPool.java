package logparser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static logparser.Utils.getLineCountForFile;

/**
 * Created by Animesh on 2/5/2016.
 */
public class ThreadPool {
    private int nThreads;
    private String dirName;

    private final static int BATCH_SIZE = 10000;

    ThreadPool(int nThreads, String dirName) {
        this.nThreads = nThreads <= 0 ? 1 : nThreads;
        this.dirName = dirName;
    }

    private void addFutureOnList(List<Future<Pair>> list, List<Integer> ppList) throws Exception {
        for (Future<Pair> _pair : list) {
            Pair pair = _pair.get();
            ppList.set(pair.fileNumber, pair.lineCount);
        }
        list.clear();
    }

    public List<Integer> getParallelPrefix(String dirFiles) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        int totalFiles = getLineCountForFile(dirFiles);
        List<Integer> ppList = new ArrayList<>();
        for (int i = 0; i < totalFiles; i++) ppList.add(0);

        try (BufferedReader br = new BufferedReader(new FileReader(new File(dirFiles)))) {

            List<Future<Pair>> list = new ArrayList<>();
            int fileNumber = 0;
            String line;
            while ((line = br.readLine()) != null) {
                //case for valid files
                if (Constants.LOG_PATTERN.matcher(line).matches()) {
                    String filePath = dirName + File.separator + line;
                    Future<Pair> submit = executor.submit(new TaskParallelPrefix(fileNumber, filePath ));
                    list.add(submit);
                    if (list.size() == BATCH_SIZE) {
                        addFutureOnList(list, ppList);
                    }
                } else {
                    ppList.set(fileNumber, 0);
                }
                fileNumber++;
            }
            if (list.size() > 0) {
                addFutureOnList(list, ppList);
            }
            executor.shutdown();
        }

        //parallel prefix has to be serial O(n) just addition/cumulative sum
        for (int i = 1; i < ppList.size(); i++) {
            ppList.set(i, ppList.get(i - 1) + ppList.get(i));
        }
        return ppList;
    }

    public void addLineNumberParallely(List<Integer> ppList, String dirFiles) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        try (BufferedReader br = new BufferedReader(new FileReader(new File(dirFiles)))) {

            List<Future<Void>> list = new ArrayList<>();
            int fileNumber = 0;
            String line; int index = 0;
            while ((line = br.readLine()) != null) {
                //case for valid files
                if (Constants.LOG_PATTERN.matcher(line).matches()) {
                    String filePath = dirName + File.separator + line;
                    Future<Void> submit = executor.submit(new TaskAddLineNumbers(filePath, getPreviousIndex(index, ppList)));
                    list.add(submit);
                    if (list.size() == BATCH_SIZE) {
                        for (Future<Void> future : list) {
                            future.get();
                        }
                        list.clear();
                    }
                } else {
                    ppList.set(fileNumber, 0);
                }
                fileNumber++;
                index++;
            }
            for (Future<Void> future : list) future.get();
            executor.shutdown();
        }
    }

    private int getPreviousIndex(int index, List<Integer> ppList) {
        if (index == 0) return 0;
        return ppList.get(index - 1) + 1;
    }
}
