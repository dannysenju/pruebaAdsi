/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Documento;

/**
 *
 * @author danny
 */
@Stateless
public class DocumentoEJB {

    @PersistenceContext
    private EntityManager em; // pone a disposicion JPA
    
    public Documento getDocumentById(int id) {
        return (Documento) em.createNamedQuery("Usuario.findByIddocumento", Documento.class).setParameter("iddocumento", id).getSingleResult();
    }
    
}
