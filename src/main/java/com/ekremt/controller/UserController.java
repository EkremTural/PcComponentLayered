package com.ekremt.controller;


import com.ekremt.entity.User;
import com.ekremt.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserController extends BaseController {
	private final UserService userService;
	//Constructor Injection
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	public void showUserMenu() {
		while (true) {
			printTitle("KULLANICI İŞLEMLERİ");
			printMenuOptions(
					"Kullanıcı Ekle",
					"Kullanıcı Listele",
					"Kullanıcı Ara",
					"Kullanıcı Güncelle",
					"Kullanıcı Sil",
					"Ana Menüye Dön"
			);
			
			int choice = readInt("Lütfen bir işlem seçiniz:");
			
			switch (choice) {
				case 1 -> addUser();
				case 2 -> listUsers();
				case 3 -> searchUserMenu();
				case 4 -> updateUser();
				case 5 -> deleteUser();
				case 6 -> {
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	private void addUser() {
		printTitle("KULLANICI EKLE");
		try {
			String name = readString("Adı:");
			String address = readString("Adres:");
			String phone = readString("Telefon:");
			int age = readInt("Yaş:");
			
			User user = User.builder()
			                .name(name)
			                .address(address)
			                .phone(phone)
			                .age(age)
			                .createAt(System.currentTimeMillis())
			                .state(1)
			                .build();
			
			userService.save(user);
			printSuccessMessage("Kullanıcı başarıyla eklendi.");
		}
		catch (Exception e) {
			printErrorMessage("Kullanıcı eklenirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void listUsers() {
		printTitle("KULLANICI LİSTESİ");
		try {
			List<User> users = userService.findAll();
			if (users.isEmpty()) {
				printErrorMessage("Kayıtlı kullanıcı bulunamadı.");
				return;
			}
			
			users.forEach(user -> {
				System.out.println("\nKullanıcı ID: " + user.getId());
				System.out.println("Ad: " + user.getName());
				System.out.println("Adres: " + user.getAddress());
				System.out.println("Telefon: " + user.getPhone());
				System.out.println("Yaş: " + user.getAge());
				System.out.println("-".repeat(30));
			});
		}
		catch (Exception e) {
			printErrorMessage("Kullanıcılar listelenirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void searchUserMenu() {
		while (true) {
			printTitle("KULLANICI ARAMA");
			printMenuOptions(
					"İsme Göre Ara",
					"Adrese Göre Ara",
					"Yaş Aralığına Göre Ara",
					"Önceki Menüye Dön"
			);
			
			int choice = readInt("Lütfen arama türü seçiniz:");
			
			switch (choice) {
				case 1 -> searchUserByName();
				case 2 -> searchUserByAddress();
				case 3 -> searchUserByAge();
				case 4 -> {
					return;
				}
				default -> printErrorMessage("Geçersiz seçim!");
			}
		}
	}
	
	private void updateUser() {
		printTitle("KULLANICI GÜNCELLE");
		try {
			Long userId = readLong("Güncellenecek kullanıcı ID:");
			Optional<User> userOpt = userService.findById(userId);
			
			if (userOpt.isEmpty()) {
				printErrorMessage("Belirtilen ID'ye sahip kullanıcı bulunamadı.");
				return;
			}
			
			User user = userOpt.get();
			System.out.println("\nMevcut Bilgiler:");
			System.out.println("Ad: " + user.getName());
			System.out.println("Adres: " + user.getAddress());
			System.out.println("Telefon: " + user.getPhone());
			System.out.println("Yaş: " + user.getAge());
			System.out.println("\nYeni bilgileri giriniz (Değişiklik yapmak istemediğiniz alanları boş bırakınız):");
			
			String name = readString("Yeni ad (" + user.getName() + "):");
			String address = readString("Yeni adres (" + user.getAddress() + "):");
			String phone = readString("Yeni telefon (" + user.getPhone() + "):");
			String ageStr = readString("Yeni yaş (" + user.getAge() + "):");
			
			if (!name.isEmpty()) user.setName(name);
			if (!address.isEmpty()) user.setAddress(address);
			if (!phone.isEmpty()) user.setPhone(phone);
			if (!ageStr.isEmpty()) user.setAge(Integer.parseInt(ageStr));
			
			user.setUpdateAt(System.currentTimeMillis());
			userService.update(user);
			printSuccessMessage("Kullanıcı başarıyla güncellendi.");
		}
		catch (Exception e) {
			printErrorMessage("Kullanıcı güncellenirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void deleteUser() {
		printTitle("KULLANICI SİL");
		try {
			Long userId = readLong("Silinecek kullanıcı ID:");
			if (userService.deleteById(userId)) {
				printSuccessMessage("Kullanıcı başarıyla silindi.");
			}
			else {
				printErrorMessage("Belirtilen ID'ye sahip kullanıcı bulunamadı.");
			}
		}
		catch (Exception e) {
			printErrorMessage("Kullanıcı silinirken hata oluştu: " + e.getMessage());
		}
	}
	
	private void searchUserByName() {
		printTitle("İSME GÖRE KULLANICI ARAMA");
		
	}
	
	private void searchUserByAddress() {
		printTitle("ADRESE GÖRE KULLANICI ARAMA");
		
	}
	
	private void searchUserByAge() {
		printTitle("YAŞ ARALIGINA GÖRE KULLANICI ARAMA");
		
	}
	
	private void displaySearchResults(List<User> users) {
		if (users.isEmpty()) {
			printErrorMessage("Arama kriterlerine uygun kullanıcı bulunamadı.");
			return;
		}
		
		System.out.println("\nBulunan Kullanıcılar:");
		users.forEach(user -> {
			System.out.println("\nKullanıcı ID: " + user.getId());
			System.out.println("Ad: " + user.getName());
			System.out.println("Adres: " + user.getAddress());
			System.out.println("Telefon: " + user.getPhone());
			System.out.println("Yaş: " + user.getAge());
			System.out.println("-".repeat(30));
		});
	}
	
	
	public void listUserPcsAndComponents() {
		printTitle("KULLANICI BİLGİSAYAR VE BİLEŞENLERİ");
		
	}
	
	public void listAllUsersWithPcsAndComponents() {
		printTitle("TÜM KULLANICILAR VE BİLGİSAYARLARI");
		
	}
	
	public void findUsersByComponent() {
		printTitle("BİLEŞENE SAHİP KULLANICILAR");
		
	}
	
	public void showUserPcCounts() {
		printTitle("KULLANICI BİLGİSAYAR SAYILARI");
		
	}
}