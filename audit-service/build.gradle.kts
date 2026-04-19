plugins {
      id("org.springframework.boot")
      id("io.spring.dependency-management")
}

dependencies {
      implementation(project(":common"))
      implementation("org.springframework.boot:spring-boot-starter-web")
      implementation("org.springframework.boot:spring-boot-starter-data-jpa")
      implementation("org.springframework.boot:spring-boot-starter-liquibase")
      implementation("org.springframework.boot:spring-boot-kafka")
      implementation("com.fasterxml.jackson.core:jackson-databind")
      implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

      compileOnly("org.projectlombok:lombok")
      annotationProcessor("org.projectlombok:lombok")

      runtimeOnly("org.postgresql:postgresql")

      testImplementation("org.springframework.boot:spring-boot-starter-test")
}
