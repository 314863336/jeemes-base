package com.huitai.core.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.huitai.core.global.SystemConstant;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description: BaseTreeEntity <br>
 * date: 2020/4/10 14:39 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class BaseTreeEntity extends BaseEntity {

    public static final String TREE_FIELD_DISABLED = "disabled";
    public static final String TREE_FIELD_HAVECHILDREN = "hasChildren";
    public static final String TREE_FIELD_CHILDREN = "children";

    @ApiModelProperty(value = "父主键")
    private String parentId;

    @ApiModelProperty(value = "所有父级机构，逗号隔开")
    private String parentIds;

    @ApiModelProperty(value = "所在树级别")
    private Integer treeLevel;

    @ApiModelProperty(value = "本级排序号（升序）")
    private Integer treeSort;

    @ApiModelProperty(value = "是否最末级(0是，1否)")
    private String treeLeaf;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getTreeSort() {
        return treeSort;
    }

    public void setTreeSort(Integer treeSort) {
        this.treeSort = treeSort;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getTreeLeaf() {
        return treeLeaf;
    }

    public void setTreeLeaf(String treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    /**
     * description: 设置树属性 <br>
     * version: 1.0 <br>
     * date: 2020/4/13 14:27 <br>
     * author: XJM <br>
     */
    public void setTreeFields(JSONObject jsonObject){
        // 可以被子类重写,设置title属性
        jsonObject.put(TREE_FIELD_DISABLED, false);
    }

    /**
     * description: 懒加载组装树状数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/13 8:34 <br>
     * author: XJM <br>
     */
    public List<JSONObject> buildByRecursiveLazy(List<? extends BaseTreeEntity> treeEntitys, String... excludeIds) {
        List<JSONObject> trees = new ArrayList<>();
        if(treeEntitys == null || treeEntitys.size() == 0){
            return trees;
        }
        List<String> excludeIdList = Arrays.asList(excludeIds);
        List<String> ids = new ArrayList<>();

        List<JSONObject> treeNodes = new ArrayList<>();
        JSONObject jsonObject = null;
        for (BaseTreeEntity treeNode : treeEntitys) {
            if(!excludeIdList.contains(treeNode.getId())){
                ids.add(treeNode.getId());
                jsonObject = JSON.parseObject(JSONObject.toJSONString(treeNode, SerializerFeature.WriteNullStringAsEmpty));
                treeNode.setTreeFields(jsonObject);
                jsonObject.put(TREE_FIELD_HAVECHILDREN, SystemConstant.NO.equals(treeNode.getTreeLeaf()) ? true : false);
                jsonObject.put(TREE_FIELD_CHILDREN, new ArrayList<JSONObject>());
                treeNodes.add(jsonObject);
            }
        }

        for (JSONObject treeNode : treeNodes) {
            // 先查出根节点再获取根节点下的子节点
            // 不存在父节点，则该节点为根节点
            if (!ids.contains(treeNode.get("parentId").toString())) {
                trees.add(findChildrenLazy(treeNode,treeNodes));
            }
        }
        return trees;
    }

    public JSONObject findChildrenLazy(JSONObject jsonObject, List<JSONObject> treeNodes) {
        for (JSONObject it : treeNodes) {
            if(jsonObject.get("id").toString().equals(it.get("parentId"))) {
                ((ArrayList<JSONObject>)jsonObject.get("children")).add(findChildrenLazy(it,treeNodes));
            }
        }
        if(SystemConstant.YES.equals(jsonObject.get("treeLeaf").toString())){
            jsonObject.remove(TREE_FIELD_CHILDREN);
        }else if(((ArrayList<JSONObject>)jsonObject.get(TREE_FIELD_CHILDREN)).size() > 0){
            jsonObject.remove(TREE_FIELD_HAVECHILDREN);
        }else{
            jsonObject.remove(TREE_FIELD_CHILDREN);
        }
        return jsonObject;
    }

    /**
     * description: 一次性加载组装树状数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/13 8:34 <br>
     * author: XJM <br>
     */
    public List<JSONObject> buildByRecursive(List<? extends BaseTreeEntity> treeEntitys, String... excludeIds) {
        List<JSONObject> trees = new ArrayList<>();
        if(treeEntitys == null || treeEntitys.size() == 0){
            return trees;
        }
        List<String> excludeIdList = Arrays.asList(excludeIds);
        List<String> ids = new ArrayList<>();

        List<JSONObject> treeNodes = new ArrayList<>();
        JSONObject jsonObject = null;
        for (BaseTreeEntity treeNode : treeEntitys) {
            if(!excludeIdList.contains(treeNode.getId())){
                ids.add(treeNode.getId());

                jsonObject = JSON.parseObject(JSONObject.toJSONString(treeNode, SerializerFeature.WriteNullStringAsEmpty));
                treeNode.setTreeFields(jsonObject);
                jsonObject.put(TREE_FIELD_CHILDREN, new ArrayList<JSONObject>());
                treeNodes.add(jsonObject);
            }
        }

        for (JSONObject treeNode : treeNodes) {
            // 先查出根节点再获取根节点下的子节点
            // 不存在父节点，则该节点为根节点
            if (!ids.contains(treeNode.get("parentId").toString())) {
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    public JSONObject findChildren(JSONObject jsonObject, List<JSONObject> treeNodes) {
        for (JSONObject it : treeNodes) {
            if(jsonObject.get("id").toString().equals(it.get("parentId"))) {
                ((ArrayList<JSONObject>)jsonObject.get("children")).add(findChildren(it,treeNodes));
            }
        }
        return jsonObject;
    }
}
