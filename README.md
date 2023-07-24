# simple_backend
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
