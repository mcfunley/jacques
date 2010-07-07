package com.etsy.jacques.tests


import org.mockito.Mockito._
import com.sun.jdi._
import org.scala_tools.javautils.Imports._
import com.etsy.jacques.Implicits._


object Mocks {

  def mockedMethod = {
    val m = mock(classOf[Method])
    when(m.argumentTypeNames).thenReturn(List("int").asJava)
    when(m.returnTypeName).thenReturn("void")
    when(m.name).thenReturn("foobar")
    val t = mockedReferenceType
    when(m.declaringType).thenReturn(t)
    m
  }


  def mockedReferenceType = {
    val t = mock(classOf[ReferenceType])
    when(t.name).thenReturn("FizzBuzz")
    t
  }


  def mockedLocation = {
    val loc = mock(classOf[Location])
    when(loc.sourcePath).thenReturn("source/path.java")
    val m = mockedMethod
    when(loc.method).thenReturn(m)
    val t = mockedReferenceType
    when(loc.declaringType).thenReturn(t)
    when(loc.lineNumber).thenReturn(42)
    loc
  }


  def mockedStackFrame = {
    val f = mock(classOf[StackFrame])
    val l = mockedLocation
    when(f.location).thenReturn(l)
    f
  }


  def mockedThread = { 
    val t = mock(classOf[ThreadReference])
    when(t.frameCount).thenReturn(3)
    when(t.name).thenReturn("foothread")
    val f = mockedStackFrame
    when(t.frame(0)).thenReturn(f)
    t
  }
  

}
