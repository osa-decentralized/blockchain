addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")

//addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.5.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.2")

//addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")

//addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

//resolvers += "Flyway" at "https://davidmweber.github.io/flyway-sbt.repo"
//addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.2.0")

resolvers += Classpaths.sbtPluginReleases
resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"
resolvers += Resolver.url("bintray-sbt-plugins", url("https://dl.bintray.com/sbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)