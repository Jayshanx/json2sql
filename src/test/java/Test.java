import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import utils.Json2Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "            \"and\": {\n" +
                "                \"or\": {\n" +
                "                    \"and\":{\n" +
                "                        \"eq\": {\n" +
                "                            \"A.carNo\": \"苏A8888\",\n" +
                "                            \"B.carNo\": \"苏A9999\"\n" +
                "                        },\n" +
                "                        \"lt\": {\n" +
                "                            \"B.id\": 100\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"eq\": {\n" +
                "                        \"A.carNo\": \"苏A8888\"\n" +
                "                    },\n" +
                "                    \"lt\": {\n" +
                "                        \"B.id\": 100\n" +
                "                    }\n" +
                "                },\n" +
                "                \"eq\": {\n" +
                "                    \"C.name\": \"hello\"\n" +
                "                },\n" +
                "                \"between\": {\n" +
                "                    \"A.type\": [\"X\",\"Y\"],\n" +
                "                    \"B.date\": [\"2019-09-09\",\"2020-12-12\"]\n" +
                "                },\n" +
                "                \"like\": {\n" +
                "                    \"A.type\": \"%sad%\"\n" +
                "                },\n" +
                "                \"in\":{\n" +
                "                    \"A.id\":  ['1',\"2\",\"3\"],\n" +
                "                    \"B.id\": ['4','5','6','7']\n" +
                "                }\n" +
                "            }\n" +
                "        }\n";

        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        String s = Json2Sql.getSql(jsonObject, "");
        System.out.println(s);
    }
}
