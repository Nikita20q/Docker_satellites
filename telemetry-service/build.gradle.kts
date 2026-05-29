plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.protobuf") version "0.9.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.projectlombok:lombok:1.18.38")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("io.grpc:grpc-stub:1.62.2")
    implementation("io.grpc:grpc-protobuf:1.62.2")
    implementation("net.devh:grpc-server-spring-boot-starter:3.0.0.RELEASE")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.apache.kafka:kafka-clients")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.apache.tomcat:annotations-api:6.0.53")
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:3.25.5" }
    plugins {
        create("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:1.63.0" }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins { create("grpc") }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/java",
                "build/generated/source/proto/main/grpc")
        }
    }
}


tasks.test {
    useJUnitPlatform()
}