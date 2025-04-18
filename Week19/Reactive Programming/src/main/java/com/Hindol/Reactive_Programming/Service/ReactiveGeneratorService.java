package com.Hindol.Reactive_Programming.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ReactiveGeneratorService {
    public Flux<String> nameFlux() {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe")).log();
    }

    public Mono<String> nameMono() {
        return Mono.just("Alex").log();
    }

    public Flux<String> nameFluxMap() {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> nameFluxImmutability() {
        /* REACTIVE STREAM ARE IMMUTABLE */
        var nameFlux = Flux.fromIterable(List.of("Alex", "Ben", "Chloe"));
        nameFlux.map(String::toUpperCase);
        return nameFlux;
    }

    public Flux<String> nameFluxMapFilter(int length) {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > length)
                .map(s -> s.length() + "-" + s)
                .log();
    }

    Flux<String> splitString(String name) {
        String[] characters = name.split("");
        return Flux.fromArray(characters);
    }

    Flux<String> splitStringWithDelay(String name) {
        String[] characters = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(characters)
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> nameFluxFlatMap(int length) {
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > length)
                .flatMap(this::splitString)
                .log();
    }

    public Flux<String> nameFluxFlatMapAsync(int length) {
        /* FLATMAP DOESN'T ALLOW ORDER */
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > length)
                .flatMap(this::splitStringWithDelay)
                .log();
    }

    public Flux<String> nameFluxConcatMap(int length) {
        /* ENSURE ORDERING BUT TAKE MORE TIME */
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > length)
                .concatMap(this::splitStringWithDelay)
                .log();
    }

    Mono<List<String>> splitStringMono(String s) {
        String[] characters = s.split("");
        return Mono.just(List.of(characters));
    }

    public Mono<List<String>> nameMonoFlatMap() {
        return Mono.just("Alex")
                .map(String::toUpperCase)
                .flatMap(this::splitStringMono);
    }

    public Flux<String> nameMonoFlatMapMany() {
        /* FLATMAP MANY - TRANSFORM MONO - FLUX */
        return Mono.just("Alex")
                .map(String::toUpperCase)
                .flatMapMany(this::splitString);
    }

    public Flux<String> nameFluxTransform(int length) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > length)
                .flatMap(this::splitString);

        var defaultFlux = Flux.just("DEFAULT")
                .transform(filterMap);

        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .transform(filterMap)
                /* .defaultIfEmpty("DEFAULT") */
                .switchIfEmpty(defaultFlux) /* TAKES A PUBLISHER */
                .log();
    }

    public Flux<String> exploreConcat() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, defFlux).log(); /* ENSURE ORDERING */
    }

    public Flux<String> exploreConcatWith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.concatWith(defFlux);
    }

    public Flux<String> exploreConcatWithMono() {
        var aMono = Mono.just("A");
        var dMono = Mono.just("D");
        return aMono.concatWith(dMono).log();
    }

    public Flux<String> exploreMerge() {
        /* INTERLEAVING */
        var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        /* return Flux.merge(abcFlux, defFlux).log(); */
        return abcFlux.mergeWith(defFlux);
    }

    public Flux<String> exploreMergeMono() {
        var aMono = Mono.just("A");
        var dMono = Mono.just("D");
        return aMono.mergeWith(dMono);
    }

    public Flux<String> exploreMergeSequential() {
        /* PROCESS IN PARALLEL, EMIT IN ORDER */
        var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    public Flux<String> exploreZip() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.zip(abcFlux, defFlux, (first, second) -> first + second).log();
    }

    public Flux<String> exploreZipWith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.zipWith(defFlux, (first, second) -> first + second).log();
    }

    public Mono<String> exploreZipWithMono() {
        var aMono = Mono.just("A");
        var dMono = Mono.just("D");
        return aMono.zipWith(dMono)
                .map(t2 -> t2.getT1() + t2.getT2())
                .log();
    }

    public Flux<String> exploreZipWithFourFlux() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        var _123Flux = Flux.just("1", "2", "3");
        var _456Flux = Flux.just("4", "5", "6");

        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
                .log();
    }


    public static void main(String[] args) {
        ReactiveGeneratorService reactiveGeneratorService = new ReactiveGeneratorService();

        /* FLUX -> O - N */
        reactiveGeneratorService.nameFlux()
                .subscribe(System.out::println);

        /* MONO -> 0 - 1 */
        reactiveGeneratorService.nameMono()
                .subscribe(System.out::println);
    }


}
