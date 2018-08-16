package com.ecommerce.microcommerce.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microcommerce.dao.ProduitDao;
import com.ecommerce.microcommerce.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.model.Produit;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@RestController
@Api("Gestion des Produits")
public class ProduitController {
    @Autowired
    private ProduitDao produitDao;

    //Récupérer la liste des produits
    @RequestMapping(value="/Produits", method=RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Produit> produits = produitDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }

    //Récupérer un produit par son Id
    @ApiOperation("Recupere un produit avec son ID ok")
    @GetMapping(value="/Produits/{id}")
    public Produit afficherUnProduit(@PathVariable int id) throws ProduitIntrouvableException  {
        Produit produit= produitDao.findById(id);
        if(produit==null) throw new ProduitIntrouvableException ("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }	
    @GetMapping(value = "test/produits/limit/{prixLimit}")
    public List<Produit> testeDeRequetes(@PathVariable int prixLimit) {
        return produitDao.chercherUnProduitCher(prixLimit);
    }  
    @GetMapping(value = "test/produits/{recherche}")
    public List<Produit> testeDeRequetess(@PathVariable String recherche) {
        return produitDao.findByNomLike("%"+recherche+"%");
    }
        
    //ajouter un produit
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Produit produit) {
        Produit produitAdded = produitDao.save(produit);
        if (produitAdded == null) {
        	return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
        		.fromCurrentRequestUri()
        		.path("/{id}")
        		.buildAndExpand(produitAdded.getId())
        		.toUri();
        return ResponseEntity.created(location).build();    				
        
    }
    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {

        produitDao.deleteById(id);
    }
    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Produit product) {

        produitDao.save(product);
    }


    
}
