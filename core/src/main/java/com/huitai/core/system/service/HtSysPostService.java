package com.huitai.core.system.service;

import com.huitai.core.base.IBaseTreeService;
import com.huitai.core.system.entity.HtSysPost;

import java.util.List;

/**
 * <p>
 * 岗位表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-16
 */
public interface HtSysPostService extends IBaseTreeService<HtSysPost> {
    /**
     * description: 保存列表排序 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 16:45 <br>
     * author: XJM <br>
     */
    void saveTreeSortData(List<HtSysPost> list);

    /**
     * description: 通过id获取岗位对象,包括父岗位信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 9:30 <br>
     * author: XJM <br>
     */
    HtSysPost getPostById(String id);

    /**
     * description: 修改岗位数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 9:35 <br>
     * author: XJM <br>
     */
    void saveOrUpdateHtSysPost(HtSysPost htSysPost);

    /**
     * description: 删除岗位 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 17:22 <br>
     * author: XJM <br>
     */
    void deletePost(String[] ids);
}
