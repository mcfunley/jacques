package com.etsy.jacques.tests

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers


class OptionsSpec extends Spec with ShouldMatchers {

  def opts(ss : String*) = Options(Array(ss : _*))

  describe("The option parser") {
    it("should accept the port") {
      opts("--port", "666").port should be (666)
    }

    it("should accept the hostname") {
      opts("--host", "foo").host should be ("foo")
    }
  }

}
