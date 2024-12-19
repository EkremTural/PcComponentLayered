package com.ekremt.controller;


import com.ekremt.init.DataInitializer;
import com.ekremt.repository.ComponentRepository;
import com.ekremt.repository.PcRepository;
import com.ekremt.repository.UserRepository;
import com.ekremt.service.ComponentService;
import com.ekremt.service.PcService;
import com.ekremt.service.UserService;

public class MainController extends BaseController {
	private final UserController userController;
	private final PcController pcController;
	private final ComponentController componentController;
	private final UserService userService;
	private final PcService pcService;
	private final ComponentService componentService;
	
	public MainController() {
		// Önce repository'leri oluştur
		UserRepository userRepository = new UserRepository();
		PcRepository pcRepository = new PcRepository();
		ComponentRepository componentRepository = new ComponentRepository();
		
		// Servisleri oluştur
		PcService tempPcService = new PcService(pcRepository, null);
		ComponentService tempComponentService = new ComponentService(componentRepository, tempPcService);
		this.pcService = new PcService(pcRepository, tempComponentService);
		this.componentService = tempComponentService;
		this.userService = new UserService(userRepository, this.pcService, this.componentService);
		
		// Controller'ları oluştur
		this.componentController = new ComponentController(this.componentService);
		this.pcController = new PcController(this.pcService, this.componentService);
		this.userController = new UserController(this.userService);
	}
	
	public void run() {
		while (true) {
			printTitle("BİLGİSAYAR BİLEŞENLERİ YÖNETİM SİSTEMİ");
			printMenuOptions(
					"Kullanıcı İşlemleri",
					"Bilgisayar İşlemleri",
					"Bileşen İşlemleri",
					"Sorgular ve Raporlar",
					"Çıkış"
			);
			
			int choice = readInt("Lütfen bir işlem seçiniz:");
			
			switch (choice) {
				case 1 -> userController.showUserMenu();
				case 2 -> pcController.showPcMenu();
				case 3 -> componentController.showComponentMenu();
				case 4 -> showQueriesMenu();
				case 5 -> {
					printSuccessMessage("Program sonlandırılıyor...");
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	private void showQueriesMenu() {
		while (true) {
			printTitle("SORGULAR VE RAPORLAR");
			printMenuOptions(
					"Kullanıcının Bilgisayarlarını Listele",
					"Kullanıcının Bilgisayar ve Bileşenlerini Listele",
					"Tüm Kullanıcıların Bilgisayar ve Bileşenlerini Listele",
					"Bileşen Arama (PC Listesi)",
					"Bileşene Sahip Kullanıcıları Listele",
					"En Çok Kullanılan Bileşen Raporu",
					"Kullanıcı Bilgisayar Sayıları",
					"Ana Menüye Dön"
			);
			
			int choice = readInt("Lütfen bir rapor seçiniz:");
			
			switch (choice) {
				case 1 -> pcController.searchPcByUser();
				case 2 -> userController.listUserPcsAndComponents();
				case 3 -> userController.listAllUsersWithPcsAndComponents();
				case 4 -> componentController.listPcsByComponent();
				case 5 -> userController.findUsersByComponent();
				case 6 -> componentController.showMostUsedComponentType();
				case 7 -> userController.showUserPcCounts();
				case 8 -> {
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	public void initializeTestData() {
		DataInitializer dataInitializer = new DataInitializer(userService, pcService, componentService);
		dataInitializer.initData();
	}
}