package com.yys.service.impl;

import com.yys.common.GoodStatusCommon;
import com.yys.dao.GoodTextRepository;
import com.yys.enums.GoodStatusEnum;
import com.yys.enums.ResultEnum;
import com.yys.po.GoodText;
import com.yys.po.Login;
import com.yys.service.GoodTextService;
import com.yys.service.ImageService;
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
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by xyr on 2017/10/18.
 */
@Service
public class GoodTextServiceImpl implements GoodTextService {

    @Autowired
    private GoodTextRepository goodTextRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public GoodText findOne(String id) {
        return goodTextRepository.findOne(id);
    }

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
        goodText.setUpdateTime(LocalDateTime.now());

        //保存更新到数据库
        goodText = goodTextRepository.saveAndFlush(goodText);
        return ResultVo.success(goodText);
    }

    @Override
    @Transactional
    public ResultVo updateGoodText(String id, String text, String image,
                                   LocalDate startTime, LocalDate endTime) throws Exception {
        //获取商品
        GoodText goodText = goodTextRepository.getOne(id);
        if (goodText == null)
            return ResultVo.error(ResultEnum.GOODS_NOT_EXIT.getCode(), ResultEnum.GOODS_NOT_EXIT.getMessage());

        //查看image和数据库中的image是否一致，不一致删除之前的图片
        if (image != null && !image.equals(goodText.getImage()))
            imageService.deleteImage(goodText.getImage());

        //更新商品
        goodText.setText(text);
        goodText.setUpdateTime(LocalDateTime.now());
        if (StringUtils.isNotBlank(image))
            goodText.setImage(image);
        if (startTime != null)
            goodText.setStartTime(startTime);
        if (endTime != null)
            goodText.setEndTime(endTime);
        //保存更新到数据库
        goodText = goodTextRepository.saveAndFlush(goodText);
        return ResultVo.success(goodText);
    }

    @Override
    @Transactional
    public ResultVo updateGoodDelete(String id) throws Exception {
        GoodText goodText = goodTextRepository.findOne(id);
        if (goodText == null)
            return ResultVo.error(ResultEnum.UPDATE_FAILURE.getCode(), ResultEnum.UPDATE_FAILURE.getMessage());

        goodText.setHasDelete(true);
        goodText.setUpdateTime(LocalDateTime.now());
        goodTextRepository.saveAndFlush(goodText);
        return ResultVo.success();
    }

    @Override
    public ResultVo deleteGood(String id) throws Exception {
        GoodText goodText = goodTextRepository.findOne(id);
        goodTextRepository.delete(id);
        imageService.deleteImage(goodText.getImage());
        return ResultVo.success();
    }

    @Override
    public ResultVo addGood(String text, String image, Login login) throws Exception {
        GoodText goodText = new GoodText();
        goodText.setId(UUID.randomUUID().toString());
        goodText.setText(text);
        goodText.setImage(image);
        goodText.setUserId(login.getId());
        goodText.setUsername(login.getUsername());
        LocalDateTime localDateTime = LocalDateTime.now();
        goodText.setCreateTime(localDateTime);
        goodText.setUpdateTime(localDateTime);
        goodText = goodTextRepository.saveAndFlush(goodText);
        return ResultVo.success(goodText);
    }
}
