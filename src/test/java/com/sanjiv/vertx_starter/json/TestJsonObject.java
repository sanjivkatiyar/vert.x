package com.sanjiv.vertx_starter.json;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestJsonObject {

  @Test
  void jsonObjectCanBeMapped(){

    final JsonObject jsonObject= new JsonObject();
    jsonObject.put("1", "Sanjiv Katiyar");
    jsonObject.put("2", "Shreyas Katiyar");

    Assertions.assertEquals("{\"1\":\"Sanjiv Katiyar\",\"2\":\"Shreyas Katiyar\"}", jsonObject.encode());

    JsonObject decodedJsonOnject = new JsonObject("{\"1\":\"Sanjiv Katiyar\",\"2\":\"Shreyas Katiyar\"}");

    Assertions.assertEquals(jsonObject, decodedJsonOnject);

  }

  @Test
  void jsonObjectCanBeCreatedFromHashMap(){
    Map<String, Object> map = new HashMap<>();
    map.put("1","Ishi Katiyar");
    map.put("1","Shreyas Katiyar");

    JsonObject jsonObject = new JsonObject(map);
    Assertions.assertEquals(map, jsonObject.getMap());

  }
}
