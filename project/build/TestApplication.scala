import sbt._
import Process._
import scala.io.Source
import java.io.File



trait TestApplication extends BasicScalaProject {
  private implicit def path2str(p : Path) = p.asFile.getCanonicalPath

  lazy val makeTestApp = task { makeTestAppAction ; None }

  def makeTestAppAction = if(mustBuildTestApp) {
    path2str(testAppBuildScript) ! log
  }

  lazy val runTestApp = task { runTestAppAction ; None } dependsOn (makeTestApp)

  def runTestAppAction = {
    killTestAppAction
    log.info("running test application.")
    new Thread(new Runnable {
      def run {
        ("java -jar " + testAppJar) ! 
      }
    }).start
    while(!testAppPidfile.exists) {
      Thread.sleep(100)
    }
  }

  lazy val killTestApp = task { killTestAppAction ; None }

  def killTestAppAction = {
    log.info("killing test application")
    if(testAppRunning) {
      ("kill " + testAppPid) ! log
      testAppPidfile.delete
    }
  }

  def testAppRunning = testAppPidfile.exists
  def testAppPidfile = new File("testapp.pid")
  def testAppDir = "tests" / "testapp"
  def testAppSourcePaths = testAppDir ** "*.scala"
  def testAppBuildScript = testAppDir / "make.sh"
  def testAppJar = testAppDir / "testapp.jar"
  def mustBuildTestApp = 
    !testAppJar.asFile.exists || testAppSourcePaths.getPaths.map { 
      Path.fromFile(_) newerThan testAppJar 
    }.reduceLeft(_ || _)
  def testAppPid = Source.fromFile(testAppPidfile).mkString

  override def testListeners = super.testListeners ++ Seq(new TestsListener {
    def doInit = runTestAppAction
    def doComplete(finalResult: Result.Value) = killTestAppAction
    
    def startGroup(name: String) {}
    def testEvent(event: TestEvent) {}
    def endGroup(name: String, t: Throwable) {}
    def endGroup(name: String, result: Result.Value) {}
  })

}
