package com.etsy.jacques.tests

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import com.sun.jdi._
import com.etsy.jacques.Implicits._
import Mocks._


class ThreadSummarySpec extends Spec with ShouldMatchers {

  describe("the threadSummary") {

    it("Should print the thread name when there are no stack frames") {
      val t = mock(classOf[ThreadReference])
      when(t.frameCount).thenReturn(0)
      when(t.name).thenReturn("foo")
      threadSummary(t) should be ("Thread foo at <unknown>.")
    }

    it("Should include the thread name") {
      threadSummary(mockedThread) should include ("foothread")
    }

    it("Should summarize the top frame in the stack") {
      val t = mockedThread
      threadSummary(mockedThread) should include (t.frame(0).location.format)
    }

  }

}
