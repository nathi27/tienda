/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.controller;

import com.tienda.domain.Producto;
import com.tienda.service.ProductoService;
import com.tienda.service.FirebaseStorageService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/producto") //cuando nos metamos al: localhost:8080/producto pueda ver la info que hay en esta clase
public class ProductoController {

    @Autowired
    private ProductoService productoService; //CRUD
    
    @Autowired
    private FirebaseStorageService firebaseStorageService; //GUARDAR IMAGENES

    @Autowired
    private MessageSource messageSource; //MENSAJES PERSONALIZADOS (TEXTOS PERSONALIZADOS)

    @GetMapping("/listado") // Cuando el usuario ingrese a: https:localhost/producto/listado podra ver la info
    public String inicio(Model model) { //pasa la info al html
        var productos = productoService.getProductos(false); //obtiene la lista de productos
        model.addAttribute("productos", productos); //aqui paso la info al html (productos)
        model.addAttribute("totalProductos", productos.size()); //es la cantidad de los productos
        return "/producto/listado"; //las vistas que yo voy a crear en el html de https:localhost/producto/listado
    }
    
    @PostMapping("/modificar") //https:localhost/producto/modificar
    public String modificar(Producto producto, Model model) { //modifica los productos que queramos cambiar
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        return "/producto/modifica"; //las vistas que yo voy a crear en el html de https:localhost/producto/modificar
    } 
 
    @PostMapping("/guardar") //guarda un producto nuevo o modificado
    public String guardar(Producto producto,
            @RequestParam MultipartFile imagenFile, //le pide un parametro, osea los datos del archivo de la img
            RedirectAttributes redirectAttributes) {
        if (!imagenFile.isEmpty()) { // Si no está vacío... pasaron una imagen...
            productoService.save(producto);
            String rutaImagen = firebaseStorageService
                    .cargaImagen(
                            imagenFile,
                            "producto",
                            producto.getIdProducto());
            producto.setRutaImagen(rutaImagen);
        }
        productoService.save(producto);
        redirectAttributes.addFlashAttribute("todoOk", //es un mensaje que tira en la pantalla de que todo esta bien
                messageSource.getMessage("mensaje.actualizado",
                        null,
                        Locale.getDefault()));
        return "redirect:/producto/listado";
    }
    
    @PostMapping("/eliminar") //elimina un producto
    public String eliminar(Producto producto, RedirectAttributes redirectAttributes) {
        producto = productoService.getProducto(producto);
        if (producto == null) {  // La producto no existe...
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("producto.error01",
                            null,
                            Locale.getDefault()));
        } else if (false) { // Esto se actualiza proximas semanas...
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("producto.error02",
                            null,
                            Locale.getDefault()));
        } else if (productoService.delete(producto)) {
            // Si se borró...
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("mensaje.eliminado",
                            null,
                            Locale.getDefault()));
        } else {
            redirectAttributes.addFlashAttribute("error", //es un mensaje que tira en la pantalla por si sucede algun error
                    messageSource.getMessage("producto.error03",
                            null,
                            Locale.getDefault()));
        }
        return "redirect:/producto/listado"; //lo redijire a ese enlace
    }

}