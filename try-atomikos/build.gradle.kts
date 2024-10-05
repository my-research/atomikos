plugins {
  kotlin("jvm") version "1.9.25"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")

  implementation("com.atomikos:transactions-jta:6.0.0")
  implementation("com.atomikos:transactions-jdbc:6.0.0")

  implementation("com.mysql:mysql-connector-j:8.3.0")
  implementation("org.postgresql:postgresql:42.7.4")

  implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("io.kotest:kotest-runner-junit5:5.0.0") // Kotest 엔진
  testImplementation("io.kotest:kotest-assertions-core:5.0.0") // Kotest Assertions
  testImplementation("io.kotest:kotest-property:5.0.0") // Property-based testing
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
