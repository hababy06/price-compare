package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor // 無參數建構子，給 JPA 用
@AllArgsConstructor // 全參數建構子
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    // <<<< 新增：只帶 name 的建構子
    public Store(String name) {
        this.name = name;
    }
}