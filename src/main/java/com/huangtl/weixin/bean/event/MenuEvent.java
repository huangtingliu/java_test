package com.huangtl.weixin.bean.event;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 自定义菜单事件推送
 */
public class MenuEvent extends BaseEvent {


    /**指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了。*/
    @JsonProperty(value = "MenuId")
    private String MenuId;

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
