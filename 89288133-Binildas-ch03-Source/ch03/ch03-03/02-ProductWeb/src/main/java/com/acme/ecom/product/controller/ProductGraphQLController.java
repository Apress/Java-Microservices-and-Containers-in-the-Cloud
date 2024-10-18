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

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.acme.ecom.product.graphql.ProductDao;
import com.acme.ecom.product.graphql.ProductCategoryDao;
import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.model.ProductCategory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Controller
public class ProductGraphQLController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductGraphQLController.class);

    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;

    public ProductGraphQLController(ProductDao productDao, ProductCategoryDao productCategoryDao) {
        
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
    }

    @QueryMapping
    public List<Product> products(@Argument int count, @Argument int offset) {

        LOGGER.info("Start");
        return productDao.getProducts(count, offset);
    }

    @MutationMapping
    public Product writeProduct(@Argument String name, @Argument String code, @Argument String title, @Argument Double price, @Argument String category) {

        LOGGER.info("Start");
        return productDao.saveProduct(new Product(null, name, code, title, price, category));
    }

    @SchemaMapping
    public ProductCategory productCategory(Product product) {

        return productCategoryDao.getProductCategory(product.getCategory());
    }

    @SchemaMapping
    public List<Product> products(ProductCategory productCategory) {

        return productDao.getProductsForCategory(productCategory.getName());
    }
}