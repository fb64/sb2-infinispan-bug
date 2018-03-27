# Spring Boot 2 / JCache / infinispan

Project that show an error while infinispan load config from classpath with jcache provider.


## Build the jar

```shell
./gradlew clean bootJar
```

## Run jar with the bug

```shell
java -jar build/libs sb2-infinispan-bug-0.0.1-SNAPSHOT.jar
```


## Run jar with workaround

```shell
java -Dspring.profiles.active=workaround -jar build/libs sb2-infinispan-bug-0.0.1-SNAPSHOT.jar
```

## Comment
It seems there is a problem with the [DefaultFileLookup](https://github.com/infinispan/infinispan/blob/master/commons/src/main/java/org/infinispan/commons/util/FileLookupFactory.java) class used by the [JCachemanager](https://github.com/infinispan/infinispan/blob/master/jcache/embedded/src/main/java/org/infinispan/jcache/embedded/JCacheManager.java) to load configuration ressource with the given classloader.
