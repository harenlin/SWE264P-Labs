# UCI MSWE 2024 Winter - SWE264P Distributed Software Architecture 

## LAB #2

### Code Author
Hao-Lun Lin (laolunl@uci.edu)

### How to run
1. Please go to ```./src/``` directory.
2. Compile the codes: ```javac *.java```.
3. Run the system with 3 input arguments: ```java SystemMain /path/to/Students.txt  /path/to/Courses.txt /path/to/log.txt```.
For example, ```java SystemMain ./../test_env/Students.txt ./../test_env/Courses.txt ./../test_env/log_file.txt```
4. The above steps are summarized into a shell script ```run.sh```, you may run the command ```bash run.sh``` to check the system correctness. Moreover, you can modify the ```test.in``` for the test cases and run the command ```bash run.sh < ./../test_env/test.in``` to check the output as well.

### Overbooking
If the course doesn't cause conflict for the student, but has been overbooked, it will generate the message like this: ```Successful! - [Caution] Course Overbooked!```.
