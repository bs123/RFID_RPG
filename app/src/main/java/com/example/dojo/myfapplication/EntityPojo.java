package com.example.dojo.myfapplication;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sai on 29-Nov-15.
 */
public class EntityPojo {

    /*
        example:
            [ "kleiner", [ "3000E200206481180120195049D3", "3000E2002064811801200620D45E" ] ]
             [ "kleiner", [ "3000E200206481180120195049D3", "3000E2002064811801200620D45E" ] ]  [ "kleiner", [ "3000E20020648118011816206C22", "3000E200206481180120196049D4" ] ]    [ "kleiner", [ "3000E200206481180120195049D3", "3000E2002064811801200810C10D", "3000E200206481180120196049D4" ] ]  [ "kleiner", [ "3000E20020648118011816206C22", "3000E20020648118012
     */
    private String recieved_msg;
    private String location;
    private List<String> monsters;

    public static String userId = "";

    public static enum artifacts {
        a,b,c;
        public static boolean contains(String s)
        {
            for(artifacts artifact:values())
                if (artifact.name().equals(s))
                    return true;
            return false;
        }
    }


    public  EntityPojo(Byte[] bytes) {
        this.recieved_msg = Arrays.toString(bytes);
        parse();
    }

    private void parse() {
        recieved_msg = recieved_msg.replace("\"","").replace("[","").replace("]","").trim();
        if(recieved_msg.length() > 1 && recieved_msg.contains(",")) {
            String[] arr = recieved_msg.split(",");
            location = arr[0];
            for(int i=1;i<arr.length-1;i++) {
                if(!(artifacts.contains(arr[i]) && arr[i]!=userId)) {
                    monsters.add(arr[i]);
                }
            }
        }
        String arr[]  = recieved_msg.split(",");
    }

    public List<String> getMonsters() {
        return  monsters;
    }

}
