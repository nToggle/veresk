package com.rubiconproject.dt.veresk.core

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3ClientBuilder

object S3Client {
  def buildAmazonClient() = {
    val awsClientOpts = new ClientConfiguration()
    awsClientOpts.setProxyHost("riak-dev.lab1.fanops.net")
    awsClientOpts.setProxyPort(8080)
    awsClientOpts.setConnectionTimeout(2000)
    awsClientOpts.setMaxConnections(3)
    awsClientOpts.setMaxErrorRetry(3)
    awsClientOpts.setSocketTimeout(2000)
    awsClientOpts.setSignerOverride("S3SignerType")
    val credentialsProvider = new AWSStaticCredentialsProvider(
      new BasicAWSCredentials(
        "6UVBIDHA5F-PJGFVU-DT",
        "tPtVyaYk-V8QyDP0KhTPgEemFPFJoePRUGh78w=="
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
