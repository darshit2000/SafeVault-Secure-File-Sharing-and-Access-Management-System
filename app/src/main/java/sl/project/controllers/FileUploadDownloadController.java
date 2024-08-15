package sl.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sl.project.models.FileUploadDownload;
import sl.project.models.Session;
import sl.project.services.FileUploadDownloadService;
import sl.project.services.SessionService;

@CrossOrigin
@RestController 
@RequestMapping("/api")
public class FileUploadDownloadController {
    
    @Autowired
    private FileUploadDownloadService fileUploadDownloadService;
    
    @Autowired
    private SessionService sessionService;
    
    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<FileUploadDownload> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("passkey") String passkey, @RequestParam("ownerid") String ownerid) {
        Session s = sessionService.findSession(ownerid);
        if(s == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    	
    	FileUploadDownload fileUploadDownload = fileUploadDownloadService.upload(file, passkey, ownerid);
        
        if(fileUploadDownload != null) {
            return new ResponseEntity<>(fileUploadDownload, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @CrossOrigin
    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String filename, @RequestParam("passkey") String passkey, @RequestParam("userid") String userid) {
    	Session s = sessionService.findSession(userid);
        if(s == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        
        Boolean isCorrectPasskey = fileUploadDownloadService.checkPasskey(filename, passkey);
        if(isCorrectPasskey == false) {
        	return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        
        FileUploadDownload fileUploadDownload = fileUploadDownloadService.getFile(filename, passkey);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileUploadDownload.getName() + "\"")
                .body(fileUploadDownload.getContent());
        
//        String[] file_ext = filename.split("\\.");
//        MediaType mediaType;
//
//        switch (file_ext[file_ext.length-1].toLowerCase()) {
//            case "pdf":
//                mediaType = MediaType.APPLICATION_PDF;
//                break;
//            case "txt":
//                mediaType = MediaType.TEXT_PLAIN;
//                break;
//            case "html":
//            	mediaType = MediaType.TEXT_HTML;
//                break;
//            case "htm":
//                mediaType = MediaType.TEXT_HTML;
//                break;
//            case "py":
//                mediaType = MediaType.TEXT_PLAIN;
//                break;
//            case "java":
//                mediaType = MediaType.TEXT_PLAIN;
//                break;
//            case "doc":
//                mediaType = new MediaType("application", "msword");
//                break;
//            case "cpp":
//            	mediaType = MediaType.TEXT_PLAIN;
//                break;
//            case "c":
//                mediaType = MediaType.TEXT_PLAIN;
//                break;
//            case "docx":
//                mediaType = new MediaType("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
//                break;
//            case "ppt":
//                mediaType = new MediaType("application", "vnd.ms-powerpoint");
//                break;
//            case "pptx":
//                mediaType = new MediaType("application", "vnd.openxmlformats-officedocument.presentationml.presentation");
//                break;
//            default:
//                mediaType = MediaType.APPLICATION_OCTET_STREAM; // Default for unknown types
//        }
//        if(fileUploadDownload != null) {
//        	return ResponseEntity.ok()
//                    .contentType(mediaType)
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
//                    .body(fileUploadDownload.getContent());
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        
        
    }
    
    @CrossOrigin
    @GetMapping("/view")
    public ResponseEntity<ArrayList<FileUploadDownload>> view() {
        
        ArrayList<FileUploadDownload> my_list = new ArrayList<>();
        my_list = fileUploadDownloadService.view();
        
        if(my_list != null) {
            return new ResponseEntity<>(my_list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @CrossOrigin
    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody String id) {
        
        fileUploadDownloadService.delete(id);
        
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
     
}
