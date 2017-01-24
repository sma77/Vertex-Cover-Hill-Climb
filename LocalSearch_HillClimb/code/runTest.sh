#!/bin/bash
cd ..
op=`pwd`
cd ./code/
javac vertexcover.java

path='/Users/mashu/Documents/CSE/Algorithm/Project/DATA/' #please set the path of input file

dataFiles=`ls $path | grep .graph`


Timecut="100"  #here, please set your own time cut for all problems,the unit of Timecut is second
randseed="5947" #please set the random seed here


echo "you have set the time cut as ------"$Timecut" seconds"
for data in $dataFiles
do
	filename=`echo $data | cut -d'.' -f1`
	echo $data
	java vertexcover $path/$data $op/output/$filename'_HillClimb_' $op/output/$filename'_HillClimb_' $Timecut $randseed


done