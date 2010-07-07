#!/bin/bash
# invoke from project base directory. 

dir=`dirname $0`

manifest="$dir/manifest.mf"
$dir/clean.sh
echo "Main-Class: com.etsy.tests.Test" > $manifest
sl=`find project -name '*scala-library.jar'`
echo "Class-Path: $PWD/$sl" >> $manifest

build="$dir/build"
mkdir $build
scalac $dir/test.scala -d $build
jar cvfm $dir/testapp.jar $manifest -C $build/ .
