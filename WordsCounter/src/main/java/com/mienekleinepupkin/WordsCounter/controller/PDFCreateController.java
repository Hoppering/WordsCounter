package com.mienekleinepupkin.WordsCounter.controller;

import com.mienekleinepupkin.WordsCounter.services.chart.FrequencyChart;
import com.mienekleinepupkin.WordsCounter.services.pdf.DocumentPDF;
import com.mienekleinepupkin.WordsCounter.services.storage.FileStorageService;
import com.mienekleinepupkin.WordsCounter.services.text.CounterText;
import com.mienekleinepupkin.WordsCounter.services.text.TextReader;
import com.mienekleinepupkin.WordsCounter.services.xml.XMLToPDF;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import org.apache.commons.io.FilenameUtils;
import org.apache.fop.apps.FOPException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PDFCreateController {

  //Collect all created reports and return all of them
  @RequestMapping(value = "/files", method = RequestMethod.GET)
  public List<String> getAllFiles() throws IOException {
    //Get all reports sort by last modified
    return FileStorageService.getAllCreatedReports();
  }

  //Get text and in the end create pdf document
  @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
  public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file)
      throws IOException, FOPException, TransformerException {

    String fileName = FileStorageService.storeFile(file);

    //Read received text
    String text = TextReader.textRead(
        FileStorageService.FILE_STORAGE_LOCATION + "\\" + fileName);

    String textWithoutExtension = FilenameUtils.removeExtension(fileName);

    //Create object of the received text
    CounterText counterText = new CounterText(text);

    //Count of words without spaces
    Map<String, Integer> mapWordsWithoutSpaces = CounterText.getMapOfWords();

    //Create XML file that will be converted to PDF document
    XMLToPDF.createXML(counterText.getCountSymbolsWithoutSpaces(),
        counterText.getCountSymbolsWithSpaces(),
        counterText.getCountWords(), counterText.getCountSentences());
    //Create Frequency chart from received text (will be returned as svg file)
    FrequencyChart.createChart(mapWordsWithoutSpaces);
    //Create PDF document
    DocumentPDF.convertToPDF(textWithoutExtension);

    //Delete all unused files sush as chart.svg, data.xml, received text
    Files.deleteIfExists(Paths.get(FileStorageService.FILE_STORAGE_LOCATION + "\\"+"chart.svg"));
    Files.deleteIfExists(Paths.get(FileStorageService.FILE_STORAGE_LOCATION + "\\"+ "data.xml"));
    Files.deleteIfExists(Paths.get(FileStorageService.FILE_STORAGE_LOCATION + "\\" + fileName));

    return ResponseEntity.ok("File uploaded " + fileName + " successfully!");
  }

  //Get a specific file
  @RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
  public void getFile(
      @PathVariable("file_name") String fileName,
      HttpServletResponse response) throws IOException {
    try {
      // get your file as InputStream
      File file = new File(FileStorageService.FILE_STORAGE_LOCATION + "\\pdf\\" + fileName);
      InputStream is = new FileInputStream(file);
      // copy it to response's OutputStream
      org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
      response.setStatus(200);
      response.flushBuffer();
    } catch (IOException ex) {
      System.out.printf("Error writing file to output stream. Filename was '{}'", fileName, ex);
      throw new RuntimeException("IOError writing file to output stream");
    }
  }
}
