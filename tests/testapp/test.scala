package com.etsy.test


import scala.actors.Actor
import scala.actors.Actor._
import java.lang.management.ManagementFactory
import java.io.FileWriter


object Test {

  def main(args : Array[String]) {
    writePid
    System.in.read
  }


  private def writePid = {
    val pid = ManagementFactory.getRuntimeMXBean.getName.split("@")(0)
    val fw = new FileWriter("testapp.pid")
    try {
      fw.write(pid)
    } finally {
      fw.close
    }
  }

}
