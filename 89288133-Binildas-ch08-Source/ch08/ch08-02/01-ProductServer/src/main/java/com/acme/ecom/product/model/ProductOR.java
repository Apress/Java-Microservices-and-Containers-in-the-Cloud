/*
 * Copyright (c) 2024/2025 Binildas A Christudas & Apress
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.ecom.product.model;

import org.springframework.data.annotation.Id;

//import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
 
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Data
@NoArgsConstructor
@Entity
@Table(name ="product")
public class ProductOR{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "productid")
	//private Long productId;
    private String productId;

	@Column(name = "prodname")
	private String name;

	@Column(name = "code")
	private String code;;

	@Column(name = "title")
	private String title;

	@Column(name = "price")
	private Double price;

	@Column(name = "category")
	private String category;

}
