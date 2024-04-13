plugins {
	java
	id("org.springframework.boot") version "3.1.10"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Database
	runtimeOnly("com.mysql:mysql-connector-j")

	// Security
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

	// Gson
	implementation("com.google.code.gson:gson:2.8.9")

	// Feign
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
