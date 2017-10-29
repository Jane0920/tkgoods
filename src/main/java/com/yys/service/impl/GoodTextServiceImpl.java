package com.yys.service.impl;

import com.yys.common.GoodStatusCommon;
import com.yys.dao.GoodTextRepository;
import com.yys.dao.ImageRepository;
import com.yys.enums.GoodStatusEnum;
import com.yys.enums.ResultEnum;
import com.yys.po.GoodText;
import com.yys.po.Image;
import com.yys.po.Login;
import com.yys.service.GoodTextService;
import com.yys.service.ImageService;
import com.yys.util.RichTextImgUtil;
import com.yys.vo.ResultVo;
import me.jiangcai.lib.resource.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ResourceService resourceService;

    @Override
    public GoodText findOne(String id) {
        return goodTextRepository.findOne(id);
    }

    @Override
    public Page<GoodText> getGood(String searchContent, Pageable pageable) {
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort() == null ?
                new Sort(Sort.Direction.DESC, "updateTime"): pageable.getSort());

        return goodTextRepository.findAll((Root<GoodText> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.isTrue(cb.literal(true));

            if (!StringUtils.isBlank(searchContent))
                predicate = cb.and(predicate, cb.like(root.get("richText").as(String.class), "%" + searchContent + "%"));

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
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort() == null ?
                new Sort(Sort.Direction.DESC, "updateTime"): pageable.getSort());

        return goodTextRepository.findAll((Root<GoodText> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.isTrue(cb.literal(true));

            if (!StringUtils.isBlank(username) && isAdmin)
                predicate = cb.and(predicate, cb.like(root.get("username").as(String.class), "%" + username + "%"));
            else if (!StringUtils.isBlank(username) && !isAdmin)
                predicate = cb.and(predicate, cb.equal(root.get("username").as(String.class), username));
            if (!StringUtils.isBlank(text))
                predicate = cb.and(predicate, cb.like(root.get("richText").as(String.class), "%" + text + "%"));
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
    public ResultVo updateGoodStatus(String id, int status, LocalDate startTime, LocalDate endTime, String reason) throws Exception {
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
            goodText.setReason(null);
        } else {
            goodText.setReason(reason);
        }
        goodText.setUpdateTime(LocalDateTime.now());

        //保存更新到数据库
        goodText = goodTextRepository.saveAndFlush(goodText);
        return ResultVo.success(goodText);
    }

    @Override
    @Transactional
    public ResultVo updateGoodText(String id, String richText,
                                   LocalDate startTime, LocalDate endTime) throws Exception {
        //获取商品
        GoodText goodText = goodTextRepository.getOne(id);
        if (goodText == null)
            return ResultVo.error(ResultEnum.GOODS_NOT_EXIT.getCode(), ResultEnum.GOODS_NOT_EXIT.getMessage());

        //获取图片路径
        String newPath = RichTextImgUtil.getImgPath(richText);
        //获取数据库中该商品对应的图片路径
        Image image = imageService.findImageByGoodId(goodText.getId());
        //查看当前richText中的image和数据库中的image是否一致，不一致删除之前的图片
        if (image != null && !(image.getPath()).equals(newPath)) {
            try {
                resourceService.deleteResource(image.getPath());
                imageRepository.delete(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //更新当前商品的id至新的image的goodId中
        Image newImage = imageService.findImageByPath(newPath);
        newImage.setGoodId(goodText.getId());
        imageService.saveImage(newImage);

        //更新商品
        goodText.setRichText(richText);
        goodText.setUpdateTime(LocalDateTime.now());
        /*if (StringUtils.isNotBlank(image))
            goodText.setImage(image);*/
        goodText.setStatus(GoodStatusEnum.UNCHECK.getCode());
        if (startTime != null && endTime != null) {
            goodText.setStartTime(startTime);
            goodText.setEndTime(endTime);
            goodText.setStatus(GoodStatusEnum.CHECKSUCCESS.getCode());
        }

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
        imageService.deleteImage(id);
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

    @Override
    public ResultVo addGood(String richText, Login login) throws Exception {
        GoodText goodText = new GoodText();
        goodText.setId(UUID.randomUUID().toString());
        goodText.setRichText(richText);
        goodText.setUserId(login.getId());
        goodText.setUsername(login.getUsername());
        LocalDateTime localDateTime = LocalDateTime.now();
        goodText.setCreateTime(localDateTime);
        goodText.setUpdateTime(localDateTime);
        goodText = goodTextRepository.saveAndFlush(goodText);

        if (goodText != null) { //保存图片相对路径 与图片相关联
            String imgPath = RichTextImgUtil.getImgPath(richText);
            Image image = imageService.findImageByPath(imgPath);
            image.setGoodId(goodText.getId());
            imageService.saveImage(image);
        }
        return ResultVo.success(goodText);
    }
}
