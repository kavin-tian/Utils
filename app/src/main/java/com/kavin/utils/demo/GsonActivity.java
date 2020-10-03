package com.kavin.utils.demo;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.reflect.TypeToken;
import com.kavin.utils.R;
import com.kavin.myutils.tools.GsonUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
    }

    public void toJson(View view) {
        User user = new User(1, "JACK");
        String json = GsonUtils.toJson(user);
        System.out.println(json);
    }

    public void fromJson(View view) {
        //String gson = "{\"name\":\"JACK\",\"id\":1}";
        String gson = "{'name':'JACK','id':1}";
        User user = GsonUtils.fromJson(gson, User.class);
        System.out.println(user);
    }

    public void list2Json(View view) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, "AA"));
        users.add(new User(2, "BB"));
        users.add(new User(3, "CC"));
        users.add(new User(4, "DD"));

        String json = GsonUtils.toJson(users, new TypeToken<List<User>>() {
        }.getType());
        System.out.println(json);
    }

    public void json2List(View view) {
        String json = "[{'name':'AA','id':1},{'name':'BB','id':2},{'name':'CC','id':3},{'name':'DD','id':4}]";
        ArrayList<User> users = GsonUtils.fromJson(json, new TypeToken<List<User>>() {
        }.getType());

        for (User user : users) {
            System.out.println(user);
        }
    }

    public void map2Json(View view) {
        HashMap<Integer, User> userMap = new HashMap<>();
        userMap.put(001, new User(1, "AA"));
        userMap.put(002, new User(2, "BB"));
        userMap.put(003, new User(3, "CC"));
        userMap.put(004, new User(4, "DD"));

        String json = GsonUtils.toJson(userMap, new TypeToken<HashMap<Integer, User>>() {
        }.getType());

        System.out.println(json);
    }

    public void json2Map(View view) {
        String json = "{'4':{'name':'DD','id':4},'1':{'name':'AA','id':1},'2':{'name':'BB','id':2},'3':{'name':'CC','id':3}}";
        HashMap<Integer, User> userMap = GsonUtils.fromJson(json, new TypeToken<HashMap<Integer, User>>() {
        }.getType());

        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            Integer key = entry.getKey();
            User value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }

    private class User {
        public int id;
        public String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
