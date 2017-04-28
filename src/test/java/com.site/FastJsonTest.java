package com.site;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

/**
 * Created by 王萍 on 2017/4/27 0027.
 */

@RunWith(MockitoJUnitRunner.class)
public class FastJsonTest {


    @Test
    public void testfastjson() {
        Data data = new Data("wanwanpp", "980325");
        String jsonString = JSON.toJSONString(data);
        System.out.println(jsonString);

        Data dataObject = JSON.parseObject(jsonString, Data.class);
        System.out.println(dataObject.getName() + "   and    " + dataObject.getPassword());
    }

    @Test
    public void testList() {
        List<Data> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Data data = new Data("wanwanpp", "980325");
            list.add(data);
        }

        String string = JSON.toJSONString(list);
        System.out.println(string);

//        一：
//        List<Data> datas = JSON.parseArray(string, Data.class);
//        for (Data data : datas) {
//            System.out.println(data.getName() + "   and   " + data.getPassword());
//        }

        //二：
        ArrayList<Data> datas = (ArrayList<Data>) JSON.parseArray(string, Data.class);
        for (Data data : datas) {
            System.out.println(data.getName() + "   and   " + data.getPassword());
        }
    }

    @Test
    public void testTag() {
        Data data = new Data("wanwanpp", "980325");
        String jsonString = JSON.toJSONString(data);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        System.out.println(jsonObject.get("name"));
    }

    @Test
    //通过json字符串将一个对象装入到另一个对象的属性中。
    public void testJSON() {
        Data data = new Data("123", "SpikePepelu");
        String dString = JSON.toJSONString(data);
        JD jd = new JD();
        jd.code = "12";
        jd.message = dString;
        // jString:{"code":"12","message":"{\"name\":\"SpikePepelu\",\"password\":\"123\"}"}
        String jString = JSON.toJSONString(jd);
        JSONObject jsonObject = JSON.parseObject(jString);
        // messageString:{"name":"SpikePepelu","password":"123"}
        String messageString = (String) jsonObject.get("message");
        Data data2 = JSON.parseObject(messageString, Data.class);
        System.out.println(data2.getName());
    }

    @Test
    public void testListMap() {
        Map<String, Object> map = new HashMap<>();
//        map.put("name", "wanwanpp");
        Data data = new Data("980325", "wanwanpp");
        map.put("data", data);
        map.put("nick", "wangping");

        List<Map<String, Object>> mapList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            mapList.add(map);
        }

        String jsonString = JSON.toJSONString(mapList);

        List<HashMap> hashMaps = JSON.parseArray(jsonString, HashMap.class);
        for (HashMap hashMap : hashMaps) {

            Data data1 = JSON.parseObject(hashMap.get("data").toString(), Data.class);
            System.out.println(data1.getPassword() + "  and  " + data1.getName() + "  and   " + hashMap.get("nick"));
//            System.out.println(hashMap.get("data")+"  and   "+hashMap.get("nick"));
        }
    }

}

/**
 * JD.class
 */
class JD {
    public JD() {
    }

    public String code, message;
}

class Data {

    private String password, name;

    public Data() {
    }

    public Data(String password, String name) {
        super();
        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
