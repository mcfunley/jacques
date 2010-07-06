import sbt._
import Process._


class JacquesProject(info : ProjectInfo) extends ProguardProject(info) 
  with TestApplication {

  override def outputDirectoryName = "build"
  override def mainScalaSourcePath = "src"
  override def testScalaSourcePath = "tests"
  override def mainResourcesPath = "lib"

  def keep(pkg : String) = "-keep class %s.** { *; }".format(pkg)

  override def proguardOptions = 
    keep("com.etsy") :: proguardKeepAllScala :: 
    keep("joptsimple") :: keep("org.scala_tools") :: keep("com.sun.jdi") :: Nil
    
  def scalaLibraryJarPath = Path.fromFile(scalaLibraryJar)
  override def proguardInJars = super.proguardInJars +++ scalaLibraryJarPath

  val maven = "maven repo" at "http://repo2.maven.org/maven2/"

  val jopt = "net.sf.jopt-simple" % "jopt-simple" % "3.2" % "compile"
  val javautils = "org.scala-tools" % "javautils" % "2.7.4-0.1" % "compile"

  val scalatest = "org.scalatest" % "scalatest" % "1.0" % "test->default"

}

