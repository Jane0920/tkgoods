package com.yys.service.impl;

import com.yys.dao.GoodTextRepository;
import com.yys.po.GoodText;
import com.yys.service.GoodTextService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

/**
 * Created by xyr on 2017/10/18.
 */
@Service
public class GoodTextServiceImpl implements GoodTextService {

    @Autowired
    private GoodTextRepository goodTextRepository;

    @Override
    public Page<GoodText> getGood(String searchContent, Pageable pageable) {

        return goodTextRepository.findAll((Root<GoodText> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.isTrue(cb.literal(true));

            if (!StringUtils.isBlank(searchContent))
                predicate = cb.and(predicate, cb.like(root.get("text").as(String.class), "%" + searchContent + "%"));

            //在有效期内
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("startTime").as(LocalDate.class), LocalDate.now()));
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("endTime").as(LocalDate.class), LocalDate.now()));
            //审核状态为审核通过
            predicate = cb.and(predicate, cb.equal(root.get("status").as(Integer.class), 1));

            return predicate;
        }, pageable);
    }
}
