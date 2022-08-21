package com.mienekleinepupkin.WordsCounter.services.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextReader {

  public static String textRead(String path) {
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
      }
      String everything = sb.toString();
      return everything;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
