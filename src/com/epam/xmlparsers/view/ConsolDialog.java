package com.epam.xmlparsers.view;

import java.util.Scanner;

import com.epam.xmlparsers.service.ParseService;

public class ConsolDialog {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose method for parse:\n1 - SAX\n2 - StAX\n3 - DOM\n0 - Exit\n");
		int request;
		String response = "";
		do {
			request = sc.nextInt();
			switch(request) {
			case 1:
				response = ParseService.saxParse();
				break;
			case 2:
				response = ParseService.staxParse();
				break;
			case 3:
				response = ParseService.domParse();
				break;
			}
			System.out.println(response);
		} while (request != 0);
		sc.close();
	}
}
