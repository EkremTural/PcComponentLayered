package com.ekremt.init;


import com.ekremt.entity.Component;
import com.ekremt.entity.EComponentType;
import com.ekremt.entity.Pc;
import com.ekremt.entity.User;
import com.ekremt.service.ComponentService;
import com.ekremt.service.PcService;
import com.ekremt.service.UserService;

public class DataInitializer {
	
	private final UserService userService;
	private final PcService pcService;
	private final ComponentService componentService;
	
	public DataInitializer(UserService userService, PcService pcService, ComponentService componentService) {
		this.userService = userService;
		this.pcService = pcService;
		this.componentService = componentService;
	}
	
	public void initData() {
		// Kullanıcılar
		User user1 = User.builder()
		                 .name("Ahmet Yılmaz")
		                 .address("İstanbul/Kadıköy")
		                 .phone("555-111-2233")
		                 .age(25)
		                 .state(1)
		                 .createAt(System.currentTimeMillis())
		                 .build();
		
		User user2 = User.builder()
		                 .name("Ayşe Demir")
		                 .address("Ankara/Çankaya")
		                 .phone("555-444-5566")
		                 .age(30)
		                 .state(1)
		                 .createAt(System.currentTimeMillis())
		                 .build();
		
		user1 = userService.save(user1);
		user2 = userService.save(user2);
		
		// Bilgisayarlar
		Pc pc1 = Pc.builder()
		           .name("İş Bilgisayarı")
		           .userId(user1.getId())
		           .state(1)
		           .createAt(System.currentTimeMillis())
		           .build();
		
		Pc pc2 = Pc.builder()
		           .name("Oyun Bilgisayarı")
		           .userId(user1.getId())
		           .state(1)
		           .createAt(System.currentTimeMillis())
		           .build();
		
		Pc pc3 = Pc.builder()
		           .name("Ev Bilgisayarı")
		           .userId(user2.getId())
		           .state(1)
		           .createAt(System.currentTimeMillis())
		           .build();
		
		pc1 = pcService.save(pc1);
		pc2 = pcService.save(pc2);
		pc3 = pcService.save(pc3);
		
		// PC1 Bileşenleri (İş Bilgisayarı)
		componentService.save(Component.builder()
		                               .name("Intel i5-12400")
		                               .type(EComponentType.CPU)
		                               .pcId(pc1.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		componentService.save(Component.builder()
		                               .name("Crucial 16GB DDR4")
		                               .type(EComponentType.RAM)
		                               .pcId(pc1.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		componentService.save(Component.builder()
		                               .name("MSI B660M")
		                               .type(EComponentType.MAINBOARD)
		                               .pcId(pc1.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		// PC2 Bileşenleri (Oyun Bilgisayarı)
		componentService.save(Component.builder()
		                               .name("AMD Ryzen 7 5800X")
		                               .type(EComponentType.CPU)
		                               .pcId(pc2.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		componentService.save(Component.builder()
		                               .name("G.Skill 32GB DDR4")
		                               .type(EComponentType.RAM)
		                               .pcId(pc2.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		componentService.save(Component.builder()
		                               .name("ASUS ROG X570")
		                               .type(EComponentType.MAINBOARD)
		                               .pcId(pc2.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		componentService.save(Component.builder()
		                               .name("NVIDIA RTX 3080")
		                               .type(EComponentType.GPU)
		                               .pcId(pc2.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		// PC3 Bileşenleri (Ev Bilgisayarı)
		componentService.save(Component.builder()
		                               .name("Intel i3-12100")
		                               .type(EComponentType.CPU)
		                               .pcId(pc3.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		componentService.save(Component.builder()
		                               .name("Kingston 8GB DDR4")
		                               .type(EComponentType.RAM)
		                               .pcId(pc3.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		componentService.save(Component.builder()
		                               .name("ASRock H610M")
		                               .type(EComponentType.MAINBOARD)
		                               .pcId(pc3.getId())
		                               .state(1)
		                               .createAt(System.currentTimeMillis())
		                               .build());
		
		System.out.println("Demo veriler başarıyla oluşturuldu!");
	}
}