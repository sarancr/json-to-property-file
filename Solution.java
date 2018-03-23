
/*
Using the Helper API defined below, create a utility that given a json file (i.e. 'itunes.json') will generate a properties file (i.e. itunes.properties)

sample input itunes.json file:

{
  "config": {
    "all": {
      "itunes": {
        "applicationId": 1234,
        "globalUrl": "https://www.itunes.com"
      }
    },
    "dc1": {
      "itunes": {
        "supportedRegions": ['United States', 'Canada', 'Mexico']
      }
    },
    "dc2": {
      "itunes": {
        "supportedRegions": ['Sweden', 'Finland', 'Denmark']
      }
    }
  }
}

sample output itunes.properties file:

config.all.itunes.applicationId=1234
config.all.itunes.globalUrl=https://www.itunes.com
config.dc1.itunes.supportedRegions[0]=United States
config.dc1.itunes.supportedRegions[1]=Canada
config.dc1.itunes.supportedRegions[2]=Mexico
config.dc2.itunes.supportedRegions[0]=Sweden
config.dc2.itunes.supportedRegions[1]=Finland
config.dc2.itunes.supportedRegions[2]=Denmark


NOTE: Your solution needs to work for any JSON file, not just the example above.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// Use the methods in this Helper API to read through the json file. YOU DO NOT NEED TO IMPLEMENT THIS INTERFACE.
interface Helper {
    /**
     * Returns Object that is either a Map or a List
     */
    Object parseJson(String filename);

    /**
     * Returns true if object is a Map
     */
    Boolean isMap(Object o);

    /**
     * Returns true if object is a List
     */
    Boolean isList(Object o);

    /**
     * Returns true if object is a int
     */
    Boolean isInt(Object o);

    /**
     * Returns true if object is a String
     */
    Boolean isString(Object o);

    /**
     * Writes Properties props into a file. i.e
     */
    void writePropertiesFile(Map<String, String> props, String filename);
}

class HelperImpl implements  Helper{


    @Override
    public Object parseJson(String filename) {
        return null;
    }

    @Override
    public Boolean isMap(Object o) {
        return o instanceof Map;
    }

    @Override
    public Boolean isList(Object o) {
        return o instanceof List;
    }

    @Override
    public Boolean isInt(Object o) {
        return o instanceof  Integer;
    }

    @Override
    public Boolean isString(Object o) {
        return o instanceof String;
    }

    @Override
    public void writePropertiesFile(Map<String, String> props, String filename) {

    }
}

class Solution {
    /*
     * Helper instance to use in your solution. Assume this instance variable is already initialized and can be used.
     */
    Helper helper = new HelperImpl();


    private void addProp(Object o,List<String> keys,
                         Map<String, String> props ){
        String key = keys.size() > 1 ? keys.stream()
                .collect(Collectors.joining(".")) : keys.get(0);

        if(helper.isString(o)){
            props.put(key, (String) o);
        }else if(helper.isInt(o)){
            props.put(key, Integer.toString((Integer) o));
        }

        removeLastKey(keys);
    }

    private void removeLastKey(List<String> keys){
        keys.remove(keys.size() - 1);
    }


    private void processList(List<Object> list, List<String> keys,
                             Map<String, String> props){
        int i = 0;
        for(Object listItem : list){
            if(helper.isInt(listItem) || helper.isString(listItem)) {
                keys.add("[" + i + "]"); ++i;
                addProp(listItem, keys, props);
            }else{
                buildProperty(listItem, keys, props);
            }
        }
        removeLastKey(keys);
    }

    private void processMap(Map<String, Object> map, List<String> keys,
                            Map<String, String> props){
        map.forEach( (key, val) -> {
            keys.add(key);
            if(helper.isMap(val)){
                buildProperty(val, keys, props);
                removeLastKey(keys);
            }else if(helper.isList(val)){
                processList((List) val, keys, props);
            }else{
                addProp(val, keys, props);
            }
        });
    }

    private void buildProperty(Object o, List<String> keys,
                               Map<String, String> props){

        if(helper.isMap(o)) {
          processMap((Map<String, Object>) o, keys, props);
        }else if(helper.isList(o)){
            processList((List<Object>)o, keys, props);
        }
    }

    public  void jsonToProperties(String json, String filename){
        Object jsonObj = helper.parseJson(json);
        Map<String, String> props = new HashMap();
        buildProperty(jsonObj, new ArrayList(), props);
        helper.writePropertiesFile(props, filename);
    }

    public static void main(String[] args) {
        //Test
       // new Solution().jsonToProperties(args[0], args[1]);
        new Solution().test();
    }


    /*
    {
  "config": {
    "all": {
      "itunes": {
        "applicationId": 1234,
        "globalUrl": "https://www.itunes.com"
      }
    },
    "dc1": {
      "itunes": {
        "supportedRegions": ['United States', 'Canada', 'Mexico']
      }
    },
    "dc2": {
      "itunes": {
        "supportedRegions": ['Sweden', 'Finland', 'Denmark']
      }
    }
  }
}
     */

    public void test(){

        Map<String, String> props = new LinkedHashMap();

        Map<String, Object> root = new LinkedHashMap();

        List<Object> connections = new ArrayList();
        connections.add("GSHDF");
        connections.add(new Integer(32));
        connections.add("YEHD");
        //root.put("config", connections);

        Map<String, Object> level = new LinkedHashMap();
        level.put("firstname", "Saranya");
        level.put("lastname", "Rajendran");
        level.put("connections", connections);
        root.put("config", level);

        Map<String, Object> addr1 = new LinkedHashMap();
        addr1.put("city", "Fremont");
        addr1.put("state", "CA");
        addr1.put("zip", new Integer(94538));
        level.put("addr1", addr1);

        Map<String, Object> addr2 = new LinkedHashMap();
        addr2.put("city", "New York");
        addr2.put("state", "NY");
        addr2.put("zip", new Integer(13912));
        level.put("addr2", addr2);

        Map<String, Object> addr2Codes = new LinkedHashMap();
        addr2Codes.put("code1", "HS2332");
        addr2Codes.put("code2", "S3424");
        addr2.put("code", addr2Codes);

        buildProperty(root, new ArrayList(), props);

        props.forEach( (k, v) -> System.out.println(k + " = " + v));

    }
}
