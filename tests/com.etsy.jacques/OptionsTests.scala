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

    it("should accept the package when provided") {
      opts("--package", "foo").`package` should be (Some("foo"))
    }

    it("should map the package to None when not provided") {
      opts("").`package` should be (None)
    }

  }

}
