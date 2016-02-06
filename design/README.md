# Log File Processing System

## Table of Contents
1. [Objective](#objective)
2. [Design](#design)


## Objective

Log File Processing System is a command line tool intended at processing all log files in the specified directory and prepending a line number to each line in the log file. Prepending of line number is done in a cumulative manner such that the entire directory spans out to be one large log file split in multiple parts. The program will run under the constraint of 100MB available memory and terabytes of log files to be processed in an efficient manner.

## Design

### Algorithm
1. The entry point of the program is Driver. The Driver takes in two arguments, Path of the log file directory and the number of threads the user wants to run for processing the log files. 
2. Driver class delegates the request to generate line numbers for every valid log file to LogParser. 
3. LogParser creates a ThreadPool with a fixed thread pool of size specified by the user. 
4. LogParser after creating the thread pool, delegates the request for computing the cumulative line numbers for each file in the directory to ThreadPool. ThreadPool processes this request in a batch of ten thousand files in one shot (fixed currently to this value) until all the files are processed. Each thread namely TaskParallelPrefix takes filenumber (sorted alphabetically) and file path and generates total number of lines for that specific filenumber.
5. Once the line numbers are generated for each file, ThreadPool, calculates the cumulative sum for each file number, so that we get a value from where the prepending of number for that file should start.
6. TheadPool now delegates the prepending of line numbers into each file to TaskAddLineNumbers, which is executed in parallel to prepend line numbers into each file.

### Executing the Project
Run the Driver class with the requied program arguments
The program takes two argument:-<br/>
1. Relative or absolute path of the directory containing the log files <br/>
2. Number of threads user wants in the execution<br/>

Sample Program Argument :-
```
 "C:\log-file-processing-system\code\src\main\resources\testInput"  4
```
