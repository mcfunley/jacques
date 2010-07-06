package com.etsy.jacques.tests

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import com.sun.jdi._


class ThreadSummarySpec extends Spec with ShouldMatchers {

  describe("the threadSummary") {

    it("Should print the thread name when there are no stack frames") {
      val t = mock(classOf[ThreadReference])
      when(t.frameCount).thenReturn(0)
      when(t.name).thenReturn("foo")
      threadSummary(t) should be ("Thread foo at <unknown>.")
    }

    def mockedThread = { 
      val t = mock(classOf[ThreadReference])
      when(t.frameCount).thenReturn(3)
      when(t.name).thenReturn("foo")

      val loc0 = mock(classOf[Location])
      when(loc0.sourceName).thenReturn("source.name")
      when(loc0.sourcePath).thenReturn("source/path")

      val f0 = mock(classOf[StackFrame])
      when(f0.location).thenReturn(loc0)
      when(t.frame(0)).thenReturn(f0)
      t
    }

    it("Should include the thread name") {
      threadSummary(mockedThread) should include ("foo")
    }

    it("Should summarize the top frame in the stack") {
      threadSummary(mockedThread) should include ("source.name")
    }

  }

}
