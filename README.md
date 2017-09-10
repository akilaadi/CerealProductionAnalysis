# CerealProductionAnalysis
This mapreduce jobs analyses yearly Sri Lankan cereal production.

### How to add more mapreduce jobs

Step 1-
We need to download the Apache Hadoop Release 2.7.4 binaries from http://hadoop.apache.org/releases.html

Step 2-
Set the JAVA_HOME environment variables to your java installation and HADOOP_HOME to the location of extracted hadoop binaries. 

Step 3-
Open the project on IntelliJ and add your Main,Map and Reducer classes to the same package (Refer existing classes). 

Step 4-
Add a application debug configuration similar to the existing "CerealProductionAnalysis". Please refer https://www.jetbrains.com/help/idea/creating-and-editing-run-debug-configurations.html. 
  * Give a suitable name 
  * Set the name of your main class as main class
  * Set "input output" to program arguments
  
Step -5
Degug and test your map reduce job. Make sure your debug configuration created in step 4 is selected on top right dropdown button. If everything works your output is available in the output folder in the project. Please makesure you delete the folder in each run.
  

