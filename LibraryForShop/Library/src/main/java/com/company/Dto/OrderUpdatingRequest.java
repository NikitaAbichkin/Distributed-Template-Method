package com.company.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor от Lombok здесь имеет важный нюанс (подробнее ниже)
public class OrderUpdatingRequest extends OrderRequest {
    // Оставляем ТОЛЬКО то, чего нет у родителя
    private Long id;
}