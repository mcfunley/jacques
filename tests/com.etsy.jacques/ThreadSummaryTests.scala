package com.etsy.jacques.tests

import org.mockito.Mockito._
import com.sun.jdi._
import com.etsy.jacques.Implicits._
import Mocks._
import org.scala_tools.javautils.Imports._


class ThreadSummarySpec extends BaseSpec {

  describe("the threadSummary") {

    it("should print the thread name when there are no stack frames") {
      val t = mock(classOf[ThreadReference])
      when(t.frameCount).thenReturn(0)
      when(t.name).thenReturn("foo")
      threadSummary(t) should be ("Thread foo at <unknown>.")
    }

    it("should include the thread name") {
      threadSummary(mockedThread) should include ("foothread")
    }

    it("should summarize the top frame in the stack") {
      val t = mockedThread
      threadSummary(mockedThread) should include (t.frame(0).location.format)
    }

    it("should find frames in a particular package when prompted") {
      withArgs("--package", "com.etsy") { 
        threadSummary(mockedThread)
      } should include ("com.etsy.FooBar")
    }

    it("should find multiple frames in a particular package when prompted") {
      val t = mockedThread
      withArgs("--package", "com.etsy") {
        threadSummary(t) should include (t.frame(2).location.format)
      }
    }

    it("should not find frames in a particular package when not prompted") {
      threadSummary(mockedThread) should not include ("com.etsy.FooBar")
    }

    it("should not double-print when the top frame is in the filtered package") {
      withArgs("--package", "com.fizz") {
        threadSummary(mockedThread)
      } should not include ("\n")
    }

  }

}
