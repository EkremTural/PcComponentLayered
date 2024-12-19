package com.ekremt.controller;


import java.util.Locale;
import java.util.Scanner;

import static com.ekremt.constant.ConsoleColors.*;


public abstract class BaseController implements AutoCloseable {
	
	protected final Scanner scanner;
	private static final int DEFAULT_WIDTH = 30;
	public BaseController() {
		this.scanner = new Scanner(System.in);
	}
	
	protected void printTitle(String metin) {
		printTitle(DEFAULT_WIDTH, metin);
	}
	
	protected void printTitle(int starCount, String text) {
		// En az text uzunluğu + 4 karakter olmalı (2 yıldız + 2 boşluk)
		int minWidth = text.length() + 4;
		int width = Math.max(starCount, minWidth);
		
		String starLine = COLOR_RED + "*".repeat(width) + COLOR_RESET;
		int paddingLength = width - 2;  // 2 yıldız için
		int leftPadding = (paddingLength - text.length()) / 2;
		int rightPadding = paddingLength - leftPadding - text.length();
		
		String centeredText = "*%s%s%s*".formatted(
				" ".repeat(Math.max(0, leftPadding)),
				WHITE_BACKGROUND + COLOR_ORANGE + text.toUpperCase(Locale.ROOT) + COLOR_RESET,
				" ".repeat(Math.max(0, rightPadding))
		);
		
		System.out.println(starLine);
		System.out.println(COLOR_RED + "*" + COLOR_RESET +
				                   centeredText.substring(1, centeredText.length() - 1) +
				                   COLOR_RED + "*" + COLOR_RESET);
		System.out.println(starLine);
	}
	
	protected void printMenuOptions(String... options) {
		int maxOptionLength = 0;
		for (String option : options) {
			maxOptionLength = Math.max(maxOptionLength, option.length() + 3);
		}
		int starCount = Math.max(maxOptionLength + 4, DEFAULT_WIDTH);
		printMenuOptions(starCount, options);
	}
	
	protected void printMenuOptions(int starCount, String... options) {
		String starLine = COLOR_RED + "*".repeat(starCount) + COLOR_RESET;
		
		for (int i = 0; i < options.length; i++) {
			String option = "%d- %s".formatted(i + 1, options[i]);
			int spacesNeeded = Math.max(0, starCount - 2 - option.length());
			String spaces = " ".repeat(spacesNeeded);
			System.out.println(COLOR_RED + "*" + COLOR_RESET +
					                   option + spaces +
					                   COLOR_RED + "*" + COLOR_RESET);
		}
		System.out.println(starLine);
	}
	
	protected int readInt(String soru) {
		System.out.print(soru + " ");
		
		while (true) {
			try {
				if (scanner.hasNextInt()) {
					int value = scanner.nextInt();
					scanner.nextLine();
					return value;
				}
				printErrorMessage("Geçersiz giriş! Lütfen sadece sayı giriniz.");
				System.out.print(soru + " ");
				scanner.nextLine();
			} catch (Exception e) {
				printErrorMessage("Terminalden değer alırken hata meydana geldi.");
				scanner.nextLine();
			}
		}
	}
	protected Long readLong(String soru) {
		System.out.print(soru + " ");
		while (true) {
			try {
				if (scanner.hasNextLong()) {
					Long value = scanner.nextLong();
					scanner.nextLine();
					return value;
				}
				printErrorMessage("Geçersiz giriş! Lütfen sadece sayı giriniz.");
				System.out.print(soru + " ");
				scanner.nextLine();
			}
			catch (Exception e) {
				printErrorMessage("Terminalden değer alırken hata meydana geldi.");
				scanner.nextLine();
			}
		}
	}
	
	protected String readString(String soru) {
		System.out.print(soru + " ");
		return scanner.nextLine().trim();
	}
	
	protected void printSuccessMessage(String message) {
		System.out.println(COLOR_GREEN + message + COLOR_RESET);
	}
	
	protected void printErrorMessage(String message) {
		System.out.println(COLOR_RED + message + COLOR_RESET);
	}
	@Override
	public void close() {
		if (scanner != null) {
			scanner.close();
		}
	}
	
	
}