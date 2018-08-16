package com.ecommerce.microcommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.microcommerce.model.Produit;

public interface ProduitDao extends JpaRepository<Produit, Integer> {

	Produit findById(int id);
	List<Produit> findByPrixGreaterThan(int prixLimit);
	List<Produit> findByNomLike(String recherche);

	@Query("SELECT id, nom, prix FROM Produit p WHERE p.prix > :prixLimit")
    List<Produit>  chercherUnProduitCher(@Param("prixLimit") int prix);


}
