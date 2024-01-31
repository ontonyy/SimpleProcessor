# SimpleProcessor
## Kafka implementation
Sending message(`bytes`) and `header` of message type to kafka topic from service and then consumed same topic bytes and special headers with 
defining type of `payload` and converting to special object of this type and process it with `service` methods

(Commonly this payload type using for heartbeats sending and main object payload, but this 
project just example of usages of some technologies. Correctly using different publishers, different topics and 
different listeners for better application designing)

## REST implementation
You can ask me why in this project every rest request invocation goes firstly to kafka and then handling this 
request with kafka message handler and process it with service. I can answer... just because I can, and want to show 
how kafka can work

## Github Actions
### Build on PR
* Works when committing on any PR

Test - have job with steps:
* Checkout / list repo files
* Set up jdk 21
* Cache: sonarcloud / gradle packages
* Analyze with jacoco test report / publish test report
* Send mail to Gmail

### Build on PR merging
* Works when merging PR to develop

Have several jobs
* Build (4 steps)
* * Checkout / Set up jdk 21
* * Cache Gradle packages
* * Analyze with jacoco test report

* Update (3 steps)
* * Login and setup docker
* * Build and push to *hub.docker*

### Build on PR reviewing
* Works when reviewing any PR 

Label when approved - have job with several steps
* Get info about trigger of the run
* PR label when reviewing by anyone