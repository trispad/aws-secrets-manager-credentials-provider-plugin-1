# Fields

Specify how fields from the underlying Secrets Manager secret are transformed to the fields on the Jenkins credential.

## ID

Choose how to present the credential ID.

### Default

The credential ID is presented without any transformation.

### String#replaceFirst

The credential ID is transformed using [`String#replaceFirst`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#replaceFirst-java.lang.String-java.lang.String-).

Arguments:

- `regex` - the regular expression to match in the string
- `replacement` - the substitute for the first match (leave blank to delete the matched text)

:warning: **This feature can break the credentials provider.** If a transformation causes multiple credentials to end up with the same ID, an error occurs. **Test your configuration before applying it, and after modifying secrets in Secrets Manager.** 

**Example:** Remove the prefix 'foo-' from any credentials that have this prefix.

```yaml
unclassified:
  awsCredentialsProvider:
    fields:
      id:
        replaceFirst:
          regex: "foo-"
          replacement: ""
``` 

## Description

Choose whether to show or hide the credential description.

### Show (default)

The description is shown in the credential.

### Hide

An empty string will be shown for the credential description.

```yaml
unclassified:
  awsCredentialsProvider:
    fields:
      description: false
```