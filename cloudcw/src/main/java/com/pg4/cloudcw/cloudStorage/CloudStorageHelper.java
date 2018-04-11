package com.pg4.cloudcw.cloudStorage;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.CopyWriter;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.cloud.storage.StorageOptions;

import org.apache.commons.fileupload.FileItemStream;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
// [START example]

public class CloudStorageHelper {

	private static Storage storage = null;
	private static final String bucketName = "scenic-firefly-199015.appspot.com";
	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	// uploadFile
	@SuppressWarnings("deprecation")
	public String uploadFile(FileItemStream fileStream, String fileName) throws IOException, ServletException {
		// A Binary Large OBject --> the inputstream is closed by default, so we don't
		// need to close it here
		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, fileName)
				// Modify access list to allow all users with link to read file
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build(),
				fileStream.openStream());
		// return the public download link
		return blobInfo.getMediaLink();
	}

	public void deleteItem(String blobName) throws IOException {
		BlobId blobId = BlobId.of(bucketName, blobName);
		boolean deleted = storage.delete(blobId);
		if (deleted) {
			// the blob was deleted
		} else {
			// the blob was not found
		}
	}

	public String renameItem(String oldName, String newblobName) throws IOException {
		Blob blob = storage.get(bucketName, oldName);
		CopyWriter copyWriter = blob.copyTo(bucketName, newblobName);
		Blob copiedBlob = copyWriter.getResult();
		
		copiedBlob.updateAcl(Acl.of(User.ofAllUsers(), Role.READER));		
	//	copiedBlob.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build();
		System.out.println(getSignUrl(newblobName));
		return copiedBlob.getMediaLink();
	}

	/**
	 * Example of creating a signed URL for the blob that is valid for 2 weeks,
	 * using the default credentials for signing the URL.
	 */

	// [TARGET signUrl(long, TimeUnit, SignUrlOption...)]
	public URL getSignUrl(String blobName) {

		
		
		
		/*
		 * StorageOptions options = StorageOptions.builder()
		 * .authCredentials(AuthCredentials.createForJson(new FileInputStream()))
		 * .build();
		 */

		Blob blob = storage.get(bucketName, blobName);
		URL signedUrl = null;
		try {
		signedUrl = blob.signUrl(14, TimeUnit.DAYS);// [END signUrl] null;
		
		System.out.println(signedUrl.toString());
		}catch(Exception e) {}
		
		try {
			signedUrl = blob.signUrl(14, TimeUnit.DAYS, SignUrlOption
					.signWith(ServiceAccountCredentials.fromStream(new FileInputStream("MyProject-acb12c393c5b.json"))));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// [END signUrlWithSigner]

		return signedUrl;
	}

	/*
	 * public void dowload(String bucketName, String objectName, Path downloadTo)
	 * throws IOException { BlobId blobId = BlobId.of(bucketName, objectName); Blob
	 * blob = storage.get(blobId); if (blob == null) {
	 * System.out.println("No such object"); return; } PrintStream writeTo =
	 * System.out; if (downloadTo != null) { writeTo = new PrintStream(new
	 * FileOutputStream(downloadTo.toFile())); } if (blob.getSize() < 1_000_000) {
	 * // Blob is small read all its content in one request byte[] content =
	 * blob.getContent(); writeTo.write(content); } else { // When Blob size is big
	 * or unknown use the blob's channel reader. try (ReadChannel reader =
	 * blob.reader()) { WritableByteChannel channel = Channels.newChannel(writeTo);
	 * ByteBuffer bytes = ByteBuffer.allocate(64 * 1024); while (reader.read(bytes)
	 * > 0) { bytes.flip(); channel.write(bytes); bytes.clear(); } } } if
	 * (downloadTo == null) { writeTo.println(); } else { writeTo.close(); } }
	 */

}
