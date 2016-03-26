package controller;

import java.io.File;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ControlUzanti controlUzanti = new ControlUzanti();
		File file = new  File("/home/tapan/Belgeler/ekranlar.pdf");
		
		ArrayList<String> a =controlUzanti.uzanyiAnla(file);
		if (a.size()>0) {
			for(String b : a){
				System.out.println(b);
			}
		}
		
		
		
		
	}

}
