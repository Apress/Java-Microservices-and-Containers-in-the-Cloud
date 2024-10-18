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
package com.acme.ecom.product;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.repository.ProductRepository;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Component
public class InitializationComponent {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductRepository productRepository;

	@PostConstruct
    private void init(){

		logger.info("Start");
		logger.debug("Doing Nothing...");

		//Deleting all existing data on start.....

/*
		productRepository.deleteAll();


    	Product product = new Product();
    	product.setName("Kamsung D3");
    	product.setCode("KAMSUNG-TRIOS");
    	product.setTitle("Kamsung Trios 12 inch , black , 12 px ....");
    	product.setPrice(12000.00);
    	productRepository.save(product);

    	product = new Product();
    	product.setName("Lokia Pomia");
    	product.setCode("LOKIA-POMIA");
    	product.setTitle("Lokia 12 inch , white , 14px ....");
    	product.setPrice(9000.00);
    	productRepository.save(product);
*/

    	//TODO: Add rest of products and catagories...........
		logger.info("End");
    }
}
