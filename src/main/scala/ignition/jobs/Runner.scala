package ignition.jobs

import ignition.core.jobs.CoreJobRunner
import ignition.jobs.setups._

object Runner {

  // Binds a setup name to a function that will run this setup and (optionally) a custom
  // extra configuration for the given setup
  val availableJobsSetups = Map[String, (CoreJobRunner.RunnerContext => Unit, Map[String, String])](
    // Simple Samples)
    ("SparkTestSetup", (context => SparkTestSetup.run(context), Map.empty))
)


  def main(args: Array[String]) {
    val defaultSparkConf = Map(
      "spark.logConf" -> "true",
      "spark.executor.extraJavaOptions" -> "-Djava.io.tmpdir=/mnt -verbose:gc -XX:-PrintGCDetails -XX:+PrintGCTimeStamps -XX:-UseGCOverheadLimit",
      "spark.akka.frameSize" -> "500",
      "spark.shuffle.memoryFraction" -> "0.2",
      "spark.storage.memoryFraction" -> "0.3",
      "spark.driver.userClassPathFirst" -> "true",
      "spark.executor.userClassPathFirst" -> "true",
      "spark.hadoop.validateOutputSpecs" -> "true",
      "spark.eventLog.enabled" -> "false" // may break the master with big jobs if true, be careful
    )
    CoreJobRunner.runJobSetup(args, availableJobsSetups, defaultSparkConf)
  }
}
