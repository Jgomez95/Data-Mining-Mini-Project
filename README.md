# Data-Mining-Mini-Project
Project for data mining class

## Steps to compile from the cmd line
1. `cd into the src filez`<br/>
To compile the code use
2. `javac Classify.java`<br/>
The program assumes you have a folder which contains 2 folders, data and train.<br/>
If you have a folder `C:\User\Desktop\data` it assumes you have a subfolder 
`C:\User\Desktop\data\test` and `C:\User\Desktop\data\train` inside the data folder.
3. `java Classify NB [Directory path]`
4. `java Classify KNN [K] [Directory path]`
When testing from the CMD line, KNN gives a memory heap out error :(
