**Tests for _Code Plagiarism Detector_**

Tests for _Code Plagiarism Detector_ are placed in this folder.

The application has a command-line interface, which is enough to run your own tests. All you need to do is to place all source code files you want to check in a separate folder, choose which comparator you want to use and run the app.     

Here are some tests I prepared to demonstrate how _Code Plagiarism Detector_ works.  Each folder contains files with source code and the output file with results. The application creates 2 files: 'log' file, which is a detailed explanation of how 
the work is done and 'report' file, which is pretty formatted results of plagiarism detecting. Probably you want to check only 'report' file when using the app.  

Tests are separated in different folders: 
  
1. Tests, which demonstrates ignoring space characters, line separators, comments and other inessential changes.
2. A small set of real test. Has 4 files in general, 2 of which are equal
3. Tests, which has all main logic placed in a single method (mostly _main()_ method).
4. Big tests. There are 50 source files with different tasks. Some of them (those, which has similar names) are copies of each other. 
Of course, they have some differences. 

I placed 'log' and 'result' files for some comparisons I made inside folders, so you can just look how the app works. 

Hope, you find it useful or at least interesting.