package com.huitai.core.message.service.impl;

import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.message.dao.HtMessageTemplateDao;
import com.huitai.core.message.entity.HtMessageTemplate;
import com.huitai.core.message.service.HtMessageTemplateService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 消息模板 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-30
 */
@Service
public class HtMessageTemplateServiceImpl extends BaseServiceImpl<HtMessageTemplateDao, HtMessageTemplate> implements HtMessageTemplateService {

    /**
     * description: 保存或修改模板 <br>
     * version: 1.0 <br>
     * date: 2020/5/6 9:33 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateTemplate(HtMessageTemplate htMessageTemplate){
        // 验证编码是否已存在

        super.checkExistsField(htMessageTemplate, "tpl_code", htMessageTemplate.getTplCode(), messageSource.getMessage("message.error.existsTemplateCode", null, LocaleContextHolder.getLocale()));
        if(StringUtil.isBlank(htMessageTemplate.getId())){
            super.save(htMessageTemplate);
        }else{
            super.updateById(htMessageTemplate);
        }
    }
}
