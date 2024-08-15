'use strict';

$(document).ready(function () {
    $("#login-form").submit(function(event) {
        let username = document.getElementById("inputUsername").value;
        let password = document.getElementById("inputPassword").value;
		var dataFile = new FormData()
        dataFile.append('username', username)
		dataFile.append('password', password);
		
        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: 'http://localhost:8080/portfolio/login',
            data: dataFile,
            contentType: false,
            cache: false,
            processData:false,
            success: function(response){
                console.log(response);
				window.localStorage.setItem("userid", response.id);
				window.localStorage.setItem("username", response.username);
				window.location.href = "./adminPage.html";
//				if(response.email == "admin@gmail.com") {
//					window.location.href = "./adminPage.html";
//				} else {
//					window.location.href = "./userPage.html";
//				}
            },
            error: function (error) {
				if(error.status == 401) {
					alert("Incorrect username or password.");
					window.location.href = "./login.html";
				}
                console.log(error);
            }
        });
        event.preventDefault();
    });

    $("#signup-form").submit(function(event) {
        let name = document.getElementById("inputName").value;
        let contact = document.getElementById("inputContact").value;
        let email = document.getElementById("inputEmailid").value;
		let username = document.getElementById("inputUsername").value;
        let password = document.getElementById("inputPassword").value;
        let confirmpassword = document.getElementById("inputConfirmPassword").value;

        let atposition=email.indexOf("@");  
        let dotposition=email.lastIndexOf(".");  

        if (atposition < 1 || dotposition < atposition+2 || dotposition + 2 >= email.length) {  
            $("#error").html("<p>Please enter a valid email address.</p>");
        } else if(password != confirmpassword) {
            $("#error").html("<p>Passwords did not match</p>");
        } else if(contact.length != 10) {
            $("#error").html("<p>Contact must be 10 digits long.</p>");
        } else {
            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                url: 'http://localhost:8080/portfolio/register',
                // data: new FormData(this),
                data: '{"name":"'+name+'", "email":"'+email+'", "username":"'+username+'", "password":"'+password+'", "contact":"'+contact+'"}',
                contentType: "application/json; charset=utf8",
                cache: false,
                processData:false,
                success: function(response){
                    if (response) {
                        console.log(response);
                        window.location.href = "./login.html";
                    } else {
                        $("#error").html("<p>Signup Failed. Please try again.</p>");
                        console.log(response);
                        document.getElementById("inputName").value = '';
                        document.getElementById("inputContact").value = '';
                        document.getElementById("inputUsername").value = '';
                        document.getElementById("inputPassword").value = ''; 
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }
        event.preventDefault();
    });

    $("#file-upload").submit(function(event) {
		let uid = window.localStorage.getItem("userid");
//		if(uid == null) {
//			alert("Session Expired. Please log in again.");
//			window.location.href = "./login.html";
//	        event.preventDefault();
//	        return; 
//		} 
        var input = document.querySelector('input[type="file"]')
		var passkey = document.getElementById("passkey").value;
    
        var dataFile = new FormData()
        dataFile.append('file', input.files[0])
		dataFile.append('passkey', passkey);
		dataFile.append('ownerid', uid);
        // alert("file : " +dataFile);
        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: 'http://localhost:8080/api/upload',
            // data: new FormData(this),
            data: dataFile,
            contentType: false,
            cache: false,
            processData:false,
            success: function(response){
                console.log("Upload Success");
				document.getElementById("file").value = '';
				document.getElementById("passkey").value = '';
                $("#upload-status").html("<p>File Uploaded Successfully.</p>");
            },
            error: function (error) {
				if(error.status == 401) {
					alert("User not authenticated. Please log in again.");
					window.location.href = "./login.html";
			        event.preventDefault();
			        return; 
				}
				document.getElementById("file").value = '';
				document.getElementById("passkey").value = '';
                console.log(error);
            }
        });
        event.preventDefault();
		
    });
	
//	new download func:
//	$("#downloadfile").submit(function(event) {
//	    let uid = window.localStorage.getItem("userid");
//	    if (uid == null) {
//	        window.location.href = "./login.html";
//	        event.preventDefault();
//	        return; 
//	    } 
//
//	    let filename = document.getElementById("str").value;
//	    let passkey = document.getElementById("downloadpasskey").value;
//	    let dataFile = new FormData();
//	    dataFile.append('filename', filename);
//	    dataFile.append('passkey', passkey);
//	    dataFile.append('userid', uid);
//
//	    $.ajax({
//	        type: 'POST',
//	        enctype: 'multipart/form-data',
//	        url: 'http://localhost:8080/api/download',
//	        data: dataFile,
//	        contentType: false,
//	        cache: false,
//	        processData: false,
//	        success: function(response, status, xhr) {
//	            console.log("Download Success");
//				var pdf = new jsPDF();
//				  console.log('Server response:', response); // Add this line to inspect the response
//				  pdf.addPage();
//				  console.log('Adding page...'); // Add this line to see if the method is being called
//				  pdf.fromHTML(response);
//				  console.log('Saving document...'); // Add this line to see if the method is being called
//				  pdf.save('document.pdf');
//
//	            // Get the filename from the Content-Disposition header
////				let disposition = xhr.getResponseHeader('Content-Disposition');
////	            let filename = '';
////	            if (disposition && disposition.indexOf('attachment') !== -1) {
////	                let matches = /filename="(.+)"/.exec(disposition);
////	                if (matches != null && matches[1]) {
////	                    filename = matches[1];
////	                }
////	            }
////
////	            // Create a Blob from the response
////	            let blob = new Blob([response], { type: xhr.getResponseHeader('Content-Type') || 'application/octet-stream' });
////	            let url = URL.createObjectURL(blob);
////
////	            // Create an anchor element and trigger the download
////	            let a = document.createElement('a');
////	            a.href = url;
////	            a.download = filename || "download"; // Default to "download" if filename is not provided
////	            document.body.appendChild(a);
////	            a.click();
////	            document.body.removeChild(a);
////	            URL.revokeObjectURL(url);
////
////	            $("#div-status").html("<p>File Downloaded Successfully.</p>");
////	            document.getElementById("str").value = '';
////	            document.getElementById("downloadpasskey").value = '';
//	        },
//	        error: function (error) {
//	            if (error.status == 401) {
//	                alert("User not authenticated. Please log in again.");
//	                window.location.href = "./login.html";
//	                event.preventDefault();
//	                return; 
//	            }
//	            if (error.status == 403) {
//	                document.getElementById("downloadpasskey").value = '';
//	                alert("Wrong passkey. Please try again.");
//	                event.preventDefault();
//	                return; 
//	            }
//	            $("#div-status").html("<p>File Not Found.</p>");
//	            console.log(error);
//	        }
//	    });
//	    event.preventDefault();
//	});


	
    $("#downloadfile").submit(function(event) {
		let uid = window.localStorage.getItem("userid");
		if(uid == null) {
			window.location.href = "./login.html";
	        event.preventDefault();
	        return; 
		} 
		
        let filename = document.getElementById("str").value;
		var passkey = document.getElementById("downloadpasskey").value;
		var dataFile = new FormData()
        dataFile.append('filename', filename)
		dataFile.append('passkey', passkey);
		dataFile.append('userid', uid);
        let output_text = '';
		
        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: 'http://localhost:8080/api/download',
            data: dataFile,
            contentType: false,
            cache: false,
            processData:false,
            success: function(response){
                console.log("Download Success");
                let i = 0;
                output_text = '';
                for (i = 0; i < response.length; ++i) {
                    if (response.charAt(i) == '\n') {
                        output_text += '<br/>';
                    }
                    else {
                        output_text += response.charAt(i);
                    }
                };
                
                // $("#uploadresult").html("<li><p>"+output_text+"</p><span></span></li>");

                let newContent = response.replaceAll("<br />","\n");
                newContent = newContent.replaceAll("\n√\n"," √");
                var element = document.createElement('a');
                element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(newContent));
                element.setAttribute('download', "download.txt");
                element.style.display = 'none';
                document.body.appendChild(element);
                element.click();
                document.body.removeChild(element);
                $("#div-status").html("<p>File Downloaded Successfully.</p>");
                document.getElementById("str").value = '';
				document.getElementById("downloadpasskey").value = '';
            },
            error: function (error) {
				if(error.status == 401) {
					alert("User not authenticated. Please log in again.");
					window.location.href = "./login.html";
			        event.preventDefault();
			        return; 
				}
				if(error.status == 403) {
					document.getElementById("downloadpasskey").value = '';
					alert("Wrong passkey. Please try again.");
			        event.preventDefault();
			        return; 
				}
                let output_text = "File Not Found.";
                $("#div-status").html("<p>"+output_text+"</p>");
                console.log(error);
            }
        });
        event.preventDefault();
    });
	
	$("#view").submit(function(event) {
		console.log("USER ID : " + window.localStorage.getItem("userid"));
		let uid = window.localStorage.getItem("userid");
		if(uid == null) {
			window.location.href = "./login.html";
	        event.preventDefault();
	        return; 
		} 
        let table = document.getElementById('table');
        document.getElementById('tp').value = '';
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/api/view',
            contentType: false,
            cache: false,
            processData:false,
            data:'',
            success: function(response){
                console.log("success");
                // let my_content = '';
                // let i = 0;
                // for (i = 0; i < response.length; ++i) {
                //     my_content += 'ID : ' + response[i].id;
                //     my_content += '<br>File-Name : ' + response[i].name;
                //     my_content += '<br>File-Content : ' + response[i].content + '<br><br>';
                // }
                // $("#view").html("<p>"+my_content+"</p>");
                
                $("#table tr").remove(); 
                let tr1 = document.createElement('tr');

                let th1 = document.createElement('th');
                th1.textContent = "ID";
                tr1.appendChild(th1);

                let th2 = document.createElement('th');
                th2.textContent = "FILE NAME";
                tr1.appendChild(th2);

                let th3 = document.createElement('th');
                th3.textContent = "FILE CONTENT";
                tr1.appendChild(th3);

                table.appendChild(tr1);
                let i = 0;
                for (let res of response) {
                    i++;
                    let tr = document.createElement('tr');
                    
                    let td1 = document.createElement('td');
                    td1.textContent = res.id;
                    tr.appendChild(td1);
                    
                    let td2 = document.createElement('td');
                    td2.textContent = res.name;
                    tr.appendChild(td2);
                    
                    let td3 = document.createElement('td');
                    td3.textContent = res.content;
                    tr.appendChild(td3);

                    let td4 = document.createElement('td');
                    var button = document.createElement('button');   
                    button.id = "del";
                    button.value = res.id;  
                    // button.onclick = del();    
                    var text = document.createTextNode("Delete");
                    button.appendChild(text);
                    button.addEventListener("click", () => dele(res.id));
                    td4.appendChild(button); 
                    tr.appendChild(td4);
                    
                    table.appendChild(tr);
                }
            },
            error: function (error) {
                console.log(error);
            }
        });
        event.preventDefault();
    });
});

function dele(id) {
    // alert(id);
    $.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: 'http://localhost:8080/api/delete',
        // data: new FormData(this),
        data: String(id),
        contentType: false,
        cache: false,
        processData:false,
        success: function(response){
            console.log("success");
            alert("File is Deleted successfully.");
            self.location['reload']();
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function onloadFunc() {
	console.log("On page reload");
	// check if you got the access that you requested and if someone wants access to your file
	
	let uid = window.localStorage.getItem("userid");
	var dataFile = new FormData()
    dataFile.append('uid', uid);
	$.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: 'http://localhost:8080/access/check',
        // data: new FormData(this),
        data: dataFile,
        contentType: false,
        cache: false,
        processData:false,
        success: function(response){
			populateTable(response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function populateTable(data) {
    let tableBody = $('#responseTable tbody');
	let table = $('#responseTable');
    tableBody.empty(); // Clear existing rows
	
	if (data.length === 0) {
	    table.hide(); // Hide the table if no rows
	    return;
	}
	
	table.show();

    data.forEach(item => {
        let row;
        if (item.granted === "PENDING") {
            row = `
                <tr>
                    <td>${item.filename}</td>
                    <td>${item.requesteduserid}</td>
                    <td></td>
                    <td>
                        <button onclick="approve('${item.filename}', '${item.requesteduserid}')">Approve</button>
                        <button onclick="decline('${item.filename}', '${item.requesteduserid}')">Decline</button>
                    </td>
                </tr>
            `;
        } else if (item.granted === "YES" || item.granted === "NO") {
            row = `
                <tr>
                    <td>${item.filename}</td>
                    <td></td>
                    <td>${item.passkey}</td>
                    <td></td>
                </tr>
            `;
        }
        tableBody.append(row);
    });
}

function approve(filename, userid) {
    console.log(`Approve clicked for file ${filename} and user ${userid}`);
	var dataFile = new FormData()
    dataFile.append('filename', filename);
	dataFile.append('requesteduserid', userid);
	dataFile.append('grant', "YES");
	$.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: 'http://localhost:8080/access/editaccess',
        data: dataFile,
        contentType: false,
        cache: false,
        processData:false,
        success: function(response){
			console.log("File approve : " + response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function decline(filename, userid) {
    console.log(`Decline clicked for file ${filename} and user ${userid}`);
	var dataFile = new FormData()
    dataFile.append('filename', filename);
	dataFile.append('requesteduserid', userid);
	dataFile.append('grant', "NO");
	$.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: 'http://localhost:8080/access/editaccess',
        data: dataFile,
        contentType: false,
        cache: false,
        processData:false,
        success: function(response){
			console.log("File decline : " + response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function viewFiles() {
    console.log("USER ID : " + window.localStorage.getItem("userid"));
    let uid = window.localStorage.getItem("userid");
    if(uid == null) {
        window.location.href = "./login.html";
        event.preventDefault();
        return; 
    }
    
	let container = document.getElementById('fileContainer');
    container.innerHTML = '';
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/view',
        contentType: false,
        cache: false,
        processData:false,
        data:'',
        success: function(response){
            console.log("success");
            for (let res of response) {
				let fileBlock = document.createElement('div');
                fileBlock.classList.add('file-block');

                let fileIcon = document.createElement('div');
                fileIcon.classList.add('file-icon');
                fileBlock.appendChild(fileIcon);

                let fileNameContainer = document.createElement('div');
                fileNameContainer.classList.add('file-name-container');

                let fileName = document.createElement('p');
                fileName.textContent = res.name;
                fileNameContainer.appendChild(fileName);

                let dropdown = document.createElement('div');
                dropdown.classList.add('dropdown');

                let threeDots = document.createElement('button');
                threeDots.classList.add('three-dots');
                threeDots.innerHTML = '⋯';
                dropdown.appendChild(threeDots);

                let dropdownMenu = document.createElement('div');
                dropdownMenu.classList.add('dropdown-menu');
                dropdownMenu.style.display = 'none';

                let deleteButton = document.createElement('button');
                deleteButton.textContent = 'Delete';
                deleteButton.addEventListener("click", () => {
					dele(res.id);
					dropdownMenu.style.display = 'none';
				});
                dropdownMenu.appendChild(deleteButton);

                let askForAccessButton = document.createElement('button');
                askForAccessButton.textContent = 'Ask for Access';
                askForAccessButton.addEventListener("click", () => {
					askForAccess(res.name);
					dropdownMenu.style.display = 'none';
				});
                dropdownMenu.appendChild(askForAccessButton);

                dropdown.appendChild(dropdownMenu);

                threeDots.addEventListener("click", () => {
                    if (dropdownMenu.style.display === 'none') {
                        dropdownMenu.style.display = 'block';
                    } else {
                        dropdownMenu.style.display = 'none';
                    }
                });

				fileNameContainer.appendChild(dropdown);
                fileBlock.appendChild(fileNameContainer);

                container.appendChild(fileBlock);
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}


function askForAccess(filename) {
    console.log(`Ask for Access clicked for file ${filename}`);
    let uid = window.localStorage.getItem("userid");
	var dataFile = new FormData()
	dataFile.append('requesteduserid', uid);
    dataFile.append('filename', filename);
	
	$.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: 'http://localhost:8080/access/ask',
        // data: new FormData(this),
        data: dataFile,
        contentType: false,
        cache: false,
        processData:false,
        success: function(response){
			console.log("After asking access : " + response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function removeItemPromise() {
    return new Promise((resolve) => {
        window.localStorage.removeItem("userid");
		window.localStorage.removeItem("username");
        resolve();
    });
}

function logoutBtn() {
	let uid = window.localStorage.getItem("userid");
	$.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: 'http://localhost:8080/portfolio/logout',
        // data: new FormData(this),
        data: String(uid),
        contentType: false,
        cache: false,
        processData:false,
        success: function(response){
			removeItemPromise().then(function() {
			    window.location.href = "./home.html";
			});
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function checksession() {
	console.log("USER ID : " + window.localStorage.getItem("userid"));
}


