package com.example.googleplay.bean;

import java.util.ArrayList;

public class Pictures {

    public String className;
    public ServerData serverData;



    public class ServerData {
        public ArrayList<String> picture_list;
        public String createdAt;
        public String objectId;
        public String updatedAt;
    }


}
