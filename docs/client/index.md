# Client

The plugin allows you to configure the Secrets Manager client that it uses to access secrets.

The most common use case is to access secrets in another account using [IAM cross-account roles](https://docs.aws.amazon.com/IAM/latest/UserGuide/tutorial_cross-account-with-roles.html).

**We recommend that you use the defaults whenever possible.** This will allow Jenkins to inherit AWS configuration from the environment. Only set these client options if you really need to (for example you have multiple Jenkins AWS plugins installed, and need the Secrets Manager plugin to behave differently to the others).

## Credentials Provider

The plugin supports the following `AWSCredentialsProvider` implementations to authenticate and authorize with Secrets Manager.

*Note: This is not the same thing as a Jenkins `CredentialsProvider`.*

### Default

This uses the standard AWS credentials lookup chain.

The authentication methods in the chain are:

- EC2 Instance Profiles.
- EC2 Container Service credentials.
- Environment variables (set `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` before starting Jenkins).
- Java properties (set `aws.accessKeyId` and `aws.secretKey` before starting Jenkins).
- User profile (configure `~/.aws/credentials` before starting Jenkins).
- Web Identity Token credentials.

### Profile

This allows you to use named AWS profiles from `~/.aws/config`.

```yaml
unclassified:
  awsCredentialsProvider:
    client:
      credentialsProvider:
        profile:
          profileName: "foobar"
```

### STS AssumeRole

This allows you to specify IAM roles inline within Jenkins.

```yaml
unclassified:
  awsCredentialsProvider:
    client:
      credentialsProvider:
        assumeRole:
          roleArn: "arn:aws:iam::111111111111:role/foo"
          roleSessionName: "jenkins"
```

## Endpoint Configuration

You can set the AWS endpoint configuration for the client.

```yaml
unclassified:
  awsCredentialsProvider:
    client:
      endpointConfiguration:
        serviceEndpoint: "http://localhost:4584"
        signingRegion: "us-east-1"
```

## Region

You can set the AWS region for the client.

```yaml
unclassified:
  awsCredentialsProvider:
    client:
      region: "us-east-1"
```
