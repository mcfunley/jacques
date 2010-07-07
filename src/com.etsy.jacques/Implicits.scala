package com.etsy.jacques

import com.sun.jdi._
import org.scala_tools.javautils.Imports._


object Implicits {

  implicit def locationImplicits(l : Location) = new LocationImplicits(l)


  class LocationImplicits(l : Location) {
    def format = { 
      val m = l.method
      val t = l.declaringType
      val args = m.argumentTypeNames.asScala.reduceLeft(_+","+_)
      val line = if(l.lineNumber > 0) l.lineNumber.toString else "?"
      "%s %s::%s(%s) (%s:%s)".format(
        m.returnTypeName, t.name, m.name, args, l.sourcePath, line)
    }
  }

}
