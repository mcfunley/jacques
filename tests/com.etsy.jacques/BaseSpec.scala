package com.etsy.jacques.tests

import org.scalatest.{Spec, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers


class BaseSpec extends Spec with ShouldMatchers with BeforeAndAfterEach {

  override def beforeEach {
    Jacques.options = Options(Array())
  }


  def withArgs[A](args : String*)(f : =>A) : A = {
    val opts = Jacques.options
    Jacques.options = Options(Array(args : _*))
    try {
      f
    } finally {
      Jacques.options = opts
    }
  }
  
}
