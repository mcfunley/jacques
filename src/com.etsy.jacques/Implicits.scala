package com.etsy.jacques

import com.sun.jdi._
import org.scala_tools.javautils.Imports._


object Implicits {

  implicit def locationImplicits(l : Location) = new LocationImplicits(l)
  implicit def threadImplicits(t : ThreadReference) = new ThreadImplicits(t)
  implicit def stackFrameImplicits(f : StackFrame) = new StackFrameImplicits(f)

  class LocationImplicits(l : Location) {
    def format = { 
      val m = l.method
      val t = l.declaringType
      val args = m.argumentTypeNames.asScala match {
        case as if as.length > 0 => as.reduceLeft(_+","+_)
        case _ => ""
      }

      val line = if(l.lineNumber > 0) l.lineNumber.toString else "?"
      "%s %s::%s(%s) (%s:%s)".format(
        m.returnTypeName, t.name, m.name, args, l.sourcePath, line)
    }
  }


  class ThreadImplicits(t : ThreadReference) {
    def frameInPackage(packageName : String) : Option[StackFrame] = 
      framesInPackage(packageName) match {
        case f :: fs => Some(f)
        case Nil => None
      }

    def framesInPackage(packageName : String) : List[StackFrame] = 
      framesInPackages(List(packageName))

    def framesInPackages(packageNames : Seq[String]) : List[StackFrame] = 
      t.frames.asScala.filter(_.isInPackages(packageNames)).toList


  }
  
  class StackFrameImplicits(f : StackFrame) {
    def isInPackage(packageName : String) : Boolean = 
      isInPackages(List(packageName))

    def isInPackages(packageNames : Seq[String]) : Boolean = {
      val tn = f.location.declaringType.name
      packageNames.exists(tn startsWith _)
    }

  }

}
