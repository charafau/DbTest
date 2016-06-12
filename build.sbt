androidBuild

scalaVersion := "2.11.8"

javacOptions in Compile ++= "-source" :: "1.7" :: "-target" :: "1.7" :: Nil

platformTarget in Android := "android-23"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  "com.fortysevendeg" %% "mvessel-android" % "0.2-SNAPSHOT",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4")

packagingOptions in Android := PackagingOptions(excludes = Seq("META-INF/LICENSE.txt",
  "META-INF/LICENSE",
  "META-INF/NOTICE.txt",
  "META-INF/NOTICE"))

typedResources in Android := true

proguardScala in Android := true

proguardOptions in Android ++= Seq(
  "-ignorewarnings",
  "-keep class scala.Dynamic",
  "-keep class scala.concurrent.ExecutionContext",
  "-keep class com.fortysevendeg.mvessel.AndroidDriver",
  "-keep class slick.driver.SQLiteDriver"
)