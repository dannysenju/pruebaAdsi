/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import service.DocumentoEJB;


/**
 *
 * @author danny
 */
@Named(value = "documentoBean")
@ViewScoped
public class DocumentoBean implements Serializable {

    private UploadedFile uploadedPicture;
    private boolean hasImage;
    private Date lastModified;
    
    @EJB // pone a dispocision Servicio
    DocumentoEJB documentoEJB;
    
    public DocumentoBean() {
        
        
    }

    @PostConstruct
    public void init() {
        hasImage = false;
        lastModified = new Date();
    }

    public void fileUploadListener(FileUploadEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        // Get uploaded file from the FileUploadEvent
        this.uploadedPicture = e.getFile();
        byte[] content;
//        try {
////            content = IOUtils.toByteArray(uploadedPicture.getInputstream());
////            if (documentoEJB.updateImage(content, usuario.getIdusuario())) {
////                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Imagen subida con exito", null));
////                hasImage = true;
////                lastModified = Calendar.getInstance().getTime();
////            } else {
////                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar su foto, contacte al administrador", null));
////            }
//        } catch (IOException ex) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del servidor, contacte al administrador", null));
//        }
    }

    

}
