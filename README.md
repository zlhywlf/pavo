# pavo

[gradle-node-plugin](https://github.com/node-gradle/gradle-node-plugin)
[gradle-python-envs](https://github.com/JetBrains/gradle-python-envs)
[gradle-miniconda-plugin](https://github.com/palantir/gradle-miniconda-plugin)

```groovy
// org.gradle.api.invocation.Gradle
// https://developer.aliyun.com/mvn/guide
allprojects {
    repositories {
        mavenLocal()
        maven {
            name = 'aliyun'
            url = 'https://maven.aliyun.com/repository/public/'
        }
        mavenCentral()
    }
}

settingsEvaluated { settings ->
    settings.pluginManagement {
        repositories {
            maven {
                name = 'aliyun'
                url = 'https://maven.aliyun.com/repository/gradle-plugin'
            }
            gradlePluginPortal()
        }
    }
}
```
