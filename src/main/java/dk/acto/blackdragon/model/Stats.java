package dk.acto.blackdragon.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
public class Stats {
    private BigInteger evenIds;
    private BigInteger oddIds;
    private BigDecimal meanCost;
    private BigDecimal weightedInventory;
    private BigInteger totalInventory;
}
