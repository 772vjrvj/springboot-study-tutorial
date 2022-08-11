package com.example.springbootstudybasictest.test;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class jsonOrgTest {
    public static void main(String[] args) {

        //org.json 편

        //json 객체 만들기 보통 DB에서 json String을 가져온다.
        JSONObject jo1 = new JSONObject("{\"city\":\"Seoul\",\"name\":\"Jone\"}");
        Iterator it = jo1.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            System.out.println("key: " + key + ", value: " + jo1.getString(key));
        }
        System.out.println(jo1.toString());
        //jsonobject 값 추가
        jo1.put("age", "30");
        System.out.println(jo1.toString());




        //json array 만들기
        String JsonArry1 = "[{\"Product\" : \"Mouse\", \"Maker\":\"Samsung\", \"Price\":23000},"
                + "{\"Product\":\"HDD\", \"Maker\":\"Western Digital\", \"Price\":156000}]";
        String result = "";
        JSONArray ja1 = new JSONArray(JsonArry1);
        for (int i = 0; i < ja1.length(); i++) {
            JSONObject order = ja1.getJSONObject(i);
            result += "product: "
                    + order.getString("Product")
                    + ", maker: " + order.getString("Maker")
                    + ", price: " + order.getInt("Price") + "\n";

        }
        System.out.println("result : " + result);




        //json to map
        String jsonObjectData = "{\"lang\":\"java\",\"name\":\"Jone\"}";
        JSONObject jsonObject = new JSONObject(jsonObjectData);
        Map<String, Object> map = jsonObject.toMap();
        System.out.println("map :" + map);
        System.out.println("lang :" + map.get("lang"));




        //jsonArray to list
        String jsonArray2 = "[{\"Product\" : \"Mouse\", \"Maker\":\"Samsung\", \"Price\":23000},"
                + "{\"Product\":\"HDD\", \"Maker\":\"Western Digital\", \"Price\":156000}]";
        JSONArray ja2 = new JSONArray(jsonArray2);
        List<Object> objList1 = ja2.toList();
        System.out.println("objList1 :" + objList1);
        for (int i = 0; i < objList1.size(); i++) {
            System.out.println("jo2 : " + objList1.get(i).getClass()); //class java.util.HashMap
            Map<String,String> map2 = (Map<String, String>) objList1.get(i);
            System.out.println("Product : " + map2.get("Product"));
        }

        List<String> list = new ArrayList<>();
        list.add("Harry");
        list.add("Sam");

        JSONArray ja = new JSONArray(list);

        JSONObject jo = new JSONObject();
        jo.put("name", "Jone");
        jo.put("city", "Seoul");
        jo.put("friends", ja);

        System.out.println(jo.toString());

    }
}
