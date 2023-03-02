package com.assessment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class SearchTabController {
    public static String searchDirectory(String directoryPath, String searchString) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            resultBuilder.append("Invalid directory path: " + directoryPath + "\n");
            return resultBuilder.toString();
        }

        File[] files = directory.listFiles(new FilenameFilter() {
            public boolean accept(File directory, String name) {
                return name.toLowerCase().endsWith(".docx");
            }
        });
        resultBuilder.append("Number of files present in the directory " + files.length + "\n");
        if (files.length == 0) {
            resultBuilder.append("No .docx files found in " + directoryPath + "\n");
            return resultBuilder.toString();
        }
        if (!directory.isDirectory()) {
            resultBuilder.append(directoryPath + " is not a directory." + "\n");
            return resultBuilder.toString();
        }

        String[] searchWords = searchString.split(",");
        for (String searchWord : searchWords) {
            resultBuilder.append('\n' + "Results for Keyword: " + searchWord + "\n");

            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".docx")) {
                    int count = searchFile(file, searchWord.trim());
                    if (count > 0) {
                        File subDir = new File(directoryPath + File.separator + searchWord);
                        if (!subDir.exists()) {
                            subDir.mkdirs();
                        }
                        resultBuilder.append("File Name is : " + file.getName() + "    " + " Keyword : "
                                + searchWord.trim() + "    " + " No of Occurence : " + count + "    "
                                + " Directory : " + directoryPath + "\n");
                        Path sourcePath = Paths.get(file.getAbsolutePath());
                        Path targetPath = Paths.get(subDir.getAbsolutePath() + File.separator + file.getName());
                        Files.copy(sourcePath, targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        resultBuilder.append(file.getName()  +"  document copied from the source path "+directoryPath +" to the target path "+ subDir+'\n');
                    } else {
                        resultBuilder.append(searchWord.trim() + " keyword is not present in word document " + file.getName() + "\n");
                    }
                }
            }
        }

        return resultBuilder.toString();
    }

    private static int searchFile(File file, String searchString) throws IOException {
		int count = 0;
		try (FileInputStream fis = new FileInputStream(file); XWPFDocument document = new XWPFDocument(fis)) {
			for (XWPFParagraph paragraph : document.getParagraphs()) {
				if (paragraph.getText().toLowerCase().contains(searchString.toLowerCase())) {
					count++;
				}
			}
		}
		return count;
	}
}
