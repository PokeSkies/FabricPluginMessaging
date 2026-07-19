plugins {
    java
    idea
    id("maven-publish")
    id("net.fabricmc.fabric-loom") version ("1.17.12")
}
val modId = project.properties["mod_id"].toString()
version = project.properties["mod_version"].toString()
group = project.properties["mod_group"].toString()

val modName = project.properties["mod_name"].toString()
base.archivesName.set(modName)

val minecraftVersion = project.properties["minecraft_version"].toString()

loom {
    interfaceInjection.enableDependencyInterfaceInjection.set(true)
    splitEnvironmentSourceSets()
    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:${project.properties["loader_version"].toString()}")
    implementation("net.fabricmc.fabric-api:fabric-api:${project.properties["fabric_version"].toString()}")
}

tasks.processResources {
    inputs.property("version", version)
    inputs.property("mod_version", version)

    filesMatching("fabric.mod.json") {
        expand("version" to version)
        expand("id" to modId, "version" to version, "name" to modName)
    }

    filesMatching("**/lang/*.json") {
        expand("id" to modId, "version" to version, "name" to modName)
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        artifactId = base.archivesName.get()
        from(components["java"])
    }

    repositories {
        mavenLocal()
    }
}

tasks.withType<Jar> {
    archiveFileName.set("${project.name}-${project.version}.jar")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(25)
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withSourcesJar()
}

tasks.withType<AbstractArchiveTask> {
    from("LICENSE") {
        rename { "${it}_${modId}" }
    }
}

