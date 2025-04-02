 # Run integrated test

You need a sub associated with a session to run an integrated journey in the Credential Issuer (CRI) Orchestrator.

You can create one using the `create-session` script, in the scripts directory.

## Creating a sub

First log-in to the VPN.

### First time setup

Follow the steps below to configure an AWS profile for `id-check`:

```bash
aws configure sso
```

Enter the following details into the prompt:

```bash
SSO session name (Recommended): gds
SSO start URL [None]: https://uk-digital-identity.awsapps.com/start#/
SSO region [None]: eu-west-2
SSO registration scopes [sso:account:access]:
Attempting to automatically open the SSO authorization page in your default browser.
If the browser does not open or you wish to use a different device to authorize this request, open the following URL:

There are 2 AWS accounts available to you.
> di-mobile-id-check-async-build
Using the account ID 058264551042
Using the role name "platform-mob-test-iam"
CLI default client Region [None]: eu-west-2
CLI default output format [None]: json
CLI profile name [platform-mob-test-iam-058264551042]: id-check
```

### All attempts

1. Sign into AWS:

```bash
aws sso login --profile id-check
```

This will take you to the AWS console.
Follow on screen instructions to approve.

2. Export the profile name as a local environment variable.

```bash
export AWS_PROFILE=id-check
```

3. Run the helper script to create a session:

```bash
sh create-session.sh
```
