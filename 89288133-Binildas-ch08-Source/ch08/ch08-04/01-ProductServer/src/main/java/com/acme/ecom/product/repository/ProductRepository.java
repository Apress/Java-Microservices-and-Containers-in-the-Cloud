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
package com.acme.ecom.product.repository;

import java.util.List;

//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.acme.ecom.product.model.ProductOR;

/**
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
@RepositoryRestResource(collectionResourceRel = "productdata", path = "productdata")
//public interface ProductRepository extends MongoRepository<Product, String>  {
public interface ProductRepository extends CrudRepository<ProductOR, Long> {

	public List<ProductOR> findByCode(@Param("code") String  code);
}
