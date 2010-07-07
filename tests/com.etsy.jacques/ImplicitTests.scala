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

    it("should not puke on an empty arglist") {
      val l = mockedLocation
      when(l.method.argumentTypeNames).thenReturn(List[String]().asJava)
      l.format should not be ("")
    }
  }


  describe("StackFrame.isInPackage") {
    def packageFrame = {
      val f = mockedStackFrame
      when(f.location.declaringType.name).thenReturn("com.etsy.Blah")
      f
    }

    it("should return true when the stack frame is located in the package") {
      packageFrame.isInPackage("com.etsy") should be (true)
    }

    it("should return false when the stack frame is not in the package") {
      packageFrame.isInPackage("com.etsy.blah") should be (false)
    }
  }


  describe("ThreadReference.frameInPackage") {
    it("should return the first frame in a particular package") {
      val t = mockedThread
      t.frameInPackage("com.etsy") should be (Some(t.frame(1)))
    }

    it("should return None when there are no frames in a particular package") {
      mockedThread.frameInPackage("com.twitter") should be (None)
    }
  }

}
