To run our HillClimb code, please change the system path to 'group-E_HillClimb/code/' folder. Then run 'runTest.sh' file
You can change the input file path as you like.
The timecut and randomseed can be set by you guys, also in 'run Test.sh' file. In the results shown in 'output' file, the timecut is 100 seconds and the randomseed is from 5947.

The maximum cutoff time is 10 min, if the time of code runs less than the Timecut, it will run the next randomseed automatically until it reaches the Timecut, it can run at most 10 randomseeds. For each randomseed it will output a solution file and trace file record the best solution and trace for this randomseed.
