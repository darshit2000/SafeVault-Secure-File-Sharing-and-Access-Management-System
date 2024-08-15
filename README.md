# SafeVault-Secure File Sharing and Access Management System

Our project comprises a basic software implementation of the encryption of files before uploading them on a secure storage server to prevent a breach of data and further decryption of the files before downloading them on demand by a desired user.

----------------------------

# List of Modules:
  * User Registration: This module is responsible for the user registration process
  * User Login: This will be the entry point of the application. Session Management will be handled here.
  * File Upload: The file will be uploaded in encrypted form. The encryption key will be generated through a pass-phrase that the user will enter at the time of file upload.
  * File Download: The file will be decrypted and downloaded only by authenticated users. If the user wants to access some files, then he needs to ask for access from the owner.
  * File Deletion: The file can be deleted from the storage by the owner if desired.

------------------------------

# Compilation and Running the project:

* The frontend code does not need any kind of compilation; one can run the "home.html" file directly on the local browser.
* When using Eclipse IDE for the backend code, one can build the Maven project by right-clicking on the project base directory and then "Run As">"Maven Build," and as soon as the window pops up, type "clean install" in "Goals" text box and then run.
* Once the Maven Build is successful, one can run the application by right-clicking on the base directory and then "Run As">"Java Application".

