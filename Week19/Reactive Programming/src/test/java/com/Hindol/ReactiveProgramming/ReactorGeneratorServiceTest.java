package com.Hindol.ReactiveProgramming;

import com.Hindol.ReactiveProgramming.Service.ReactiveGeneratorService;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

public class ReactorGeneratorServiceTest {
    ReactiveGeneratorService reactiveGeneratorService = new ReactiveGeneratorService();
    @Test
    void nameFlux() {
        /* Given */

        /* When */
        var nameFlux = reactiveGeneratorService.nameFlux();

        /* Then */
        StepVerifier.create(nameFlux)
                /* .expectNext("Alex", "Ben", "Chloe") * -> Event */
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void nameFluxMap() {
        var nameFlux = reactiveGeneratorService.nameFluxMap();
        StepVerifier.create(nameFlux)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    @Test
    void nameFluxImmutability() {
        var nameFlux = reactiveGeneratorService.nameFluxImmutability();
        StepVerifier.create(nameFlux)
                .expectNext("Alex", "Ben", "Chloe")
                .verifyComplete();
    }

    @Test
    void nameFluxMapFilter() {
        var nameFlux = reactiveGeneratorService.nameFluxMapFilter(3);
        StepVerifier.create(nameFlux)
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void nameFluxFlatMap() {
        var nameFlux = reactiveGeneratorService.nameFluxFlatMap(3);
        StepVerifier.create(nameFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void nameFluxFlatMapAsync() {
        var nameFlux = reactiveGeneratorService.nameFluxFlatMapAsync(3);
        StepVerifier.create(nameFlux)
                /* .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E") */
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void nameFluxConcatMap() {
        var nameFlux = reactiveGeneratorService.nameFluxConcatMap(3);
        StepVerifier.create(nameFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void nameMonoFlatMap() {
        var nameFlux = reactiveGeneratorService.nameMonoFlatMap();
        StepVerifier.create(nameFlux)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void nameMonoFlatMapMany() {
        var value = reactiveGeneratorService.nameMonoFlatMapMany();
        StepVerifier.create(value)
                .expectNext("A", "L", "E", "X")
                .verifyComplete();
    }

    @Test
    void nameFluxTransform() {
        var nameFlux = reactiveGeneratorService.nameFluxTransform(3);
        StepVerifier.create(nameFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void nameFluxTransformWithEmptyState() {
        var nameFlux = reactiveGeneratorService.nameFluxTransform(6);
        StepVerifier.create(nameFlux)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }

    @Test
    void exploreConcat() {
        var concatFlux = reactiveGeneratorService.exploreConcat();
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreMerge() {
        var mergeFlux = reactiveGeneratorService.exploreMerge();
        StepVerifier.create(mergeFlux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void exploreMergeSequential() {
        var mergeSequentialFlux = reactiveGeneratorService.exploreMergeSequential();
        StepVerifier.create(mergeSequentialFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreZip() {
        var zipFlux = reactiveGeneratorService.exploreZip();
        StepVerifier.create(zipFlux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void exploreZipWithFourFlux() {
        var zipFlux = reactiveGeneratorService.exploreZipWithFourFlux();
        StepVerifier.create(zipFlux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void exploreZipWithMono() {
        var zipWithMono = reactiveGeneratorService.exploreZipWithMono();
        StepVerifier.create(zipWithMono)
                .expectNext("AD")
                .verifyComplete();
    }
}
