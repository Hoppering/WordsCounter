package com.mienekleinepupkin.WordsCounter.services.pdf;

import com.mienekleinepupkin.WordsCounter.services.storage.FileStorageService;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

public class DocumentPDF {

  private static final String RESOURCES_DIR_PDF = FileStorageService.FILE_STORAGE_LOCATION + "\\";

  public static void convertToPDF(String textWithoutExtension) throws IOException, FOPException, TransformerException {
    // the XSL FO file
    File xsltFile = new File(RESOURCES_DIR_PDF + "\\template.xsl");
    // the XML file which provides the input
    StreamSource xmlSource = new StreamSource(new File(RESOURCES_DIR_PDF + "\\data.xml"));
    // create an instance of fop factory
    FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    // a user agent is needed for transformation
    FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
    // Setup output
    OutputStream out;
    out = new java.io.FileOutputStream(
        FileStorageService.FILE_STORAGE_LOCATION  + "\\pdf\\" + textWithoutExtension + ".pdf");

    try {
      // Construct fop with desired output format
      Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

      // Setup XSLT
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

      // Resulting SAX events (the generated FO) must be piped through to
      // FOP
      Result res = new SAXResult(fop.getDefaultHandler());

      // Start XSLT transformation and FOP processing
      // That's where the XML is first transformed to XSL-FO and then
      // PDF is created
      transformer.transform(xmlSource, res);
    } finally {
      out.close();
    }
  }
}
