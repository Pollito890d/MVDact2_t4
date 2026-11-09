package com.dantemtzv.springboot_aplicacion.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dantemtzv.springboot_aplicacion.dto.ProductoDTO;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Value("${app.titulo}")
    private String tituloApp;

    @Value("${app.autor}")
    private String autorApp;

    // Lista simulada con la nueva firma del constructor (incluye cantidad)
    private List<ProductoDTO> productos = new ArrayList<>(Arrays.asList(
        new ProductoDTO(1, "Laptop Lenovo", 15499.00, "Computo", 10),
        new ProductoDTO(2, "Mouse Inalámbrico", 350.50, "Accesorios", 25),
        new ProductoDTO(3, "Monitor 24 pulg", 3200.00, "Computo", 8),
        new ProductoDTO(4, "Teclado Mecánico", 1200.00, "Accesorios", 15)
    ));

    @GetMapping("/listar")
    public String listarProductos(@RequestParam(name = "categoria", required = false) String categoria, Model model) {
        List<ProductoDTO> filtrados = productos;
        if (categoria != null && !categoria.isBlank()) {
            filtrados = productos.stream()
                    .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                    .collect(Collectors.toList());
        }
        
        model.addAttribute("titulo", tituloApp);
        model.addAttribute("autor", autorApp);
        model.addAttribute("productos", filtrados);
        model.addAttribute("categoriaFiltro", categoria);
        return "productos/listar";
    }

    // El parámetro id ahora es int primitivo
    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable(name = "id") int id, Model model) {
        ProductoDTO producto = productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        model.addAttribute("titulo", "Detalle de Producto");
        model.addAttribute("producto", producto);
        return "productos/detalle";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("titulo", "Registrar Nuevo Producto");
        model.addAttribute("producto", new ProductoDTO());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") ProductoDTO producto, Model model) {
        producto.setId(productos.size() + 1);
        productos.add(producto);
        model.addAttribute("titulo", "Producto Registrado");
        model.addAttribute("producto", producto);
        return "productos/detalle";
    }
}