/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        return (Documento) em.createNamedQuery("Documento.findByIddocumento", Documento.class).setParameter("iddocumento", id).getSingleResult();
    }
    
    public int save(Documento doc){
        em.persist(doc);
        em.flush();
        return doc.getIddocumento();
    }
    
    public boolean updateImage(byte[] archivo, int id) {
        Query q = em.createQuery("Update Documento d SET d.archivo = :archivo WHERE d.iddocumento = :id")
                .setParameter("archivo", archivo)
                .setParameter("id", id);
        return q.executeUpdate() > 0;
    }
    
    public List<Documento> listAll(){
        return em.createNamedQuery("Documento.findAll").getResultList();
    }
    
}
