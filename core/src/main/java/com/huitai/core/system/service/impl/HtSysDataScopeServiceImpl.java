package com.huitai.core.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huitai.core.system.dao.HtSysDataScopeDao;
import com.huitai.core.system.entity.HtSysDataScope;
import com.huitai.core.system.service.HtSysDataScopeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色数据权限关联表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
@Service
public class HtSysDataScopeServiceImpl extends ServiceImpl<HtSysDataScopeDao, HtSysDataScope> implements HtSysDataScopeService {

    /**
     * description: 分配数据权限 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 15:40 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void assignDataScope(List<HtSysDataScope> htSysDataScope, String roleId){
        QueryWrapper<HtSysDataScope> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        super.remove(queryWrapper);
        if(htSysDataScope != null && htSysDataScope.size() > 0) {
            if(htSysDataScope != null && htSysDataScope.size() > 0){
                for (int i = 0; i < htSysDataScope.size(); i++) {
                    super.save(htSysDataScope.get(i));
                }
            }
        }
    }

    /**
     * description: 根据角色id获取所有关联的数据权限id <br>
     * version: 1.0 <br>
     * date: 2020/4/21 16:04 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public List<String> findDataScopesOfRole(String id){
        return baseMapper.findDataScopesOfRole(id);
    }
}
