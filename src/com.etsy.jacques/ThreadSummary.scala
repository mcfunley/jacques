package com.etsy.jacques

import com.sun.jdi._
import Implicits._
import Jacques._


object threadSummary {

  def apply(t : ThreadReference) = if(t.frameCount > 0) {
    val sb = new StringBuilder
    val f0 = t.frame(0)
    val loc = f0.location
    sb.append("Thread %s at: %s".format(t.name, loc.format))
    
    t.framesInPackages(options.`package`).filter(_ != f0).foreach { f =>
        sb.append("\n   at ").append(f.location.format)
    }
    
    sb.toString
  } else "Thread %s at <unknown>.".format(t.name)

}
