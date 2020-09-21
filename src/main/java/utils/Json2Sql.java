package utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Json2Sql {
    public static String getSql(JSONObject jsonObject, String type) {
        if (!type.isEmpty()) {
            jsonObject = JSONUtil.parseObj("{\"" + type + "\":" + jsonObject.toString() + "}");
        }
        List<String> setList = new ArrayList<>(jsonObject.keySet());
        for (String set : setList) {
            StringBuilder builder = new StringBuilder();
            JSONObject obj = (JSONObject) jsonObject.get(set);
            Set<String> keys = obj.keySet();
            Set<Map.Entry<String, Object>> entrySets = obj.entrySet();
            switch (set) {
                case "eq":
                    // ==
                    return entrySets.stream().map(e -> e.getKey() + "='" + e.getValue().toString() + "'").collect(Collectors.joining(" and "));
                case "ne":
                    // <>
                    return entrySets.stream().map(e -> e.getKey() + "!='" + e.getValue().toString() + "'").collect(Collectors.joining(" and "));
                case "lt":
                    // <
                    return entrySets.stream().map(e -> e.getKey() + "<'" + e.getValue().toString() + "'").collect(Collectors.joining(" and "));
                case "lte":
                    // <=
                    return entrySets.stream().map(e -> e.getKey() + "<='" + e.getValue().toString() + "'").collect(Collectors.joining(" and "));
                case "gt":
                    //>
                    return entrySets.stream().map(e -> e.getKey() + ">'" + e.getValue().toString() + "'").collect(Collectors.joining(" and "));
                case "gte":
                    //>=
                    return entrySets.stream().map(e -> e.getKey() + ">='" + e.getValue().toString() + "'").collect(Collectors.joining(" and "));
                case "like":
                    //like "like": {"A.type": "%ss%"}
                    return entrySets.stream().map(e -> e.getKey() + " like '" + e.getValue().toString() + "'").collect(Collectors.joining(" and "));
                case "between":
                    // BETWEEN a AND b   BETWEEN: {"A.type": ["X","Y"]}
                    String betweenStr = entrySets.stream().map(e -> {
                        JSONArray jsonArray = (JSONArray) e.getValue();
                        return "(" + e.getKey() + " BETWEEN '" + jsonArray.get(0).toString() + "' AND '" + jsonArray.get(1).toString() + "')";
                    }).collect(Collectors.joining(" and "));
                    return betweenStr;
                case "in":
                    // in  "in":{ "A.id":  ['1',"2","3"] }
                    String inStr = entrySets.stream().map(e -> {
                        JSONArray jsonArray = (JSONArray) e.getValue();
                        String inString = jsonArray.stream().map(in -> "'" + in + "'").collect(Collectors.joining(","));
                        return e.getKey() + " in (" + inString + ")";
                    }).collect(Collectors.joining(" and "));
                    return inStr;
                case "and":
                case "AND":
                case "or":
                case "OR":
                    String express = "and";
                    if (set.equals("or") || set.equals("OR")) {
                        express = "or";
                    }

                    int lengthAnd = keys.size();
                    while (lengthAnd > 1) {
                        builder.append("(${").append(lengthAnd - 1).append("}) ").append(express).append(" ");
                        lengthAnd--;
                    }
                    builder.append("(${0})");
                    List<String> andKeysList = new ArrayList<>(obj.keySet());

                    String andReplaced = builder.toString();
                    List<Map.Entry<String, Object>> entryArrayList = new ArrayList<>(entrySets);

                    for (int j = 0; j < andKeysList.size(); j++) {
                        andReplaced = andReplaced.replace("${" + j + "}", getSql((JSONObject) entryArrayList.get(j).getValue(), entryArrayList.get(j).getKey()));
                    }
                    return andReplaced;
                default:
                    return "";
            }
        }
        return "";
    }
}
