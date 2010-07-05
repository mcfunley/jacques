package com.etsy.jacques

import joptsimple.{OptionParser, OptionSet}
import org.scala_tools.javautils.Imports._


class Options(opts : OptionSet) {
  lazy val port = opts.valueOf("port").asInstanceOf[Int]
  lazy val host = opts.valueOf("host").toString

}


object Options {
  
  def apply(args : Array[String]) = {
    val p = new OptionParser
    p.acceptsAll(List("?", "help").asJava, "Display help.")
    p
      .acceptsAll(List("p", "port").asJava, "Port to connect to.")
      .withRequiredArg
      .ofType(classOf[java.lang.Integer])
      .defaultsTo(1044)

    p
      .acceptsAll(List("h", "host").asJava, "Host to connect to.")
      .withRequiredArg
      .ofType(classOf[java.lang.String])
      .defaultsTo("localhost")

    val opts = p.parse(args : _*)

    if(opts.has("?")) {
      p.printHelpOn(System.out)
      System.exit(0)
    }
    new Options(opts)
  }

}
