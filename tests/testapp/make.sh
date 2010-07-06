#!/bin/bash

./clean.sh
echo "Main-Class: Test" > manifest.mf
sl=`find ../../project -name '*scala-library.jar'`
echo "Class-Path: $sl" >> manifest.mf

mkdir build
scalac test.scala -d build
jar cvfm testapp.jar manifest.mf -C build/ .
