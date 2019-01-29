/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import model.Documento;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import service.DocumentoEJB;

/**
 *
 * @author danny
 */
@Named(value = "documentoBean")
@ViewScoped
public class DocumentoBean implements Serializable {

    private UploadedFile uploadedFile;
    private UploadedFile loader;
    private boolean hasImage;
    private Date lastModified;
    private Documento documento;
    
    private List<Documento> documentos;
    private List<Documento> documentosFilter;
    private StreamedContent fileDownload;

    @EJB // pone a dispocision Servicio
    DocumentoEJB documentoEJB;

    public DocumentoBean() {

        
    }

    @PostConstruct
    public void init() {
        hasImage = false;
        lastModified = new Date();
        documento = new Documento();
        documentos = new ArrayList<>();
        documentos = documentoEJB.listAll();
        documentosFilter = new ArrayList<>();
    }

    public void upload() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (uploadedFile != null) {
            FacesMessage message = new FacesMessage("Carga del archivo ", uploadedFile.getFileName() + " correcta.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            byte[] content;
            try {
                content = IOUtils.toByteArray(uploadedFile.getInputstream());

                String str = uploadedFile.getFileName();
                String ext = str.substring(str.lastIndexOf('.'), str.length());

                if (ext.equals(".pdf") || ext.equals(".jpg")) {
                    documento.setTipoArchivo(ext);
                    documento.setPeso((int) (long) uploadedFile.getSize());
                    documento.setEstado(1);
                    documento.setFechaCreacion(new Date());
                    documento.setFechaActualizacion(new Date());

                    int id = documentoEJB.save(documento);
                    if (id > 0) {
                        documentoEJB.updateImage(content, id);
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo  guardado con exito", null));
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar su archivo, contacte al administrador", null));
                    }
                }else{
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El archivo cargado no es valido", null));
                }

            } catch (IOException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del servidor, contacte al administrador", null));
            }
        }
    }

    public void fileUploadListener(FileUploadEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        // Get uploaded file from the FileUploadEvent

        this.loader = e.getFile();

    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public StreamedContent getFileDownload() {
        return fileDownload;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public List<Documento> getDocumentosFilter() {
        return documentosFilter;
    }

    public void setDocumentosFilter(List<Documento> documentosFilter) {
        this.documentosFilter = documentosFilter;
    }

    
    
}
