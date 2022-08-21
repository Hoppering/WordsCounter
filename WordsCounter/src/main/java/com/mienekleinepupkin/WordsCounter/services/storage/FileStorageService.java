package com.mienekleinepupkin.WordsCounter.services.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

  public static Path FILE_STORAGE_LOCATION = null;

  @Autowired
  public FileStorageService(Environment env) {
    this.FILE_STORAGE_LOCATION = Paths.get(env.getProperty("app.file.upload-dir", "./files"))
        .toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.FILE_STORAGE_LOCATION);
      Files.createDirectories(Path.of(this.FILE_STORAGE_LOCATION + "\\pdf\\"));
    } catch (Exception ex) {
      throw new RuntimeException(
          "Could not create the directory where the uploaded files will be stored.", ex);
    }
  }

  public static String storeFile(MultipartFile file) {
    // Normalize file name
    String fileName =
        new Date().getTime() + "-report." + FilenameUtils.getExtension(file.getOriginalFilename());

    try {
      // Check if the filename contains invalid characters
      if (fileName.contains("..")) {
        throw new RuntimeException(
            "Sorry! Filename contains invalid path sequence " + fileName);
      }

      Path targetLocation = FILE_STORAGE_LOCATION.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return fileName;
    } catch (IOException ex) {
      throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
    }
  }

  public static List<String> getAllCreatedReports() throws IOException {
    return Stream.of(Objects.requireNonNull(
            new File(FileStorageService.FILE_STORAGE_LOCATION + "\\pdf\\").listFiles()))
        .filter(File::isFile)
        .sorted(Comparator.comparing(File::lastModified).reversed())
        .map(File::getName)
        .collect(Collectors.toList());
  }
}