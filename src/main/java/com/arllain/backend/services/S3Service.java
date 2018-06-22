package com.arllain.backend.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	
	private Logger LOG = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public void uploadFile(String localFilePath) {
		
		try {
			LOG.info("Inciando upload");
			File file = new File(localFilePath);
			s3Client.putObject(new PutObjectRequest(bucketName, file.getName(), file));
			LOG.info("Upload finalizado");
		}catch(AmazonServiceException ex) {
			LOG.info("AmazonServiceException: " + ex.getErrorMessage());
			LOG.info("Status Code: " + ex.getErrorCode());
		}catch(AmazonClientException e) {
			LOG.info("AmazonClientException: " + e.getMessage());
		}
	}
}
