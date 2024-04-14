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

	// Google
	implementation("com.google.api-client:google-api-client:2.0.0")
	implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
	implementation("com.google.apis:google-api-services-drive:v3-rev20220815-2.0.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
