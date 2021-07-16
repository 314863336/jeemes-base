package com.huitai.core.message.service;

import com.huitai.core.message.entity.HtMessageTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 消息模板 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-30
 */
public interface HtMessageTemplateService extends IService<HtMessageTemplate> {

    /**
     * description: 保存或修改模板 <br>
     * version: 1.0 <br>
     * date: 2020/5/6 9:33 <br>
     * author: XJM <br>
     */
    void saveOrUpdateTemplate(HtMessageTemplate htMessageTemplate);
}
