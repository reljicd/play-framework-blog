# Play Framework Blog Demo

## About

This is a demo project for practicing Play Framework + Twirl. 
The idea was to build some basic blogging platform.

It was made using **Java 8**, **Play Framework**, **Twirl**, **Ebean**, **Guice**. 
Database is in memory **H2**.
Testing is done using **Junit**.

There is a login and registration functionality included.

User has his own blog page, where he can add new blog posts. 
Every authenticated user can comment on posts made by other users.
Home page is paginated list of all posts.
Non-authenticated users can see all blog posts, but cannot add new posts or comment.

## How to run

You can run the application from the command line with [SBT](http://www.scala-sbt.org/download.html). 

### Default

Go to the root folder of the application and type:
```bash
$ cd my-first-app
$ sbt run
```

### Using Play console

Alternatively, you can run Play console. Change to the directory of your project, and run sbt:
```bash
$ cd my-first-app
$ sbt
``` 
And you will see something like:
```bash
[info] Loading global plugins from /Users/play-developer/.sbt/0.13/plugins
[info] Loading project definition from /Users/play-developer/my-first-app/project
[info] Updating {file:/Users/play-developer/my-first-app/project/}my-first-app-build...
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[info] Set current project to my-first-app (in build file:/Users/play-developer/my-first-app/)
[my-first-app] $
```
To run the current application in development mode, use the run command:
```bash
[my-first-app] $ run
```

### Docker

It is also possible to run the blog app using Docker:

Build the Docker image:
```bash
$ docker build -t reljicd/play-blog -f docker\Dockerfile .
```

Run the Docker container:
```bash
$ docker run --rm -i -p 9000:9000 reljicd/play-blog
```

#### Helper Script

It is possible to run all of the above with helper script:

```bash
$ chmod +x scripts/run_docker.sh
$ scripts/run_docker.sh
```

## Post Installation

The application should be up and running within a few seconds.

Go to the web browser and visit `http://localhost:9000/blog`

User username: **user**

User password: **password**

## How to test

The location for tests is in the **test** folder. 

### Using SBT console

You can run tests from the SBT console.
Change to the directory of your project, and run *sbt*:
```bash
$ cd my-first-app
$ sbt
``` 
To run all tests, run *test*:
```bash
[my-first-app] $ test
```
To run only one test class, run *testOnly* followed by the name of the class i.e. *testOnly my.namespace.MyTest*:
```bash
[my-first-app] $ testOnly my.namespace.MyTest
```
To run only the tests that have failed, run *testQuick*:
```bash
[my-first-app] $ testQuick
```
To run tests continually, run a command with a tilde in front, i.e. *~testQuick*:
```bash
[my-first-app] $ ~testQuick
```
To access test helpers such as FakeApplication in console, run *test:console*:
```bash
[my-first-app] $ test:console
```

Testing in Play is based on [SBT](http://www.scala-sbt.org/download.html), and a full description is available in the [testing documentation](http://www.scala-sbt.org/release/docs/Testing.html).

### Default

Go to the root folder of the application and type:
```bash
$ cd my-first-app
$ sbt test
```

### Docker

It is also possible to run tests using Docker:

Build the Docker image:
```bash
$ docker build -t reljicd/play-blog -f docker\Dockerfile .
```

Run the Docker container:
```bash
$ docker run --rm reljicd/play-blog test
```

## Helper Tools

### H2 Database web interface

You can browse the contents of your database by typing *h2-browser* at the sbt shell:
```bash
$ cd my-first-app
$ sbt run
[my-first-app] $ h2-browser
```
An SQL browser will run in your web browser.

In field **JDBC URL** put 
```
jdbc:h2:file:$WORKING_DIRECTORY/play;AUTO_SERVER=TRUE
```
where *$WORKING_DIRECTORY* should be substituted with the full path of app directory.