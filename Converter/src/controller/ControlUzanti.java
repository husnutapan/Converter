package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class ControlUzanti {

	public ArrayList<String> uzanyiAnla(File file) {
		ArrayList<String> paths = new ArrayList<String>();
		BufferedReader br = null;

		System.err.println(file.getAbsolutePath().toString());
		String degerler[] = file.getAbsolutePath().toString().split(".");

		String str = file.getAbsolutePath().toString();

		File dir = file.getParentFile();
		String dirPath = dir.getAbsolutePath() + "/";

		System.out.println(dirPath);
		int index = str.lastIndexOf(".");
		String sol = str.substring(0, index);

		StringBuilder builder = new StringBuilder();
		builder.append(sol + ".png");

		System.out.println(builder.toString());

		String dene = str.substring(index + 1).toUpperCase();

		if (dene.equals("JPG")) {
		boolean ok =	readAndWriteImage(str, builder.toString());
		if (ok) {
			paths.add(builder.toString());
		}
			
		} else if (dene.equals("PDF")) {
		ArrayList<String> returns =	convertPDFToPNG(str, dirPath);
		if (returns.size()>0) {
			paths.addAll(returns);
		}
		}

		return paths ;
	}

	public static boolean readAndWriteImage(String readFile, String writeFile) {
		boolean ok = false;
		int width = 963; // width of the image
		int height = 640; // height of the image
		BufferedImage image = null;
		File f = null;
		try {
			f = new File(readFile); // image file path
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(f);
			System.out.println("Reading complete.");
			;
		} catch (IOException e) {
			System.out.println("Error: " + e);
			
		}

		// write image
		try {
			f = new File(writeFile); // output file path
			ImageIO.write(image, "png", f);
			System.out.println("Writing complete.");
			ok = true;
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		
		return ok;

	}

	public static ArrayList<String> convertPDFToPNG(String pdfPath, String destinationPath) {

		ArrayList<String> returnList = new ArrayList<String>();

		try {
			String sourceDir = pdfPath;
			String destinationDir = destinationPath; // converted images from
														// pdf document are
														// saved here

			File sourceFile = new File(sourceDir);
			File destinationFile = new File(destinationDir);
			if (!destinationFile.exists()) {
				destinationFile.mkdir();
				System.out.println("Folder Created -> " + destinationFile.getAbsolutePath());
			}
			if (sourceFile.exists()) {
				System.out.println("Images copied to Folder: " + destinationFile.getName());
				PDDocument document = PDDocument.load(sourceDir);
				List<PDPage> list = document.getDocumentCatalog().getAllPages();
				System.out.println("Total files to be converted -> " + list.size());

				String fileName = sourceFile.getName().replace(".pdf", "");
				int pageNumber = 1;
				for (PDPage page : list) {
					BufferedImage image = page.convertToImage();
					File outputfile = new File(destinationDir + fileName + "_" + pageNumber + ".png");
					System.out.println("Image Created -> " + outputfile.getName());
					returnList.add(destinationDir + fileName + "_" + pageNumber + ".png");
					ImageIO.write(image, "png", outputfile);
					pageNumber++;
				}
				document.close();
				System.out.println("Converted Images are saved at -> " + destinationFile.getAbsolutePath());
			} else {
				System.err.println(sourceFile.getName() + " File not exists");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}

}
