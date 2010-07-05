package com.etsy.jacques

import com.sun.jdi._
import com.sun.jdi.connect.Connector._
import org.scala_tools.javautils.Imports._


object Jacques {
  
  def main(args : Array[String]) {
    val opts = Options(args)
    val vmm = Bootstrap.virtualMachineManager
    val connector = vmm.attachingConnectors.asScala.first
    
    println("Attaching to %s:%s".format(opts.host, opts.port))
    val connargs = connector.defaultArguments
    connargs.get("port").setValue(opts.port.toString)
    connargs.get("hostname").setValue(opts.host)

    connector.attach(connargs)
  }

}
