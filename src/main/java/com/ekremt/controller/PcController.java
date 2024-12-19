package com.ekremt.controller;


import com.ekremt.entity.Component;
import com.ekremt.entity.EComponentType;
import com.ekremt.entity.Pc;
import com.ekremt.service.ComponentService;
import com.ekremt.service.PcService;

import java.util.List;
import java.util.Optional;

public class PcController extends BaseController {
	private final PcService pcService;
	private final ComponentService componentService;
	
	public PcController(PcService pcService, ComponentService componentService) {
		this.pcService = pcService;
		this.componentService = componentService;
	}
	
	public void showPcMenu() {
		while (true) {
			printTitle("BİLGİSAYAR İŞLEMLERİ");
			printMenuOptions(
					"Bilgisayar Ekle",
					"Bilgisayar Listele",
					"Kullanıcıya Bilgisayar Ata",
					"Bilgisayar Güncelle",
					"Bilgisayar Sil",
					"Bilgisayara Bileşen Ekle",
					"Bilgisayar Arama",
					"Ana Menüye Dön"
			);
			
			int choice = readInt("Lütfen bir işlem seçiniz:");
			
			switch (choice) {
				case 1 -> addPc();
				case 2 -> listPcs();
				case 3 -> assignPcToUser();
				case 4 -> updatePc();
				case 5 -> deletePc();
				case 6 -> addComponentToPc();
				case 7 -> searchPcMenu();
				case 8 -> {
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	private void addPc() {
		printTitle("BİLGİSAYAR EKLE");
		try {
			String name = readString("Bilgisayar adı:");
			Long userId = readLong("Kullanıcı ID:");
			
			Pc pc = Pc.builder()
			          .name(name)
			          .userId(userId)
			          .createAt(System.currentTimeMillis())
			          .state(1)
			          .build();
			
			pcService.save(pc);
			printSuccessMessage("Bilgisayar başarıyla eklendi.");
		} catch (Exception e) {
			printErrorMessage("Bilgisayar eklenirken hata oluştu: " + e.getMessage());
		}
	}
	
	public void listPcs() {
		printTitle("BİLGİSAYAR LİSTESİ");
		
	}
	
	private void assignPcToUser() {
		printTitle("KULLANICIYA BİLGİSAYAR ATA");
		try {
			Long pcId = readLong("Bilgisayar ID:");
			Optional<Pc> pcOptional = pcService.findById(pcId);
			
			if (pcOptional.isEmpty()) {
				printErrorMessage("Bilgisayar bulunamadı.");
				return;
			}
			
			Long userId = readLong("Yeni kullanıcı ID:");
			Pc pc = pcOptional.get();
			pc.setUserId(userId);
			pc.setUpdateAt(System.currentTimeMillis());
			
			pcService.update(pc);
			printSuccessMessage("Bilgisayar başarıyla kullanıcıya atandı.");
		} catch (Exception e) {
			printErrorMessage("Bilgisayar atama işlemi sırasında hata oluştu: " + e.getMessage());
		}
	}
	
	private void updatePc() {
		printTitle("BİLGİSAYAR GÜNCELLE");
		try {
			Long pcId = readLong("Güncellenecek bilgisayar ID:");
			Optional<Pc> pcOptional = pcService.findById(pcId);
			
			if (pcOptional.isEmpty()) {
				printErrorMessage("Bilgisayar bulunamadı.");
				return;
			}
			
			Pc pc = pcOptional.get();
			System.out.println("\nMevcut Bilgiler:");
			System.out.println("Ad: " + pc.getName());
			System.out.println("Kullanıcı ID: " + pc.getUserId());
			
			String name = readString("Yeni ad (" + pc.getName() + "): ");
			String userIdStr = readString("Yeni kullanıcı ID (" + pc.getUserId() + "): ");
			
			if (!name.isEmpty()) pc.setName(name);
			if (!userIdStr.isEmpty()) pc.setUserId(Long.parseLong(userIdStr));
			pc.setUpdateAt(System.currentTimeMillis());
			
			pcService.update(pc);
			printSuccessMessage("Bilgisayar başarıyla güncellendi.");
		} catch (Exception e) {
			printErrorMessage("Bilgisayar güncellenirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void deletePc() {
		printTitle("BİLGİSAYAR SİL");
		try {
			Long pcId = readLong("Silinecek bilgisayar ID:");
			if (pcService.deleteById(pcId)) {
				printSuccessMessage("Bilgisayar başarıyla silindi.");
			} else {
				printErrorMessage("Bilgisayar bulunamadı.");
			}
		} catch (Exception e) {
			printErrorMessage("Bilgisayar silinirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void addComponentToPc() {
		printTitle("BİLGİSAYARA BİLEŞEN EKLE");
		try {
			Long pcId = readLong("Bilgisayar ID:");
			if (pcService.findById(pcId).isEmpty()) {
				printErrorMessage("Bilgisayar bulunamadı.");
				return;
			}
			
			System.out.println("\nBileşen Tipleri:");
			EComponentType[] types = EComponentType.values();
			for (int i = 0; i < types.length; i++) {
				System.out.println((i + 1) + "- " + types[i]);
			}
			
			int typeChoice = readInt("Bileşen tipi seçiniz (1-" + types.length + "):");
			if (typeChoice < 1 || typeChoice > types.length) {
				printErrorMessage("Geçersiz bileşen tipi!");
				return;
			}
			
			String componentName = readString("Bileşen adı:");
			
			Component component = Component.builder()
			                               .pcId(pcId)
			                               .name(componentName)
			                               .type(types[typeChoice - 1])
			                               .createAt(System.currentTimeMillis())
			                               .state(1)
			                               .build();
			
			componentService.save(component);
			printSuccessMessage("Bileşen başarıyla eklendi.");
		} catch (Exception e) {
			printErrorMessage("Bileşen eklenirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void searchPcMenu() {
		while (true) {
			printTitle("BİLGİSAYAR ARAMA");
			printMenuOptions(
					"İsme Göre Ara",
					"Kullanıcıya Göre Ara",
					"Bileşen Tipine Göre Ara",
					"Önceki Menüye Dön"
			);
			
			int choice = readInt("Lütfen arama türü seçiniz:");
			
			switch (choice) {
				case 1 -> searchPcByName();
				case 2 -> searchPcByUser();
				case 3 -> searchPcByComponentType();
				case 4 -> {
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	public void searchPcByName() {
		printTitle("İSME GÖRE BİLGİSAYAR ARAMA");
		
		
	}
	
	/**
	 * "Kullanıcının Bilgisayarlarını Listele"
	 */
	public void searchPcByUser() {
		printTitle("KULLANICIYA GÖRE BİLGİSAYAR ARAMA");
		Long userId = readLong("Kullanıcı id:");
		List<Pc> allPcByUserId = pcService.findAllPcByUserId(userId);
		displaySearchResults(allPcByUserId);
	}
	
	public void searchPcByComponentType() {
		printTitle("BİLEŞEN TİPİNE GÖRE BİLGİSAYAR ARAMA");
		
	}
	
	private void displaySearchResults(List<Pc> pcList) {
		if (pcList.isEmpty()) {
			printErrorMessage("Arama kriterlerine uygun bilgisayar bulunamadı.");
			return;
		}
		
		System.out.println("\nBulunan Bilgisayarlar:");
		pcList.forEach(System.out::println);
	}
}