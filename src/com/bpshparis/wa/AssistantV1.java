package com.bpshparis.wa;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
// make sure to use the Assistant v1 import!
import com.ibm.watson.assistant.v1.Assistant;
import com.ibm.watson.assistant.v1.model.Context;
import com.ibm.watson.assistant.v1.model.MessageInput;
import com.ibm.watson.assistant.v1.model.MessageOptions;
import com.ibm.watson.assistant.v1.model.MessageResponse;

public class AssistantV1 {

	public static void main(String args[]){
        
        String workspaceId = "55cc67cf-1bc4-4f3e-a70b-9bc3cd091447";
        String iam_api_key = "DVeTU-kw-nuSPkFH_p0J790aqgeyExqLFEqeuGA81TVB";
        String url = "https://api.eu-de.assistant.watson.cloud.ibm.com";

        Authenticator authenticator = new IamAuthenticator(iam_api_key);
        Assistant service = new Assistant("2019-02-28", authenticator);
        service.setServiceUrl(url);

        Context context = null;
        MessageInput input = new MessageInput();
        input.setText("Je veux un rdv.");
        MessageOptions options = new MessageOptions.Builder(workspaceId)
        .input(input)
        .context(context)
        .build();
        MessageResponse response = service.message(options).execute().getResult();
        context = response.getContext();
        System.out.println(response);	
        
        input.setText("mardi");
        options = new MessageOptions.Builder(workspaceId)
        .input(input)
        .context(context)
        .build();

        response = service.message(options).execute().getResult();
        context = response.getContext();
        System.out.println(response);        
    }
}