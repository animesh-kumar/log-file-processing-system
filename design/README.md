# Log File Processing System

## Table of Contents
1. [Objective](#objective)
2. [Design](#design)


## Objective

Log File Processing System is a command line tool intended at processing all log files in the specified directory and prepending a line number to each line in the log file, in a cumulative manner such that the entire directory spans out to be one large log file split in multiple parts under the constraint of 100MB available memory and terabytes of log files to be processed in an efficient manner.

## Design

