package com.mienekleinepupkin.WordsCounter.services.utilities;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtil {

  //Sort map by DESC and choose words, which size is bigger than 3 symbols
  public static Map<String, Integer> sortMapByDESC(Map<String, Integer> mapWords) {
    return mapWords.entrySet().stream()
        .filter(map -> map.getKey().length()>3)
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
  }
}
