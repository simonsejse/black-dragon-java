package dk.acto.blackdragon;

import dk.acto.blackdragon.model.Model;
import dk.acto.blackdragon.model.Stats;
import dk.acto.blackdragon.service.ModelTransformer;
import io.vavr.collection.List;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ModelTransformerImpl implements ModelTransformer<Model, Stats> {
    private Stats apply(Stats accumulator, Model elm) {
        if (elm.getId() % 2 == 0) accumulator.setEvenIds(
                accumulator.getEvenIds().add(BigInteger.ONE)
        );
        else accumulator.setOddIds(
                accumulator.getOddIds().add(BigInteger.ONE)
        );

        BigDecimal cost = BigDecimal.valueOf(elm.getCost(), 2);
        accumulator.setMeanCost(accumulator.getMeanCost().add(cost));

        BigDecimal weightedInventory = BigDecimal.valueOf(elm.getWeight() * elm.getInventory());
        accumulator.setWeightedInventory(
                accumulator.getWeightedInventory().add(weightedInventory)
        );

        BigInteger totalInventory = BigInteger.valueOf(elm.getInventory());
        accumulator.setTotalInventory(
                accumulator.getTotalInventory().add(totalInventory)
        );
        return accumulator;
    }

    @Override
    public Stats transform(List<Model> models) {

        Stats stats = Stats.builder()
                .evenIds(BigInteger.ZERO)
                .oddIds(BigInteger.ZERO)
                .meanCost(BigDecimal.ZERO)
                .weightedInventory(BigDecimal.ZERO)
                .totalInventory(BigInteger.ZERO)
                .build();

        stats = models.foldLeft(stats, this::apply);
        stats.setMeanCost(BigDecimal.valueOf(stats.getMeanCost().intValue() / models.size()));
        return stats;
    }
}
