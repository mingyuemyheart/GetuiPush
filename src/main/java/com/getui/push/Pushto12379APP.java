package com.getui.push;

import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.dto.GtReq;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.util.ArrayList;
import java.util.List;

public class Pushto12379APP {

    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = "UgnrpGkXsi9tsfqrdFfL7";
    private static String appKey = "ku9FtFhusL9ZIcl9X2PYP3";
    private static String masterSecret = "pg0TbJUkik51srnz39iEr9";
    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) {
        push();
    }

    /**
     * 推送消息
     */
    public static void push() {
        IGtPush push = new IGtPush(host, appKey, masterSecret);

        final AppMessage message = new AppMessage();
        message.setData(transmissionTemplate(2));
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions();
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);
        //手机类型
        List<String> phoneTypeList = new ArrayList<String>();
        //省份
        List<String> provinceList = new ArrayList<String>();
        //自定义tag
        List<String> tagList = new ArrayList<String>();
        tagList.add("110000");

        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        cdt.addCondition(AppConditions.REGION, provinceList);
        cdt.addCondition(AppConditions.TAG,tagList);
        message.setConditions(cdt);

//        Target target = new Target();
//        target.setAppId(appId);
////        target.setClientId("c10268d6f2308e61d8c7774080a69542");//小米
//        target.setClientId("2204f2b4f08454670be6b41faae3e4a9");//华为
////        target.setClientId("93f99ba87ffabd01be8de838616aba2b");//魅族
////        target.setClientId("7e258d140152c0a2cbb1c2c568a204aa");
//        //target.setAlias(Alias);
//        IPushResult ret = null;
//        try {
//            ret = push.pushMessageToSingle(message, target);
//        } catch (RequestException e) {
//            e.printStackTrace();
//            ret = push.pushMessageToSingle(message, target, e.getRequestId());
//        }

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }

    /**
     * 推送透传
     * @ type 推送的消息类型
     * @return
     */
    private static TransmissionTemplate transmissionTemplate(int type) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionType(2);
        template.setTransmissionContent(content(type));
        template.set3rdNotifyInfo(notify(type));
//        APNPayload payload = new APNPayload();
////        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
//        payload.setAutoBadge("1");
//        payload.setContentAvailable(1);
//        payload.setSound("default");
//        payload.setCategory("$由客户端定义");
//        payload.addCustomMsg("payload",getobj("3"));
////        //简单模式APNPayload.SimpleMsg
//////        payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
////
////        //字典模式使用APNPayload.DictionaryAlertMsg
//        payload.setAlertMsg(getDictionaryAlertMsg());
////        // 添加多媒体资源
////        payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video)
////                .setResUrl("http://ol5mrj259.bkt.clouddn.com/test2.mp4")
////                .setOnlyWifi(true));
//
//        template.setAPNInfo(payload);
        return template;
    }

    //IOS推送内容
    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody("weatherCity10101");
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey("LocKey");
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle("weatherCity10101");
        alertMsg.setTitleLocKey("TitleLocKey");
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }

    /**
     * 设置第三方通知
     * @param type
     * @return
     */
    private static Notify notify(int type){
        Notify notify = new Notify();
        switch (type) {
            case 1:
                notify.setTitle("12379");
                notify.setContent("你的作品审核通过 ");
                notify.setIntent("intent:#Intent;launchFlags=0x10000000;package=com.warning;component=com.warning/.activity.CheckWorksActivity;end");
                notify.setType(GtReq.NotifyInfo.Type._intent);
                break;
            case 2:
                notify.setTitle("12379");
                notify.setContent("广东省罗定市气象局发布雷雨大风蓝色预警[IV级/一般]");
                String url = "000000-20180321211500-14G0705.html";
                notify.setIntent("intent:#Intent;launchFlags=0x10000000;package=com.warning;component=com.warning/.activity.WarningDetailActivity;S.url="+url+";end");
                notify.setType(GtReq.NotifyInfo.Type._intent);
                break;
        }
        return notify;
    }

    /**
     * 设置透传内容
     */
    private static String content(int type){
        JSONObject object = new JSONObject();
        switch (type) {
            case 1:
                object.put("title","12379");
                object.put("otherInfo","你的作品审核通过");
                break;
            case 2:
                object.put("title","12379");
                object.put("content","广东省罗定市气象局发布雷雨大风蓝色预警[IV级/一般]");
                object.put("url", "000000-20180321211500-14G0705.html");
                break;
        }
        return object.toString();
    }

}