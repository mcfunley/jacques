package com.etsy.jacques

import com.sun.jdi._


object threadSummary {

  def apply(t : ThreadReference) = if(t.frameCount > 0) {
    val sf = t.frame(0)
    val loc = sf.location
    "Thread %s at %s (%s)".format(t.name, loc.sourceName, loc.sourcePath)
  } else "Thread %s at <unknown>.".format(t.name)

}
