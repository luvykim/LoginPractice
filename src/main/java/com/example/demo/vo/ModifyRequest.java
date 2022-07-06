package com.example.demo.vo;

import lombok.Data;

@Data
public class ModifyRequest {
    private String username = "";
    private String password = "";
    private String updatedPassword = "";
}
