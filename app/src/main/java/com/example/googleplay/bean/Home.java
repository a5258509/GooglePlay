package com.example.googleplay.bean;

import java.util.ArrayList;
import java.util.Date;

public class Home {

    public String className;
    public ServerData serverData;


    public class ServerData {
        public boolean needVip;
        public long index;
        public String title;
        public String imgUrl;
        public String createdAt;
        public String downUrl;
        public String size;
        public String objectId;
        public String updatedAt;
        public String desc;
        public ArrayList<String> descImg;
    }


}
