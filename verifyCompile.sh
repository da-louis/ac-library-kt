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

command="kotlinc ${baseDir}/src/main/kotlin ${baseDir}/${testCodePath} -include-runtime -d $distJarPath -XXLanguage:+InlineClasses -language-version 1.3"
echo $command
eval $command


# bash ./verify.sh src/test/kotlin/jp/atcoder/library/kotlin/dsu/DSUTest.kt $HOME/Desktop/ac-library-kt .
