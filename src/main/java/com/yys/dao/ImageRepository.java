package com.yys.dao;

import com.yys.po.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2017/10/27.
 */
public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    Image findByPath(String path);

    Image findByGoodId(String goodId);
}
