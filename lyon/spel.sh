Here is a random entity:
<? entities[(entities.size() * T(java.lang.Math).random()).intValue()].value ?>

<? input.text.toUpperCase().contains('MARDI')? context.jour='mardi': null ?>

<? input.text.toUpperCase().contains('JEUDI')? context.jour='jeudi': null ?>

<? input.text.toUpperCase().find('MARDI|JEUDI') ? context.jour=input.text.toLowerCase().extract('(mardi|jeudi)', 0): null ?>

All of <? entities.size() ?> entities: <? entities ?>. They include "sys-location" (<? entities['sys-location'] ?>), "sys-time" (<? entities['sys-time'] ?>), "sys-date" (<? entities['sys-date'] ?>), "sys-number" (<? entities['sys-number'] ?>), "sys-person" (<? entities['sys-person'] ?>) and more...

// KO
"sys-time" (<? entities['sys-time'].reformatDateTime('h a') ?>)

<? entities['sys-time'] && entities['sys-time'].size()==1 && entities['sys-time'][0].value=='14:00:00'? context.heure='10h': null ?>

<? entities['sys-date'] && entities['sys-date'].size()==1 && context.jours.contains(entities['sys-date'][0].value)? context.jour="blabla": null ?>

<? entities['sys-date'] && entities['sys-date'].size()==1 && context.jours.contains(entities['sys-date'][0].value.reformatDateTime('u'))? context.jour=entities['sys-date'][0].value: null ?>

<? entities['sys-date'] && entities['sys-date'].size()==1 && context.jours.contains(entities['sys-date'][0].value.reformatDateTime('E'))? context.jour=entities['sys-date'][0].value.reformatDateTime('E dd MMMM'): null ?>

<? entities['sys-time'] && entities['sys-time'].size()==1 && context.heures.contains(entities['sys-time'][0].value.reformatDateTime('h'))? context.heure=entities['sys-time'][0].value.reformatDateTime('h') + 'h': null ?>

context.jours.contains('mardi')? context.jour='mardi': null

{
  "context":{
    "jours": ["mardi", "jeudi"],
    "heures": ["10:00:00", "14:00:00"]
  }
}

[
  {
    "jour": "mardi",
    "heures": [
      "10h",
      "11h"
    ]
  },
  {
    "jour": "jeudi",
    "heures": [
      "14h",
      "15h"
    ]
  }
]

Nous recevons les <? $horaires.DATAS.joinToArray("%e.jour%").join(' et ') ?>.

Nous recevons les <? $horaires.DATAS[0].jour ?> à <? $horaires.DATAS[0].heures.join(' et ') ?>.


Nous recevons les <? $horaires.DATAS['heures'].joinToArray(' et ') ?>.


API Details
Assistant Details
Assistant Name:
assistant0

Assistant ID:
cb9acef7-08c9-4069-85ea-dd52f5ed1bcd

Assistant URL:
https://gateway-fra.watsonplatform.net/assistant/api/v2/assistants/cb9acef7-08c9-4069-85ea-dd52f5ed1bcd/sessions

Service Credentials
Credentials Name:
Auto-generated service credentials

Api Key:
DVeTU-kw-nuSPkFH_p0J790aqgeyExqLFEqeuGA81TVB

Credentials Name:
Auto-generated service credentials

Api Key:
yj3VhX_MeJU0hMvEVX7kU5lk4q6M796JFfdgj1A2wBDA

API_KEY="DVeTU-kw-nuSPkFH_p0J790aqgeyExqLFEqeuGA81TVB"

VERSION="2019-02-28"

SESSION_ID=$(curl -X POST -u 'apikey:DVeTU-kw-nuSPkFH_p0J790aqgeyExqLFEqeuGA81TVB' https://gateway-fra.watsonplatform.net/assistant/api/v2/assistants/cb9acef7-08c9-4069-85ea-dd52f5ed1bcd/sessions?version=$VERSION | jq -r .session_id)

URL="https://api.eu-de.assistant.watson.cloud.ibm.com"

SKILL="skill1"

WORKSPACE_ID=$(curl -u 'apikey:'$API_KEY $URL/v1/workspaces?version=2019-02-28 | jq -r '.workspaces[] | select(.name=="'$SKILL'") | .workspace_id')

curl -X POST -u 'apikey:'$API_KEY -H 'Content-Type:application/json' -d '{"input": {"text": "Je veux prendre rendez-vous"}}' $URL/v1/workspaces/$WORKSPACE_ID/message?version=$VERSION


Nous recevons les <? $horaires.DATAS.joinToArray("%e.jour%").join(' et ') ?>. <? context.horaires.DATAS ?>