package com.ekremt.controller;


import com.ekremt.entity.Component;
import com.ekremt.entity.EComponentType;
import com.ekremt.service.ComponentService;

import java.util.List;
import java.util.Optional;

public class ComponentController extends BaseController {
	private final ComponentService componentService;
	
	public ComponentController(ComponentService componentService) {
		this.componentService = componentService;
	}
	
	public void showComponentMenu() {
		while (true) {
			printTitle("BİLEŞEN İŞLEMLERİ");
			printMenuOptions(
					"Bileşen Listele",
					"Bileşen Güncelle",
					"Bileşen Sil",
					"Bileşen Arama",
					"Bileşen Raporları",
					"Ana Menüye Dön"
			);
			
			int choice = readInt("Lütfen bir işlem seçiniz:");
			
			switch (choice) {
				case 1 -> listComponents();
				case 2 -> updateComponent();
				case 3 -> deleteComponent();
				case 4 -> searchComponentMenu();
				case 5 -> showReportsMenu();
				case 6 -> {
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	private void listComponents() {
		printTitle("BİLEŞEN LİSTESİ");
		try {
			List<Component> components = componentService.findAll();
			if (components.isEmpty()) {
				printErrorMessage("Kayıtlı bileşen bulunamadı.");
				return;
			}
			
			displayComponents(components);
		} catch (Exception e) {
			printErrorMessage("Bileşenler listelenirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void updateComponent() {
		printTitle("BİLEŞEN GÜNCELLE");
		try {
			Long componentId = readLong("Güncellenecek bileşen ID:");
			Optional<Component> componentOpt = componentService.findById(componentId);
			
			if (componentOpt.isEmpty()) {
				printErrorMessage("Bileşen bulunamadı.");
				return;
			}
			
			Component component = componentOpt.get();
			System.out.println("\nMevcut Bilgiler:");
			System.out.println("Ad: " + component.getName());
			System.out.println("Tip: " + component.getType());
			System.out.println("PC ID: " + component.getPcId());
			
			String name = readString("Yeni ad (" + component.getName() + "): ");
			
			System.out.println("\nBileşen Tipleri:");
			EComponentType[] types = EComponentType.values();
			for (int i = 0; i < types.length; i++) {
				System.out.println((i + 1) + "- " + types[i]);
			}
			String typeChoice = readString("Yeni tip (Enter tuşuna basın geçmek için): ");
			
			String pcIdStr = readString("Yeni PC ID (" + component.getPcId() + "): ");
			
			if (!name.isEmpty()) component.setName(name);
			if (!typeChoice.isEmpty()) {
				int typeIndex = Integer.parseInt(typeChoice) - 1;
				if (typeIndex >= 0 && typeIndex < types.length) {
					component.setType(types[typeIndex]);
				}
			}
			if (!pcIdStr.isEmpty()) component.setPcId(Long.parseLong(pcIdStr));
			component.setUpdateAt(System.currentTimeMillis());
			
			componentService.update(component);
			printSuccessMessage("Bileşen başarıyla güncellendi.");
		} catch (Exception e) {
			printErrorMessage("Bileşen güncellenirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void deleteComponent() {
		printTitle("BİLEŞEN SİL");
		try {
			Long componentId = readLong("Silinecek bileşen ID:");
			if (componentService.deleteById(componentId)) {
				printSuccessMessage("Bileşen başarıyla silindi.");
			} else {
				printErrorMessage("Bileşen bulunamadı.");
			}
		} catch (Exception e) {
			printErrorMessage("Bileşen silinirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void searchComponentMenu() {
		while (true) {
			printTitle("BİLEŞEN ARAMA");
			printMenuOptions(
					"İsme Göre Ara",
					"Tipe Göre Ara",
					"PC'ye Göre Ara",
					"Önceki Menüye Dön"
			);
			
			int choice = readInt("Lütfen arama türü seçiniz:");
			
			switch (choice) {
				case 1 -> searchByName();
				case 2 -> searchByType();
				case 3 -> searchByPc();
				case 4 -> {
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	private void searchByName() {
		printTitle("İSME GÖRE BİLEŞEN ARAMA");
		
	}
	
	private void searchByType() {
		printTitle("TİPE GÖRE BİLEŞEN ARAMA");
		
	}
	
	private void searchByPc() {
	
	}
	
	private void showReportsMenu() {
		while (true) {
			printTitle("BİLEŞEN RAPORLARI");
			printMenuOptions(
					"Bileşene Sahip PC'leri Listele",
					"En Çok Kullanılan Bileşen Tipi",
					"PC Başına Ortalama Bileşen Sayısı",
					"Bileşen Tipi Dağılımı",
					"Önceki Menüye Dön"
			);
			
			int choice = readInt("Lütfen rapor seçiniz:");
			
			switch (choice) {
				case 1 -> listPcsByComponent();
				case 2 -> showMostUsedComponentType();
				case 3 -> showAverageComponentsPerPc();
				case 4 -> showComponentDistribution();
				case 5 -> {
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	public void listPcsByComponent() {
		printTitle("BİLEŞENE SAHİP PC'LER");
		
	}
	
	public void showMostUsedComponentType() {
		printTitle("EN ÇOK KULLANILAN BİLEŞEN TİPİ");
		
	}
	
	public void showAverageComponentsPerPc() {
		printTitle("PC BAŞINA ORTALAMA BİLEŞEN SAYISI");
		
	}
	
	public void showComponentDistribution() {
		printTitle("BİLEŞEN TİPİ DAĞILIMI");
		
	}
	
	private void displayComponents(List<Component> components) {
		if (components.isEmpty()) {
			printErrorMessage("Bileşen bulunamadı.");
			return;
		}
		
		components.forEach(component -> {
			System.out.println("\nBileşen ID: " + component.getId());
			System.out.println("Ad: " + component.getName());
			System.out.println("Tip: " + component.getType());
			System.out.println("PC ID: " + component.getPcId());
			System.out.println("-".repeat(30));
		});
	}
}