package com.mienekleinepupkin.WordsCounter.services.xml;

import com.mienekleinepupkin.WordsCounter.services.storage.FileStorageService;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLToPDF {

  private static final String XML_FILE_PATH = FileStorageService.FILE_STORAGE_LOCATION + "\\" +"data.xml";

  public static void createXML(int countSymbolsNoneSpaces, int countSymbolsWithSpaces,
      int countOfWords, int countOfSentences) {
    try {

      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

      Document document = documentBuilder.newDocument();

      // root element
      Element root = document.createElement("document");
      document.appendChild(root);

      Element title = document.createElement("title");
      title.appendChild(document.createTextNode("Frequency analysis report"));

      root.appendChild(title);

      // employee element
      Element body = document.createElement("body");

      root.appendChild(body);

      // firstname element
      Element description = document.createElement("description");
      description.appendChild(document.createTextNode(
          "This report describes the results of the frequency analysis of the text, as well as counting the number of characters, words and sentences."));
      body.appendChild(description);

      Element chartTitle = document.createElement("chart-title");
      chartTitle.appendChild(document.createTextNode("Picture 1 - Frequency chart. Top 25 words"));
      body.appendChild(chartTitle);

      Element tableData = document.createElement("table");
      body.appendChild(tableData);

      Element symbolsNoneSpaces = document.createElement("symbols-none-spaces");
      symbolsNoneSpaces.appendChild(
          document.createTextNode(String.valueOf(countSymbolsNoneSpaces)));
      tableData.appendChild(symbolsNoneSpaces);

      Element symbolsWithSpaces = document.createElement("symbols-with-spaces");
      symbolsWithSpaces.appendChild(
          document.createTextNode(String.valueOf(countSymbolsWithSpaces)));
      tableData.appendChild(symbolsWithSpaces);

      Element words = document.createElement("count-words");
      words.appendChild(document.createTextNode(String.valueOf(countOfWords)));
      tableData.appendChild(words);

      Element sentences = document.createElement("count-sentences");
      sentences.appendChild(document.createTextNode(String.valueOf(countOfSentences)));
      tableData.appendChild(sentences);

      // create the xml file
      //transform the DOM Object to an XML File
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(new File(XML_FILE_PATH));

      transformer.transform(domSource, streamResult);

      System.out.println("Done creating XML File");

    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (TransformerException tfe) {
      tfe.printStackTrace();
    }
  }
}

