package com.asiainfo.oss.monitor.uitl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asiainfo.oss.monitor.entity.user.SysPermission;
import java.util.List;

public class TreeUtils {

    /**
     * 菜单树
     *
     * @param parentId
     * @param permissionsAll
     * @param array
     */
    public static void setPermissionsTree(Integer parentId, List<SysPermission> permissionsAll, JSONArray array) {
        for (SysPermission per : permissionsAll) {
            if (per.getParentid().equals(parentId.longValue())) {
                String string = JSONObject.toJSONString(per);
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                array.add(parent);
                if (permissionsAll.stream().filter(p -> p.getParentid().equals(per.getId())).findAny() != null) {
                    JSONArray child = new JSONArray();
                    parent.put("child", child);
                    setPermissionsTree(per.getId().intValue(), permissionsAll, child);
                }
            }
        }
    }
}
