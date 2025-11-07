/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //Le decimos a java como se tiene q comportar: Como servicio
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository; // crea una instancia de la clase ProductoRepository y la conecta aqui (CRUD= read, create, update, delete)

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) { //Read = Ingresar a la bd pueda 
        var lista = productoRepository.findAll(); //el findall me trae la lista de la BD
        //producto 1 = monitor hp
        //producto 2 = monitor dell
        //producto 3 = monitor lenovo
        if (activo) {
            lista.removeIf(e -> !e.getActivo());
        }
        return lista;
    }

    @Transactional //este metodo funciona para guardar y actualizar
    public void save(Producto producto) { 
        productoRepository.save(producto); //aqui guarda la entidad (que seria producto)
    }
    
    @Transactional 
    public boolean delete(Producto producto){ //eliminar un producto
        try{
            productoRepository.delete(producto); //el delete es que va a borrar la categoria (producto)
            productoRepository.flush();  
            return true;
        } catch (Exception e){ 
            return false;
        }
    }
    
    @Transactional(readOnly = true) //solo buscara 
    public Producto getProducto(Producto producto){ //busca y trae los productos por un ID en especifico  
        return productoRepository.findById(producto.getIdProducto()).orElse(null);
    }
    
    //Lista de productos con precio inf y sup para la consulta ampliada
    @Transactional(readOnly = true)

    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup) {

        return productoRepository.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);

    }
    
    //Lista de producto con precio inf y sup para la consulta jpql
    @Transactional(readOnly=true)    
    public List<Producto> metodoJPQL(double precioInf, double precioSup) {
        return productoRepository.metodoJPQL(precioInf, precioSup);
    }
    
    
 
}