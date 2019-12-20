#!/bin/bash
# | -> Alt+Shift+L
# { -> Alt+(
# } -> Alt+)
# [ -> Alt+Shift+(
# ] -> Alt+Shift+)

TEMP_FILE="./temp.json"
TEXT="blablabla"
CTX={}
APIKEY=""
URL="https://gateway.watsonplatform.net/assistant/api"
VERSION="2018-09-20"
GET_WKS_METHOD="/v1/workspaces?version="$VERSION
WKS_ID=$(curl -s -u 'apikey:'$APIKEY $URL$GET_WKS_METHOD | jq -r .workspaces[0].workspace_id)
MSG_METHOD="/v1/workspaces/"$WKS_ID"/message?version="$VERSION
MSG_URL=$URL$MSG_METHOD

function syntax(){
  echo "SYNTAX: ${0##*/} --i|--b <file>"
  exit 1
}

function ichat(){
  while [[ ! -z "$TEXT" ]]
    do
      echo -n "       ... "
      read TEXT
      chat "$TEXT" --i
    done
}

function bchat(){

  if [ ! -e "$1" ]; then
      syntax
  fi
  while read LINE
    do
      chat "$LINE"
    done < "$1"
}

function chat(){
  TEXT="$1"
  if [ -z "$2" ]; then
      echo "        "$TEXT
  fi
  jq -n -c --arg text "$TEXT" --argjson ctx "$CTX" '{"input":{"text": $text}, "context":$ctx}' > $TEMP_FILE
  OUTPUT=$(curl -s --cookie cookies.txt --cookie-jar cookies.txt -X POST \
    -u 'apikey:'$APIKEY -H 'Content-Type:application/json' \
    -d @$TEMP_FILE $MSG_URL | \
    jq -c '{text: .output.text | map(.) | join(" "), context: .context}')
  CTX=$(echo $OUTPUT | jq -c .context)
  TEXT=$(echo $OUTPUT | jq -c -r .text)
  echo $TEXT
}

case $1 in
  "--i") ichat ;;
  "--b") bchat $2;;
  *) syntax ;;
esac

rm -f $TEMP_FILE

exit 0
