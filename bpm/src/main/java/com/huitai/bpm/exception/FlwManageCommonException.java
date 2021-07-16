package com.huitai.bpm.exception;

import com.huitai.core.base.BaseException;

/**
 * @description: 工作流管理模块下的公共模块异常类型 <br>
 * @author PLF <br>
 * @date 2020-12-03 19:08 <br>
 * @version 1.0 <br>
 */
public class FlwManageCommonException extends BaseException {
    private static final long serialVersionUID = 1191140906554698916L;
    public FlwManageCommonException() {
        super();
    }
    public FlwManageCommonException(String message) {
        super(message);
    }
}
