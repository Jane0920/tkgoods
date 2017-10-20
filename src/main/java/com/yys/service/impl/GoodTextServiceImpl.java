package com.yys.service.impl;

import com.yys.common.GoodStatusCommon;
import com.yys.dao.GoodTextRepository;
import com.yys.enums.GoodStatusEnum;
import com.yys.enums.ResultEnum;
import com.yys.po.GoodText;
import com.yys.service.GoodTextService;
import com.yys.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            predicate = cb.and(predicate, cb.equal(root.get("status").as(Integer.class), GoodStatusCommon.CHECKSUCCESS));

            return predicate;
        }, pageable);
    }

    @Override
    public Page<GoodText> getGood(String text, int status, String username, boolean isAdmin, Pageable pageable) {
        return goodTextRepository.findAll((Root<GoodText> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.isTrue(cb.literal(true));

            if (!StringUtils.isBlank(username) && isAdmin)
                predicate = cb.and(predicate, cb.like(root.get("username").as(String.class), "%" + username + "%"));
            else if (!StringUtils.isBlank(username) && !isAdmin)
                predicate = cb.and(predicate, cb.equal(root.get("username").as(String.class), username));
            if (!StringUtils.isBlank(text))
                predicate = cb.and(predicate, cb.like(root.get("text").as(String.class), "%" + text + "%"));
            if (status != -1)
                predicate = cb.and(predicate, cb.equal(root.get("status").as(Integer.class), status));

            if (!isAdmin)
                predicate = cb.and(predicate, cb.equal(root.get("hasDelete").as(Boolean.class), false));

            return predicate;
        }, pageable);
    }

    @Override
    public GoodText goodDetail(String id) {
        return goodTextRepository.findOne(id);
    }

    @Override
    @Transactional
    public ResultVo updateGoodStatus(String id, int status, LocalDate startTime, LocalDate endTime) throws Exception {
        //获取商品
        GoodText goodText = goodTextRepository.findOne(id);
        if (goodText == null) {
            return ResultVo.error(ResultEnum.GOODS_NOT_EXIT.getCode(), ResultEnum.GOODS_NOT_EXIT.getMessage());
        }
        //更新商品
        goodText.setStatus(status);
        if (status == GoodStatusEnum.CHECKSUCCESS.getCode()) {
            goodText.setStartTime(startTime);
            goodText.setEndTime(endTime);
        }

        //保存更新到数据库
        goodText = goodTextRepository.saveAndFlush(goodText);
        return ResultVo.success(goodText);
    }

    @Override
    public void updateGoodText(GoodText goodText) {

    }

    @Override
    @Transactional
    public ResultVo updateGoodDelete(String id) throws Exception {
        GoodText goodText = goodTextRepository.findOne(id);
        if (goodText == null)
            return ResultVo.error(ResultEnum.UPDATE_FAILURE.getCode(), ResultEnum.UPDATE_FAILURE.getMessage());

        goodText.setHasDelete(true);
        goodTextRepository.saveAndFlush(goodText);
        return ResultVo.success();
    }

    @Override
    public ResultVo deleteGood(String id) throws Exception {
        goodTextRepository.delete(id);

        return ResultVo.success();
    }
}
