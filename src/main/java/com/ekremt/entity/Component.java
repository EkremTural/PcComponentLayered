package com.ekremt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_component")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Component extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long pcId;
	private String name;
	@Enumerated(EnumType.STRING)
	private EComponentType type;
}