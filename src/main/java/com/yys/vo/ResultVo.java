package com.yys.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xyr on 2017/10/17.
 */
@Data
public class ResultVo<T> implements Serializable {
    private static final long serialVersionUID = -8957372288032951260L;

    /** 错误码 */
    private Integer code;

    /** 返回的信息 */
    private String msg;

    /** 返回的内容 */
    private T data;

    public static ResultVo success(Object object) {
        ResultVo resultVO = new ResultVo();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVo success() {
        return success(null);
    }

    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVO = new ResultVo();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
