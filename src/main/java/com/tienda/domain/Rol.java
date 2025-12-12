/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//Semana 10
package com.tienda.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;

@Data //clase de datos
@Entity //siempre va a tener una entidad la Data
@Table(name ="rol") //Y se especifica el nombre de la tabla
public class Rol implements Serializable{
    
    private static final long serialVersionUID = 1L; //genera los id 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;
    private String nombre;
    @Column(name="id_usuario")
    private Integer idUsuario;
}
