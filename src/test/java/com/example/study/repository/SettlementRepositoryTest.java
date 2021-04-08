package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Settlement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

class SettlementRepositoryTest extends StudyApplicationTests {

    @Autowired
    private SettlementRepository settlementRepository;

    @Test
    public void create(){
        Settlement settlement = Settlement.builder()
                .price(BigDecimal.valueOf(123412))
                .build();

        Settlement newSettlement = settlementRepository.save(settlement);

        Assertions.assertNotNull(newSettlement);
    }

    @Test
    @Transactional
    public void read(){

        Optional<Settlement> optional = settlementRepository.findById(1L);

        optional.ifPresent(settlement -> {
            System.out.println(settlement.getId());
            System.out.println(settlement.getPrice());
        });
    }

}