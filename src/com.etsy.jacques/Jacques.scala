package com.etsy.jacques

import com.sun.jdi._
import com.sun.jdi.connect.Connector._
import org.scala_tools.javautils.Imports._


object Jacques {

  def suspending[A](vm : VirtualMachine)(f : =>A) = {
    vm.suspend
    try {
      f
    } finally {
      vm.resume
    }
  }
  
  
  def main(args : Array[String]) {
    val opts = Options(args)
    val vmm = Bootstrap.virtualMachineManager
    val connector = vmm.attachingConnectors.asScala.first
    
    println("Attaching to %s:%s".format(opts.host, opts.port))
    val connargs = connector.defaultArguments
    connargs.get("port").setValue(opts.port.toString)
    connargs.get("hostname").setValue(opts.host)

    val vm = connector.attach(connargs)
    suspending(vm) {
      threadSummary(vm)
    }
  }


  private def threadSummary(vm : VirtualMachine) {
    println("\nThread Summary")
    var i = 0
    vm.allThreads.asScala.foreach { t =>
      if(t.frameCount > 0) {
        val sf = t.frame(0)
        val loc = sf.location
        println("%s. Thread %s at %s (%s)".format(
          i, t.name, loc.sourceName, loc.sourcePath))
      } else {
        println("%s. Thread %s at <unknown>.".format(i, t.name))
      }
      i += 1
    }
  }


}
