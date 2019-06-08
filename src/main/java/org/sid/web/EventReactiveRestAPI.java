package org.sid.web;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * EventReactiveRestAPI
 */
@RestController
public class EventReactiveRestAPI {
@GetMapping(value = "/listEvents/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
Flux<Event> listEvents(@PathVariable String id){
    Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
  Flux<Event> listEvents = Flux.fromStream(Stream.generate(()->{
      Event e = new Event();
      e.setInstant(Instant.now());
      e.setPrice(Math.random()*1000);
      e.setSocieteID(id);
      return e;
  }));
  return Flux.zip(interval, listEvents)
  .map(data->{
      return data.getT2();
});
}
  @GetMapping("/show")  
  public String show(){
      return "MMMMM";
  }
}
/**
 * Event
 */

class Event {
    private Instant instant;
    private double price;
    private String societeID;

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSocieteID() {
        return societeID;
    }

    public void setSocieteID(String societeID) {
        this.societeID = societeID;
    }
}