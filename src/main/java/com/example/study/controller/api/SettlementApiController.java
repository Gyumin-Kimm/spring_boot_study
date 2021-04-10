package com.example.study.controller.api;

import com.example.study.exception.NoContentException;
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

    // 각 ID 별로 POST, PUT이 요청왔을때 DB에서 TotalPrice를 계산하여 SETTLEMENT 테이블에 삽입

    @PostMapping("{id}")
    public Settlement create(@PathVariable Long id){

        // POST, ID존재시 update 함수로
        if(settlementRepository.findById(id).isPresent()){
            return update(id);
        }

        // SELECT
        Optional<User> optional = userRepository.findById(id);

        // TotalPrcie 계산
        BigDecimal totalPrice = optional.map(user -> {
            return user.getOrderGroupList()
                    .stream()
                    .map(OrderGroup::getTotalPrice)
                    .reduce(BigDecimal::add)
                    .get();
        })
                .orElse(null);

        // Settlement 객체 생성
        Settlement settlement = Settlement.builder()
                .id(id)
                .price(totalPrice)
                .build();

        // CREATE
        return settlementRepository.save(settlement);
    }

    @GetMapping("{id}")
    public Settlement read(@PathVariable Long id){

        Optional<Settlement> optional = settlementRepository.findById(id);

        return optional
                .orElseGet(()->{
                    throw new NoContentException("데이터가 없습니다.");
                });
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
