#!/bin/bash

set -eu

testCodePath=$1
baseDir=$2
tmpDir=$3

oldIFS=$IFS

IFS=/ testCodePathArray=($testCodePath)
IFS=$oldIFS
testCodePathArrayLength=${#testCodePathArray[@]}
testFileName=${testCodePathArray[${testCodePathArrayLength}-1]}

IFS=. tmp=($testFileName)
IFS=$oldIFS
testFileNameWithoutExtention=${tmp[0]}

distJarPath=${tmpDir}/${testFileNameWithoutExtention}.jar

execClass=`echo $testCodePath | sed s/\.kt$/Kt/ | sed  s/"src\/test\/kotlin\/"//`
command="java -cp $distJarPath $execClass -Xss{stack_size:mb}m"
# echo $command # comment out (this output cause wrong answer on judge)
eval $command


# bash ./verify.sh src/test/kotlin/jp/atcoder/library/kotlin/dsu/DSUTest.kt $HOME/Desktop/ac-library-kt .
