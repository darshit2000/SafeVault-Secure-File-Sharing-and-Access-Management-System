package sl.project.services;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import sl.project.models.FileUploadDownload;

public interface IFileUploadDownloadService {
    
    public FileUploadDownload upload(MultipartFile file, String passkey, String ownerid);
        
    public void getAESFinalKey(String initialKey);
    
    public String encryptData(String str, String initialKey);
            
    public String pdfToText(MultipartFile file);
    
    public String decryptData(String str, String initialKey);
    
    public FileUploadDownload getFile(String str, String passkey);
    
    public ArrayList<FileUploadDownload> view();
    
    public void delete(String id);
    
    public Boolean checkPasskey(String filename, String passkey);
    
}