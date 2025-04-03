#!/bin/bash

set -eu

expected_account=058264551042
caller_account="$(aws sts get-caller-identity | jq --raw-output .Account)"

if [ "$expected_account" != "$caller_account" ]; then
    echo "You are not authenticated to the correct AWS account"
    echo "You are using credentials for $caller_account"
    echo "Ensure you are using credentials for: 058264551042"
    exit 1
fi

read -p "Enter a sub for testing: " sub
read -p "Do you want to include a redirect_uri (y/n): " includeRedirectUri

environment=build
proxy_url="https://proxy.review-b-async.${environment}.account.gov.uk"

aws_credentials=$( aws configure export-credentials )
access_key_id=$( echo "$aws_credentials" | jq -r .AccessKeyId )
secret_access_key=$( echo "$aws_credentials" | jq -r .SecretAccessKey )
session_token=$( echo "$aws_credentials" | jq -r .SessionToken )

secret_string=$( ( aws secretsmanager get-secret-value --secret-id ${environment}/clientRegistryMobileAppTests ) | jq -r .SecretString )
access_token=$( echo "$secret_string" | jq -r .access_token )
client_id=$( echo "$secret_string" | jq -r .client_id )
redirect_uri=$( echo "$secret_string" | jq -r .redirect_uri )

echo
echo "Making request to the /async/token endpoint to get an access token..."

access_token=$(curl --fail-with-body --silent --data "grant_type=client_credentials" "${proxy_url}/async/token" \
  --user "${access_key_id}":"${secret_access_key}" \
  --header "x-amz-security-token: ${session_token}" \
  --header "x-custom-auth: Basic ${access_token}" \
  --aws-sigv4 "aws:amz:eu-west-2:execute-api" | jq -r .access_token)

echo "Making request to the /async/credential endpoint to create a session..."

# Data for the request
data='{
  "state": "mockState",
  "sub": "'"$sub"'",
  "client_id": "'"$client_id"'",
  "govuk_signin_journey_id": "mockGovukSigninJourneyId"
}'

# Add redirect_uri to data if $includeRedirectUri is "y"
if [ "$includeRedirectUri" == "y" ]; then
  data=$(echo "$data" | jq --arg redirect_uri "$redirect_uri" '. + { "redirect_uri": $redirect_uri }')
fi

# Execute curl command with constructed options and data
curl --fail-with-body --output /dev/null --silent --request POST "${proxy_url}/async/credential" \
  -d "$data" \
  --user "${access_key_id}":"${secret_access_key}" \
  --header "x-amz-security-token: ${session_token}" \
  --header "x-custom-auth: Bearer ${access_token}" \
  --aws-sigv4 "aws:amz:eu-west-2:execute-api"

echo
echo "Successfully created a session for the sub: $sub"

