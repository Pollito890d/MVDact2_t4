package com.dantemtzv.springboot_aplicacion.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantemtzv.springboot_aplicacion.dto.ProductoDTO;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @PostMapping("/crear")
    public ProductoDTO crearProducto(@RequestBody ProductoDTO nuevoProducto) {
        nuevoProducto.setId(99);
        return nuevoProducto; // Retorna el objeto en formato JSON
    }
}