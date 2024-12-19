package com.ekremt;


import com.ekremt.controller.MainController;
import com.ekremt.init.DatabaseInitializer;
import com.ekremt.utility.JPAUtility;

public class ComputerPartsManagementApp {
	public static void main(String[] args) {
		try {
			DatabaseInitializer.initialize();
			Thread.sleep(1000);
			
			try (MainController mainController = new MainController()) {
				System.out.println("\n" + "=".repeat(50));
				System.out.println("   BİLGİSAYAR BİLEŞENLERİ YÖNETİM SİSTEMİ");
				System.out.println("=".repeat(50) + "\n");
				
				mainController.initializeTestData();
				mainController.run();
			}
		}
		catch (Exception e) {
			System.err.println("Uygulama çalışırken bir hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				JPAUtility.closeEntityManagerFactory();
				System.out.println("\nUygulama başarıyla kapatıldı.");
			}
			catch (Exception e) {
				System.err.println("Uygulama kapatılırken hata oluştu: " + e.getMessage());
			}
		}
	}
}