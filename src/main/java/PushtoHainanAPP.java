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

public class PushtoHainanAPP {

    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = "XXWnlGGaHg6RjsNz9ClXg";
    private static String appKey = "jQw2utiZhT6F0JMrOpoJE8";
    private static String masterSecret = "PnTSTB47yH6RSaTUUKuHlA";
    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) {

        IGtPush push = new IGtPush(host, appKey, masterSecret);

        final AppMessage message = new AppMessage();
        message.setData(transmissionTemplate(1));
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
        tagList.add("weatherCity101010200");

        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        cdt.addCondition(AppConditions.REGION, provinceList);
        cdt.addCondition(AppConditions.TAG,tagList);
        message.setConditions(cdt);

//        Target target = new Target();
//        target.setAppId(appId);
//        target.setClientId("087adce87c13875b1200b2ae14ac72c3");//华为
//        target.setClientId("ff710264197882788aef2ec4f1a8c967");//魅族
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
        String value1 = "",value2 = "";
        switch (type) {
            case 1:
                notify.setTitle("有跳转的pdf文档");
                notify.setContent("有跳转的pdf文档 ");
                value1 = content(type);
                value2 = "";
                notify.setIntent("intent:#Intent;launchFlags=0x10000000;package=com.pmsc.weather4decision.phone.hainan;component=com.pmsc.weather4decision.phone.hainan/.act.PdfActivity;S.parm1="+value1+";S.parm2="+value2+";end");
                notify.setType(GtReq.NotifyInfo.Type._intent);
                break;
            case 2:
                notify.setTitle("有跳转的预警");
                notify.setContent("有跳转的预警 ");
                value1 = content(type);
                value2 = "";
                notify.setIntent("intent:#Intent;launchFlags=0x10000000;package=com.pmsc.weather4decision.phone.hainan;component=com.pmsc.weather4decision.phone.hainan/.act.WarningDetailsActivity;S.parm1="+value1+";S.parm2="+value2+";end");
                notify.setType(GtReq.NotifyInfo.Type._intent);
                break;
            case 3:
                notify.setTitle("没有跳转的透传");
                notify.setContent("没有跳转的透传 ");
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
                object.put("msgType","document");
                object.put("pdfUrl","http://59.50.130.88:8888/decision-admin/upload/doc/a615a184f8602733ed5d4cc23ee9f691.pdf");
                object.put("title","有跳转的pdf文档");
                object.put("content","有跳转的pdf文档");
                break;
            case 2:
                object.put("msgType", "warning");
                object.put("w1", "陵水县");
                object.put("w2","");
                object.put("w3", "");
                object.put("w4", "09");
                object.put("w5", "雷电");
                object.put("w6", "03");
                object.put("w7", "橙色");
                object.put("w8", "2017-10-11 05:40");
                object.put("w9", "陵水县气象台2017年10月11日05时40分发布雷电橙色预警信号：我县地区已经受雷电活动影响，且可能持续，出现雷电灾害事故的可能性比较大，请有关单位和人员做好防范工作。");
                object.put("w10", "201710110540599540雷电橙色");
                object.put("w11", "");
                object.put("title","有跳转的预警");
                object.put("content","有跳转的预警");
                break;
            case 3:
                object.put("msgType", "custom");
                object.put("title","没有跳转的透传");
                object.put("content","没有跳转的透传");
                break;
        }
        return object.toString();
    }

}