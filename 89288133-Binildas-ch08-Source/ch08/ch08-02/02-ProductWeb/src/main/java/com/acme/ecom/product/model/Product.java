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

//import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product{

    //@ApiModelProperty(position = 1)
    private String productId;
    
    //@ApiModelProperty(position = 2)
    private String name;
    
    //@ApiModelProperty(position = 3)
    private String code;;
    
    //@ApiModelProperty(position = 4)
    private String title;
    
    //@ApiModelProperty(position = 5)
    private Double price;

    //@ApiModelProperty(position = 6)
    private String category;
}
