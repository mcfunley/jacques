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
    when(t.name).thenReturn("com.fizz.FizzBuzz")
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
    val f0 = mockedStackFrame
    when(t.frame(0)).thenReturn(f0)

    val f1 = mockedStackFrame
    val t1 = mock(classOf[ReferenceType])
    when(t1.name).thenReturn("com.etsy.FooBar")
    when(f1.location.declaringType).thenReturn(t1)
    when(t.frame(1)).thenReturn(f1)

    val f2 = mockedStackFrame
    val t2 = mock(classOf[ReferenceType])
    when(t2.name).thenReturn("com.etsy.BlahBlah")
    when(f2.location.declaringType).thenReturn(t2)
    when(t.frame(2)).thenReturn(f2)

    when(t.frames).thenReturn(List(f0, f1, f2).asJava)

    t
  }
  

}
