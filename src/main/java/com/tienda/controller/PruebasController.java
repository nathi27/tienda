package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tienda.service.ProductoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pruebas") //localhost:8080//pruebas
public class PruebasController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false); // le trae lista de productos
        var categorias = categoriaService.getCategorias(false); // le trae lista de categorias
        model.addAttribute("productos", productos); //lista al html
        model.addAttribute("totalProductos", productos.size()); //total de productos al html
        model.addAttribute("categorias", categorias);//lista de categorias al html
        return "/pruebas/listado"; //creacion del html 
    }

    @GetMapping("/listado/{idCategoria}") //localhost:8080/pruebas/listado/categoria=Monitores (categoria=3)
    public String listado(Model model, Categoria categoria) {
        var productos = categoriaService.getCategoria(categoria).getProductos(); //asociacion
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }
}