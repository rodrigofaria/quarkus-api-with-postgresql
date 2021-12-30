package br.com.rodrigoluisfaria.resource;

import br.com.rodrigoluisfaria.entity.Fruit;
import br.com.rodrigoluisfaria.service.FruitService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/fruits")
@ApplicationScoped
public class FruitResource {

    @Inject
    FruitService fruitService;

    @GET
    public Uni<List<Fruit>> get() {
        return Fruit.listAll(Sort.by("name"));
    }

    @GET()
    @Path("/test1")
    public Uni<Response> test1() {
        Fruit fruit2 = new Fruit();
        fruit2.setName("TesteDeFruta");
        //fruit2.persistAndFlush();
        Panache.<Fruit>withTransaction(fruit2::persist);


        Fruit fruit = new Fruit();
        fruit.setName("Melancia");

        return Panache.<Fruit>withTransaction(fruit::persist)
                .onItem()
                .transform(inserted -> Response.created(URI.create("/fruits/" + inserted.id))
                        .build());
    }

    @GET()
    @Path("/test2")
    public Uni<List<Fruit>> test2() {
        return Fruit.listAll(Sort.by("name"));
    }

    @GET()
    @Path("/test3")
    public Uni<List<Fruit>> test3() {
        List<String> fruitList = List.of("Manga", "Abacaxi", "Uva", "Melão");

        Uni.createFrom()
                .item(fruitList)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .subscribe()
                .with(fruitService::test, Throwable::printStackTrace);

        return Fruit.listAll(Sort.by("name"));
    }
}
