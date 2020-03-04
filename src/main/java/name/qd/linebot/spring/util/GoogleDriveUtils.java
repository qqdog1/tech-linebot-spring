package name.qd.linebot.spring.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

public class GoogleDriveUtils {
	private static final Logger log = LoggerFactory.getLogger(GoogleDriveUtils.class);
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
	private String appName;
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	private String credentialsFilePath;
	private Drive drive;

	public GoogleDriveUtils(String credentialsFilePath, String appName) throws Exception {
		this.credentialsFilePath = credentialsFilePath;
		this.appName = appName;

		initDrive();
	}

	private void initDrive() throws Exception {
		try {
			NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(appName).build();
		} catch (GeneralSecurityException | IOException e) {
			log.error("Init google drive failed.", e);
			throw e;
		}
	}

	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(new FileInputStream(credentialsFilePath)));

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
	
	public String getFileId(String name) {
		return getFileId(null, name);
	}
	
	public String getFileId(String folderId, String name) {
		try {
			com.google.api.services.drive.Drive.Files.List googleList = drive.files().list();
			if(folderId != null) {
				googleList = googleList.setQ("'" + folderId + "' in parents");
			}
			googleList = googleList.setQ("name='" + name + "'");
			FileList fileList = googleList.execute();
			
			if(fileList.size() > 0) {
				return fileList.getFiles().get(0).getId();
			}
		} catch (IOException e) {
			log.error("Query google drive failed. folderId:{}, name:{}", folderId, name, e);
		}
		return null;
	}
	
	public String upload(String folderId, File file) {
		String fileName = file.getName();
		log.info("Uploading {} file to google drive.", fileName);
		String fileId = getFileId(folderId, fileName);
		if(fileId != null) {
			log.info("There is a file with same name, delete it. name:{}, id:{}", fileName, fileId);
			remove(fileId);
		}
		
		com.google.api.services.drive.model.File googleFile = new com.google.api.services.drive.model.File();
		googleFile.setName(fileName);
		googleFile.setMimeType("text/plain");
		if(folderId != null) {
        	List<String> lstFolderId = new ArrayList<>();
            lstFolderId.add(folderId);
            googleFile.setParents(lstFolderId);
        }
		FileContent mediaContent = new FileContent("text/plain", file);
		
		try {
			com.google.api.services.drive.model.File fileUploaded = drive.files().create(googleFile, mediaContent).execute();
			if(fileUploaded != null) {
				return fileUploaded.getId();
			}
		} catch (IOException e) {
			log.error("Upload file to google drive failed.", e);
		}
		return null;
	}
	
	public File get(String fileId) {
		try {
			OutputStream outputStream = new ByteArrayOutputStream();
			drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);

			
		} catch (IOException e) {
			log.error("Get file from google drive failed. id:{}", fileId);
		}
		return null;
	}
	
	public boolean remove(String fileId) {
		try {
			drive.files().delete(fileId).execute();
			log.info("File removed from google drive. id:{}", fileId);
			return true;
		} catch (IOException e) {
			log.error("Remove file failed. FileId:{}", fileId, e);
		}
		return false;
	}

	public static void main(String[] s) {
		String folderId = "1chbOmupdFnC5lYdj5GDeNKowj-TYG1UJ";
		try {
			GoogleDriveUtils googleDrive = new GoogleDriveUtils("./config/credentials.json", "GOGOApi");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
