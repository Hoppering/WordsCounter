package com.mienekleinepupkin.WordsCounter.services.chart;

import com.mienekleinepupkin.WordsCounter.services.storage.FileStorageService;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

public class FrequencyChart {

  private static final String OUTPUT_DIR_SVG = FileStorageService.FILE_STORAGE_LOCATION + "\\";
  public static void createChart(Map<String, Integer> mapFrequencyWords) throws IOException {

    DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

    //Get top 25 of frequently encountered words
    mapFrequencyWords.entrySet().stream()
        .limit(25)
        .forEach( element ->
          line_chart_dataset.addValue(element.getValue(), "words", element.getKey()));

    JFreeChart lineChartObject = ChartFactory.createBarChart(
        "","Words",
        "Count",
        line_chart_dataset, PlotOrientation.HORIZONTAL,
        false,true,false);

    SVGGraphics2D graphics2DSVG = new SVGGraphics2D(640, 480);
    Rectangle rectangleSVG = new Rectangle(0, 0, 640, 480);
    lineChartObject.draw(graphics2DSVG, rectangleSVG);
    File fileSVG = new File(OUTPUT_DIR_SVG + "//chart.svg");
    SVGUtils.writeToSVG(fileSVG, graphics2DSVG.getSVGElement());
  }

}
