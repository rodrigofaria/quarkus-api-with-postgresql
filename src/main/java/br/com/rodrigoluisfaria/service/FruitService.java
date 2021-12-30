package br.com.rodrigoluisfaria.service;

import br.com.rodrigoluisfaria.entity.Fruit;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FruitService {

    public void test(List<String> strings) {
        strings.forEach(fruitName -> {
            Fruit fruit = new Fruit();
            fruit.setName(fruitName);
            fruit.persistAndFlush();
        });
    }
}
