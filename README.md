# How to deploy and execute Hear&Know C-Cada Logger

:information_source: To keep your environment clean we will complete installation in a container

Install [Docker](<https://docs.docker.com/install/>)


> On host

Pull an Ubuntu, WebSphere Liberty Profile server image with ibmcloud and other tools...

```
sudo docker pull baudelaine/wlp
```

Get application code from github

```
sudo git clone https://github.com/baudelaine/hak
```

Change to application code directory

```
cd hak
```

Create hak container from baudelaine/wlp image and share application code with container

```
sudo docker run -p 80:9080 -tdi --name hak -v $PWD:/app baudelaine/wlp
```

Start container

	sudo docker start hak

Attach container

	sudo docker attach hak




> Inside container

Login to IBM Cloud

```
icl
```

:bulb: **icl** is an alias from ~/.bash_aliases. To guess which command is hidden behind this alias use: **command -v icl**

Create Cloudant service instance

```
ibmcloud resource service-instance-create db cloudantnosqldb lite eu-de -p '{"legacyCredentials": true}'
```

Create Cloudant service key

```
ibmcloud resource service-key-create dbKey Manager --instance-name db
```

Change to application code directory

```
cd /app
```

Set Cloudant credential and HAK UDP server parameters in **VCAP_SERVICES** environment variable

:warning: Be sure to run this command inside **/app** directory

```
. ./getResources.sh
```

Check **VCAP_SERVICES** environment variable is set

```
echo $VCAP_SERVICES | jq .
```

Start WebSphere Liberty Profile server

```
stwlp
```

:bulb: **stwlp** is an alias from ~/.bash_aliases. To guess which command is hidden behind this alias use: **command -v stwlp**


> On host

:checkered_flag: Browse  [app](http://localhost/app)



## Assistant

### Service endpoints by location:

Dallas: https://api.us-south.assistant.watson.cloud.ibm.com
Washington, DC: https://api.us-east.assistant.watson.cloud.ibm.com
Frankfurt: https://api.eu-de.assistant.watson.cloud.ibm.com
Sydney: https://api.au-syd.assistant.watson.cloud.ibm.com
Tokyo: https://api.jp-tok.assistant.watson.cloud.ibm.com
London: https://api.eu-gb.assistant.watson.cloud.ibm.com
Seoul: https://api.kr-seo.assistant.watson.cloud.ibm.com


### Set URL aka ENDPOINT, API_KEY and VERSION

	URL="https://api.eu-de.assistant.watson.cloud.ibm.com"
	API_KEY="DVeTU-kw-nuSPkFH_p0J790aqgeyExqLFEqeuGA81TVB"
	VERSION="2019-02-28"

## Assistant V1 [api](https://cloud.ibm.com/apidocs/assistant/assistant-v1)

### Set the SKILL_ID aka WORKSPACE_ID

	SKILL_ID="55cc67cf-1bc4-4f3e-a70b-9bc3cd091447"

### Send first message

	MESSAGE="Je veux un rendez-vous."
	FILE="message"
	cat > $FILE << EOF
	{
		"input":{
			"text": "$MESSAGE"
		}
	}
	EOF
	jq . $FILE
	OUTPUT=$(curl -k -u 'apikey:'$API_KEY -X POST -H 'Content-Type:application/json' -d @$FILE $URL/v1/workspaces/$SKILL_ID/message?version=$VERSION)
	CONTEXT=$(echo $OUTPUT | jq -c .context)
	echo $OUTPUT | jq .

### Send a second message

	MESSAGE="jeudi."
	FILE="message"
	cat > $FILE << EOF
	{
		"input":{
			"text": "$MESSAGE"
		},
		"context": $CONTEXT
	}
	EOF
	jq . $FILE
	curl -k -u 'apikey:'$API_KEY -X POST -H 'Content-Type:application/json' -d @$FILE $URL/v1/workspaces/$SKILL_ID/message?version=$VERSION | jq .

## Assistant V2 [api](https://cloud.ibm.com/apidocs/assistant/assistant-v2)

### Set the ASSISTANT_ID

	ASSISTANT_ID="cb9acef7-08c9-4069-85ea-dd52f5ed1bcd"

### Create a session

	SESSION_ID=$(curl -k -u 'apikey:'$API_KEY -X POST $URL/v2/assistants/$ASSISTANT_ID/sessions?version=$VERSION | jq -r .session_id) && echo $SESSION_ID

### Send first message

	MESSAGE="Je veux un rendez-vous."
	FILE="message"
	cat > $FILE << EOF
	{
		"input":{
			"text": "$MESSAGE",
	        "options":{
	            "debug": true,
	            "return_context": true
	        }
		}
	}
	EOF
	jq . $FILE
	curl -k -u 'apikey:'$API_KEY -X POST -H 'Content-Type:application/json' -d @$FILE $URL/v2/assistants/$ASSISTANT_ID/sessions/$SESSION_ID/message?version=$VERSION | jq .

### Send a second message

	MESSAGE="mardi."
	FILE="message"
	cat > $FILE << EOF
	{
		"input":{
			"text": "$MESSAGE",
	        "options":{
	            "debug": true,
	            "return_context": true
	        }
		}
	}
	EOF
	jq . $FILE
	curl -k -u 'apikey:'$API_KEY -X POST -H 'Content-Type:application/json' -d @$FILE $URL/v2/assistants/$ASSISTANT_ID/sessions/$SESSION_ID/message?version=$VERSION | jq .







