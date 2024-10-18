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
package com.acme.ecom.product.controller;

import com.acme.ecom.product.model.Product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@CrossOrigin
@RestController
public class ProductRestController{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);
	
	@Value("${acme.PRODUCT_SERVICE_URL}")
	private String PRODUCT_SERVICE_URL;

	@Autowired
	public RestTemplate restTemplate;
	
	@Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CollectionModel<com.acme.ecom.product.hateoas.model.Product>> getAllProducts() {

    	LOGGER.info("Delegating to product µ service GET");
		ParameterizedTypeReference<List<com.acme.ecom.product.model.Product>> responseTypeRef = new ParameterizedTypeReference<List<com.acme.ecom.product.model.Product>>() {

		};
		ResponseEntity<List<com.acme.ecom.product.model.Product>> responseEntity = restTemplate
				.exchange(PRODUCT_SERVICE_URL, HttpMethod.GET, (HttpEntity<Product>) null, responseTypeRef);

		Link links[] = { linkTo(methodOn(ProductRestController.class).getAllProducts()).withSelfRel(),
				linkTo(methodOn(ProductRestController.class).getAllProducts()).withRel("getAllProducts") };

		List<com.acme.ecom.product.hateoas.model.Product> list = new ArrayList<com.acme.ecom.product.hateoas.model.Product>();
		for (com.acme.ecom.product.model.Product product : responseEntity.getBody()) {
			com.acme.ecom.product.hateoas.model.Product productHateoas = convertEntityToHateoasEntity(product);
			list.add(productHateoas
					.add(linkTo(methodOn(ProductRestController.class).getProduct(productHateoas.getProductId()))
							.withSelfRel()));
		}
		list.forEach(item -> LOGGER.debug(item.toString()));
		CollectionModel<com.acme.ecom.product.hateoas.model.Product> result = CollectionModel.of(list, links);
		return new ResponseEntity<CollectionModel<com.acme.ecom.product.hateoas.model.Product>>(result, HttpStatus.OK);
	}

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.acme.ecom.product.hateoas.model.Product> getProduct(@PathVariable("productId") String productId) {
        
    	LOGGER.info("Delegating to product µ service GET");
    	String uri = PRODUCT_SERVICE_URL + "/" + productId;
    	com.acme.ecom.product.model.Product productRetreived = restTemplate.getForObject(uri, com.acme.ecom.product.model.Product.class);
    	com.acme.ecom.product.hateoas.model.Product productHateoas = convertEntityToHateoasEntity(productRetreived);
    	productHateoas.add(linkTo(methodOn(ProductRestController.class).getProduct(productHateoas.getProductId())).withSelfRel());
        return new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(productHateoas, HttpStatus.OK);

    }
    
    @RequestMapping(value = "/productsweb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.acme.ecom.product.hateoas.model.Product> addProduct(@RequestBody com.acme.ecom.product.model.Product product, UriComponentsBuilder ucBuilder) {
        
    	LOGGER.info("Delegating  to product µ service POST");
    	com.acme.ecom.product.model.Product productRecieved= restTemplate.postForObject( PRODUCT_SERVICE_URL, product, com.acme.ecom.product.model.Product.class);
    	com.acme.ecom.product.hateoas.model.Product productHateoas = convertEntityToHateoasEntity(productRecieved); 
    	HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/products/{id}").buildAndExpand(product.getProductId()).toUri());
        productHateoas.add(linkTo(methodOn(ProductRestController.class).getProduct(productHateoas.getProductId())).withSelfRel());
        return new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(productHateoas, HttpStatus.OK);

    }

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<com.acme.ecom.product.hateoas.model.Product> deleteProduct(@PathVariable("productId")String productId) {
    	LOGGER.info("Delegating  to product µ service DELETE");
    	restTemplate.delete(PRODUCT_SERVICE_URL + "/" +productId);
    	return new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(HttpStatus.NO_CONTENT);
	}

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<com.acme.ecom.product.hateoas.model.Product> updateProduct(@PathVariable("productId")String productId,@RequestBody com.acme.ecom.product.model.Product product) {
    	 
    	LOGGER.info("Delegating  to product µ service PUT");
    	restTemplate.put( PRODUCT_SERVICE_URL + "/" + productId, product, com.acme.ecom.product.model.Product.class);
    	com.acme.ecom.product.hateoas.model.Product productHateoas = convertEntityToHateoasEntity(product); 
    	productHateoas.add(linkTo(methodOn(ProductRestController.class).getProduct(productHateoas.getProductId())).withSelfRel());
        return new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(productHateoas, HttpStatus.OK);
	}
    
    private com.acme.ecom.product.hateoas.model.Product convertEntityToHateoasEntity(com.acme.ecom.product.model.Product product){
    	return  modelMapper.map(product,  com.acme.ecom.product.hateoas.model.Product.class);
    }
}
