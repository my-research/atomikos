plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.3.4"
  id("io.spring.dependency-management") version "1.1.6"
  kotlin("plugin.jpa") version "1.9.25"
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
  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  runtimeOnly("com.mysql:mysql-connector-j")
  runtimeOnly("org.postgresql:postgresql")

  implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
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
