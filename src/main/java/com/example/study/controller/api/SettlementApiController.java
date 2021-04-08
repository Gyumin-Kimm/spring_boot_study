package com.example.study.controller.api;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.Settlement;
import com.example.study.model.entity.User;
import com.example.study.repository.SettlementRepository;
import com.example.study.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;


/*
************ 'settlement' Table create query **************
CREATE TABLE `study`.`settlement` (
  `id` BIGINT NOT NULL,
  `price` DECIMAL(12,4) NULL,
  PRIMARY KEY (`id`));
 */
@Slf4j
@RestController
@RequestMapping("/settlement")
public class SettlementApiController {

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("{id}")
    public Settlement create(@PathVariable Long id){
        if(settlementRepository.findById(id).isPresent()){
            return update(id);
        }

        Optional<User> optional = userRepository.findById(id);

        BigDecimal totalPrice = optional.map(user -> {
            return user.getOrderGroupList()
                    .stream()
                    .map(orderGroup -> {
//                        System.out.println(orderGroup.getTotalPrice());
                        return orderGroup.getTotalPrice();
                    })
                    .reduce(BigDecimal::add)
                    .get();
        })
                .orElse(null);

        Settlement settlement = Settlement.builder()
                .id(id)
                .price(totalPrice)
                .build();

        return settlementRepository.save(settlement);
    }

    @GetMapping("{id}")
    public Settlement read(@PathVariable Long id){

        Optional<Settlement> optional = settlementRepository.findById(id);

        return optional
                .orElse(null);
    }

    @PutMapping("{id}")
    public Settlement update(@PathVariable Long id){
        Optional<Settlement> optional = settlementRepository.findById(id);
        if(!optional.isPresent()){
            return create(id);
        }

        return optional.map(settlement -> {
            BigDecimal totalPrice = userRepository.findById(id).map(user -> user.getOrderGroupList()
                    .stream()
                    .map(OrderGroup::getTotalPrice)
                    .reduce(BigDecimal::add)
                    .get())
                    .orElse(null);

            settlement.setPrice(totalPrice);

            return settlement;
        })
                .map(settlement -> settlementRepository.save(settlement))
                .orElse(null);
    }
}
