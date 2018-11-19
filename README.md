# MathBot

#### Learn counting to calculus by programming a robot!

### How the project is funded
Mathbot is a 100% volunteer funded project.
Contributors are not paid, 
but their efforts are tracked
and if tips are provided to mathbot
those tips are distributed to contributors.

Playing all of the levels on mathbot
will always be free for everyone around the world
and it is an open source project
so anyone is free to copy all of the work
we have done to date as their own starting point
for building a competing application.

We want excellent competitors because we want
kids to be rescued from the mental abuse
and discouragement they often suffer at the hands
of poor quality math teachers.

In the coming months we also plan to release
a premium experience that will allow
parents, grandparents or anyone else
to reward kids with bitcoin for mastering 
programming concepts.

While we already encourage parents
to provide additional incentives and
rewards for completing levels
we think the automatic and immediate
in game rewards will be even better
at helping to make math and programming 
an enjoyable experience.

For example you might buy "Algebra" for $50
for a child or grandchild.
In that case the student will be rewarded with $45,
at maybe $3 per level,
for learning Algebra and passing the 15 levels.

The remaining $5 will be distributed to 
past volunteers as a tip.

For more details on how tips are distributed
to contributors see:
https://github.com/JWWeatherman/tipping_tokens

## Running The App

### Prerequisites

  - Java -v 1.8
    - [Java](https://java.com/en/download/)
  - Scala -v 2.11.11
    - [Scala](https://www.scala-lang.org/download/)
  - NodeJS -v 7.10.1
    - Use [N](https://github.com/tj/n)
  - WebPack installed globally
    - ```npm install webpack -g```
  - MongoDB install globally
    - Goto [MongoDb](https://docs.mongodb.com/manual/installation/)
    
### Suggested IDE

The project has been built thus far with Intellij
   - [Intellij](https://www.jetbrains.com/idea/) 
     - Plugins
        - Scala Plugin
        - VueJs Plugin
        - ScalaFmt Plugin 

### Getting started

#### Github and Google api keys
You will need keys and secrets for these services

- Google API w/Google+ enabled
- Github
- SendGrid

Add the following to your environment, then restart your OS
```
 mathbot_oauth_google_clientId="your_id"
 mathbot_oauth_google_clientSecret="your_secret"
 mathbot_oauth_github_clientId="your_id"
 mathbot_oauth_github_clientSecret="your_secret"
 mathbot_admin_custodianEmail="your_email"
 mathbot_sendgrid_secretKey="your_sendgrid_secret"
```

FYI - if you are using Intellij be sure to do a full restart of your system after you set these env variables.

#### Starting and build app

* This application is not using any of the Scala play views and all the views are served by the [Vue](https://vuejs.org/) code base which is inside the `ui` folder.

* Use any of the sbt commands listed in the below according to the requirement which are working fine with this application.(To see more details of [sbt](http://www.scala-sbt.org/))

``` 
    sbt clean               # Clear existing build files
    
    sbt stage               # Build your application from your project’s source directory 
                     
    sbt run                 # Run both backend and frontend builds in watch mode
    
    sbt dist                # Build both backend and frontend sources into a single distribution
               
    !! sbt test             # Run both backend and frontend unit tests !! *coming soon!  
```

### Game Rules to Pass A Step

1) Program must be completely finished running
2) Robot must not be carrying anything
3) Robot must be standing on the portal
4) Correct amount of items must be placed on portal (if problem exists)
5) Special parameters must be met (if any)

### Implementing New Levels

- see `app/level_gen/README.md`
