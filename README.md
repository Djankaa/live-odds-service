**Live Odds Service**

Service consist of few parts:

**MatchService** <br/>
Service which handles logic for starting/updating/finishing a match.

**ScoreboardService** <br/>
Entry point for previewing the scoreboard, provides an interface as well for outgoing adapter.

**LiveOdds** <br/>
Is used for generating messages that will feed the FeedService.

**FeedService** <br/>
Runs as a singleton thread waiting for LiveOdds messages to income to execute actions on matches.

<br/>
Architecturally service is using port & adapters approach.
This allows changing incoming adapter the current implementation is a **MatchSimulator**.
For saving adapter we are using a **inmemoryscoreboard**

Using this approach we can change incoming adapter to be a REST API, Web interface, Kafka etc..
Only changes that would be needed are how the data comes in, no change on business layer.

The same is for the outgoing part of application, if we wanted to change from in-memory to sending messages
to Kafka/RMQ or persisting them to database it would only require changing the adapter implementation.

<br/>

**MatchSimulator**

It is possible to configure how long will a match last.

**Configuration can be change inside resources/application.yml**

A match is divided into 90 segments, each segment representing one minute. 
The least amount a simulation can last is 90 seconds.
- Example #1 - 90 second duration, 1 second represents 1 minute in real match.
- Example #2 - 450 second duration, 5 second represents 1 minute in real match.

Default configuration is 180 seconds duration, 2 seconds represent 1 minute in real match.

<br/>

**HOW TO USE**

Start the project

**Please go to localhost:8080/scoreboard**

Upon starting:

The FeedService starts running awaiting for new 
messages to handle. <br/>
The MatchSimulator has been started, the simulator will start sending data.
- 5 seconds after starting two new matches will come
- 10 seconds after starting, two new matches will come
- 15 seconds after starting, one new match will come

In total simulator will send 5 matches.
You don't have the need to refresh the page, data should change in real-time.
After the match has been finished it should disappear from the scoreboard.


You can add a new match in the simulator by adding
```
MatchSimulatorEvent matchEvent5 = new MatchSimulatorEvent(
    matchService,
    matchSimulatorConfig,
    "Portugal",
    "Switzerland");
    
matchEvent5.start();
```
