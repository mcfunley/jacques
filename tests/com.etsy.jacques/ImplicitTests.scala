package com.etsy.jacques.tests

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import com.sun.jdi._
import com.etsy.jacques.Implicits._
import Mocks._
import org.scala_tools.javautils.Imports._


class ImplicitTests extends Spec with ShouldMatchers {

  describe("The Location formatter") {
    def fmt = mockedLocation.format

    it("should include the method name") {
      fmt should include (mockedMethod.name)
    }

    it("should include the declaring type name") {
      fmt should include (mockedLocation.declaringType.name)
    }

    it("should include the return type name") {
      fmt should include (mockedMethod.returnTypeName)
    }

    it("should include the argument typenames") {
      fmt should include("int")
    }

    it("should comma-separate argument typenames") {
      val l = mockedLocation
      val m = mockedMethod
      when(m.argumentTypeNames).thenReturn(List("int", "long").asJava)
      when(l.method).thenReturn(m)
      l.format should include ("int,long")
    }

    it("should include the source path") {
      fmt should include (mockedLocation.sourcePath)
    }

    it("should include the line number") {
      fmt should include (":"+mockedLocation.lineNumber)
    }

    it("should give ? as the line number when it is not known") {
      val l = mockedLocation
      when(l.lineNumber).thenReturn(-1)
      l.format should include (":?")
    }
  }


}
