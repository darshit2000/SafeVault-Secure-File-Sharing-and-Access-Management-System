package sl.project.services;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import sl.project.models.FileUploadDownload;
import sl.project.repository.FileUploadDownloadRepository;

@Service
public class FileUploadDownloadService implements IFileUploadDownloadService {
	
	@Autowired
    private Environment env;

    @Autowired
    private FileUploadDownloadRepository fileUploadDownloadRepository;
//    
//    private String initialKey = "";
    private byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  
    private static SecretKeySpec AESSecretKey;
    private static byte[] keyBytesArray;
    
    @Override
    public void getAESFinalKey(String initialKey) {
        try {
            // Reference : https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
            MessageDigest md = null;
            keyBytesArray = initialKey.getBytes("UTF-8");
            md = MessageDigest.getInstance("SHA-1");
            keyBytesArray = md.digest(keyBytesArray);
            keyBytesArray = Arrays.copyOf(keyBytesArray, 16);
            AESSecretKey = new SecretKeySpec(keyBytesArray, "AES"); 
        } catch(Exception e) {
            e.printStackTrace();
        }   
    }

    @Override
    public String encryptData(String str, String initialKey) {
        try {
            String encryptedData;
            getAESFinalKey(initialKey);
            
            IvParameterSpec initializationVector = new IvParameterSpec(iv);                      
            Cipher cipherText = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherText.init(Cipher.ENCRYPT_MODE, AESSecretKey, initializationVector);
            encryptedData = Base64.getEncoder().encodeToString(cipherText.doFinal(str.getBytes("UTF-8")));
            return encryptedData;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String decryptData(String str, String initialKey) {
        try {
            String decryptedData;
            
            // Reference : https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
            getAESFinalKey(initialKey);
            
            IvParameterSpec initializationVector = new IvParameterSpec(iv);  
            Cipher plainText = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            plainText.init(Cipher.DECRYPT_MODE, AESSecretKey,initializationVector);
            decryptedData = new String(plainText.doFinal(Base64.getDecoder().decode(str)));
            return decryptedData;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String pdfToText(MultipartFile file) {
        String str = "";
        try {
            PDDocument pdDoc = PDDocument.load(file.getInputStream());
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            str = pdfTextStripper.getText(pdDoc);
            return str;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }    
    
    @Override
    public FileUploadDownload upload(MultipartFile file, String passkey, String ownerid) {
        String file_name = StringUtils.cleanPath(file.getOriginalFilename());
        String file_ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String encryptedPasskey = encryptData(passkey, env.getProperty("initkey.pass"));
        try {
//        	byte[] fileContent = file.getBytes();
//            String cipher = encryptData(new String(fileContent), passkey);
//            FileUploadDownload fileUploadDownload = new FileUploadDownload(file_name, ownerid, encryptedPasskey, cipher.getBytes());
//            return fileUploadDownloadRepository.save(fileUploadDownload);
        	
            if(file_ext.equals("pdf")) {
                String textFile = pdfToText(file);
                String cipher = encryptData(textFile, passkey);
                FileUploadDownload fileUploadDownload = new FileUploadDownload(file_name, ownerid, encryptedPasskey, cipher.getBytes());
                return fileUploadDownloadRepository.save(fileUploadDownload);
                
            } else if(file_ext.equals("txt") || file_ext.equals("sh")) {
                String body = new String(file.getBytes());
                String cipher = encryptData(body, passkey);
                FileUploadDownload fileUploadDownload = new FileUploadDownload(file_name, ownerid, encryptedPasskey, cipher.getBytes());
                return fileUploadDownloadRepository.save(fileUploadDownload);
            } else {
                return null;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FileUploadDownload getFile(String filename, String passkey) {
//        String cipherFileName = encryptData(filename);
        
        FileUploadDownload fileUploadDownload = fileUploadDownloadRepository.findByFilename(filename);
        String cipher = new String(fileUploadDownload.getContent());
        String plainText = decryptData(cipher, passkey);
        fileUploadDownload.setContent(plainText.getBytes());    
        fileUploadDownload.setName(filename);
        
        return fileUploadDownload;
    }

    @Override
    public ArrayList<FileUploadDownload> view() {
        List<FileUploadDownload> my_list = new ArrayList<>();
        my_list = fileUploadDownloadRepository.findAll();
        return (ArrayList<FileUploadDownload>) my_list;
    }

    @Override
    public void delete(String id) {
        fileUploadDownloadRepository.deleteById(id);
    }

	@Override
	public Boolean checkPasskey(String filename, String passkey) {
		FileUploadDownload fd = fileUploadDownloadRepository.findByFilename(filename);
		String decryptedPasskey = decryptData(fd.getPasskey(), env.getProperty("initkey.pass"));
		if(passkey.equals(decryptedPasskey)) {
			return true;
		} else {
			return false;
		}
	}

}
