package com.rubiconproject.dt.veresk.core

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3ClientBuilder

object S3Client {
  def buildAmazonClient(awsAccessKey: String,
                        awsSecretKey: String,
                        awsProxyHost: String,
                        awsProxyPort: Int) = {
    val awsClientOpts = new ClientConfiguration()
    awsClientOpts.setProxyHost(awsProxyHost)
    awsClientOpts.setProxyPort(awsProxyPort)
    awsClientOpts.setConnectionTimeout(2000)
    awsClientOpts.setMaxConnections(3)
    awsClientOpts.setMaxErrorRetry(3)
    awsClientOpts.setSocketTimeout(2000)
    awsClientOpts.setSignerOverride("S3SignerType")
    val credentialsProvider = new AWSStaticCredentialsProvider(
      new BasicAWSCredentials(
        awsAccessKey,
        awsSecretKey
      )
    )
    val configuration = new EndpointConfiguration(
      "http://s3.amazonaws.com",
      "us-east-1"
    )
    val clientBuilder = AmazonS3ClientBuilder
      .standard()
      .withEndpointConfiguration(configuration)
      .withPathStyleAccessEnabled(true)
      .withAccelerateModeEnabled(false)
      .withClientConfiguration(awsClientOpts)
      .withCredentials(credentialsProvider)

    clientBuilder.build()
  }
}
