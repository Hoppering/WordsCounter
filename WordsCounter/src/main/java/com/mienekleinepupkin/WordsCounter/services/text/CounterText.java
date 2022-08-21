package com.mienekleinepupkin.WordsCounter.services.text;

import com.mienekleinepupkin.WordsCounter.services.utilities.MapUtil;
import java.util.HashMap;
import java.util.Map;

public class CounterText {

  private static String text;

  private static int symbolsWithoutSpaces;
  private static int symbolsWithSpaces;
  private static int words;
  private static int sentences;

  public CounterText(String text) {
    this.text = text;
    setCountAll();
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return this.text;
  }

  private void setCountSymbolsWithoutSpaces() {
    String postText = text.replaceAll("\\s", "");
    this.symbolsWithoutSpaces = postText.length();
  }

  public int getCountSymbolsWithoutSpaces() {
    return symbolsWithoutSpaces;
  }

  private void setCountSymbolsWithSpaces() {
    this.symbolsWithSpaces = text.length();
  }

  public int getCountSymbolsWithSpaces() {
    return symbolsWithSpaces;
  }

  private void setCountWords() {
    String preText = text.replaceAll("[^A-Za-zА-Яа-я0-9]", " ");
    String[] words = preText.toLowerCase().split(" ");
    this.words = words.length;
  }

  public int getCountWords() {
    return this.words;
  }

  private void setCountSentences() {
    String sentences = text.replaceAll("[^!.?]", "");
    this.sentences = sentences.length();
  }

  public int getCountSentences() {
    return this.sentences;
  }

  public void setCountAll() {
    setCountSymbolsWithoutSpaces();
    setCountSymbolsWithSpaces();
    setCountWords();
    setCountSentences();
  }

  //Convert received text to map (key is word, value is count of this word) and return sort map by value
  public static Map<String, Integer> getMapOfWords() {
    Map<String, Integer> mapWords = new HashMap<String, Integer>();
    String preText = text.replaceAll("[^A-Za-zА-Яа-я0-9]", " ");
    String[] words = preText.toLowerCase().split(" ");

    for (int i = 0; i < words.length; i++) {
      if (mapWords.containsKey(words[i])) {
        int wordsNumber = mapWords.get(words[i]);
        mapWords.replace(words[i], wordsNumber += 1);
      } else {
        mapWords.put(words[i], 1);
      }
    }
    mapWords = MapUtil.sortMapByDESC(mapWords);
    mapWords.remove(" ");
    return mapWords;
  }
}

