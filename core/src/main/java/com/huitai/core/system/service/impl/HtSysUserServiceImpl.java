package com.huitai.core.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huitai.common.utils.Md5Util;
import com.huitai.common.utils.Page;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.system.dao.HtSysUserDao;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.entity.HtSysUserPost;
import com.huitai.core.system.service.HtSysConfigService;
import com.huitai.core.system.service.HtSysUserPostService;
import com.huitai.core.system.service.HtSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-08
 */
@Service
public class HtSysUserServiceImpl extends BaseServiceImpl<HtSysUserDao, HtSysUser> implements HtSysUserService {

    @Autowired
    private HtSysUserPostService htSysUserPostService;

    @Autowired
    private HtSysConfigService htSysConfigService;

    /**
     * description: 根据id获取实体对象 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:49 <br>
     * author: XJM <br>
     */
    @Transactional(rollbackFor = {Exception.class}, readOnly = true)
    public HtSysUser getUserById(String id) {
        return baseMapper.getUserById(id);
    }

    /**
     * description: 批量删除用户 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 10:39 <br>
     * author: XJM <br>
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteBatch(String[] ids) {
        baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * description: 修改用户状态 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 10:53 <br>
     * author: XJM <br>
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateStatus(String id, String status) {
        HtSysUser htSysUser = this.getById(id);
        htSysUser.setStatus(status);
        baseMapper.updateById(htSysUser);
    }

    /**
     * description: 修改密码 newPassWord是前端加密过的密码， md5加密 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:32 <br>
     * author: XJM <br>
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updatePassword(String id, String newPassWord) {
        HtSysUser htSysUser = this.getById(id);
        htSysUser.setPassword(newPassWord);
        baseMapper.updateById(htSysUser);
    }

    /**
     * description: 重置密码 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:46 <br>
     * author: XJM <br>
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void resetPassword(String id) {
        HtSysUser htSysUser = this.getById(id);
        htSysUser.setPassword(Md5Util.stringToMD5(htSysConfigService.getValueByKey(HtSysUserService.SYS_USER_INIT_PASSWORD)));
        baseMapper.updateById(htSysUser);
    }

    /**
     * description: 根据登录账号获取用户信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 13:37 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Override
    public HtSysUser getByLoginCode(String loginCode) {
        HtSysUser htSysUser = new HtSysUser();
        htSysUser.setLoginCode(loginCode);
        Wrapper<HtSysUser> queryWrapper = new QueryWrapper<>(htSysUser);
        htSysUser = baseMapper.selectOne(queryWrapper);
        return htSysUser;
    }

    /**
     * description: 获取用户列表数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 11:22 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    @DS("slave")
    @Override
    public Page<HtSysUser> findHtSysUserList(Page<HtSysUser> page) {
        if (page.getParams() == null) {
            page.setParams(new HtSysUser());
        }
        if (page.getExtraParams() == null) {
            page.setExtraParams(new HashMap<>());
        }

        return baseMapper.findHtSysUserList(page);
    }

    /**
     * description: 保存或修改用户信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 17:44 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateUser(HtSysUser htSysUser) {
        // 验证编码是否存在
        checkExistsField(htSysUser, "login_code", htSysUser.getLoginCode(), messageSource.getMessage("system.error.existsLoginCode", null, LocaleContextHolder.getLocale()));
        if (StringUtil.isEmpty(htSysUser.getId())) {
            htSysUser.setPassword(Md5Util.stringToMD5(htSysConfigService.getValueByKey(HtSysUserService.SYS_USER_INIT_PASSWORD)));
            super.save(htSysUser);
        } else {
            super.updateById(htSysUser);
        }
    }

    /**
     * description: 根据ids删除用户 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 10:54 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteUser(String[] ids) {
        QueryWrapper<HtSysUserPost> queryWrapper = null;
        int count = 0;
        for (int i = 0; i < ids.length; i++) {
            if (!StringUtil.isEmpty(ids[i])) {
                baseMapper.deleteById(ids[i]);

                // 删除用户岗位关联表数据
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("user_id", ids[i]);
                htSysUserPostService.remove(queryWrapper);
            }
        }
    }

    /**
     * description: 根据查询参数获取用户数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 9:37 <br>
     * author: TYJ <br>
     */
    @Override
    public List<HtSysUser> listExcel(Map<String, Object> params) {
        return baseMapper.selectListByParams(params);
    }

    @Override
    public IPage<HtSysUser> pageByPosts(IPage<?> page, String posts, HtSysUser htSysUser) {
        IPage<HtSysUser> htSysUserIPage = baseMapper.pageByPosts(page, posts.split(","), htSysUser);
        return htSysUserIPage;
    }
}
