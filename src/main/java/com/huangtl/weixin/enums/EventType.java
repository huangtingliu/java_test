package com.huangtl.weixin.enums;

/**
 * 事件类型
 */
public enum EventType {
    /**关注，包括（直接关注和用户未关注状态扫描带参数二维码关注）*/
    SUBSCRIBE,

    /**取消关注事件*/
    UNSUBSCRIBE,

    /**用户已关注状态扫描带参数二维码*/
    SCAN,

    /**上报地理位置事件*/
    LOCATION,

    /**点击菜单拉取消息时的事件推送*/
    CLICK,

    /**点击菜单跳转链接时的事件推送*/
    VIEW,

    /**小程序跳转*/
    VIEW_MINIPROGRAM,

    /**模版消息发送任务完成*/
    TEMPLATESENDJOBFINISH;
}
