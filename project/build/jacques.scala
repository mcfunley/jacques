import sbt._



class JacquesProject(info : ProjectInfo) extends ProguardProject(info) {
  override def outputDirectoryName = "build"
  override def mainScalaSourcePath = "src"
  override def testScalaSourcePath = "tests"
  override def mainResourcesPath = "lib"

  override def proguardOptions = 
    "-keep class com.etsy.** { *; }" :: proguardKeepAllScala :: 
    "-keep class joptsimple.** { *; }" :: 
    "-keep class org.scala_tools.** { *; }" :: Nil

  def scalaLibraryJarPath = Path.fromFile(scalaLibraryJar)
  override def proguardInJars = super.proguardInJars +++ scalaLibraryJarPath

  val maven = "maven repo" at "http://repo2.maven.org/maven2/"

  val jopt = "net.sf.jopt-simple" % "jopt-simple" % "3.2" % "compile"
  val javautils = "org.scala-tools" % "javautils" % "2.7.4-0.1" % "compile"

}

