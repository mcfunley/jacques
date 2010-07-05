import sbt._



class JacquesProject(info : ProjectInfo) extends ProguardProject(info) {
  override def outputDirectoryName = "build"
  override def mainScalaSourcePath = "src"
  override def testScalaSourcePath = "tests"
  override def mainResourcesPath = "lib"

  override def proguardOptions = 
    "-keep class com.etsy.** { *; }" :: proguardKeepAllScala :: Nil

  def scalaLibraryJarPath = Path.fromFile(scalaLibraryJar)
  override def proguardInJars = super.proguardInJars +++ scalaLibraryJarPath

}

