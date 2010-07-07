package com.etsy.jacques

import com.sun.jdi._
import Implicits._


object threadSummary {

  def apply(t : ThreadReference) = if(t.frameCount > 0) {
    val sf = t.frame(0)
    val loc = sf.location
    "Thread %s at: %s".format(t.name, loc.format)
  } else "Thread %s at <unknown>.".format(t.name)

}
