package com.fatec.apiestacionamento.service;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.communication.email.models.EmailSendStatus;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.polling.LongRunningOperationStatus;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AzureMailService {

    // Azure Communication Services credentials and endpoint
    private static final String ENDPOINT = "https://<resource-name>.communication.azure.com";
    private static final String ACCESS_KEY = "<access-key>";

    public void sendVerificationEmail(String email, String verificationCode) {
        // Create an instance of EmailClient using Azure Communication Services
        EmailClient emailClient = new EmailClientBuilder()
                .endpoint(ENDPOINT)
                .credential(new AzureKeyCredential(ACCESS_KEY))
                .buildClient();

        // Construct the email message
        EmailMessage message = new EmailMessage()
                .setSenderAddress("<donotreply@yourdomain.com>")
                .setToRecipients(email)
                .setSubject("Email Verification Code")
                .setBodyPlainText("Your verification code is: " + verificationCode);

        try {
            SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message, null);

            PollResponse<EmailSendResult> pollResponse = null;

            Duration timeElapsed = Duration.ofSeconds(0);
            Duration POLLER_WAIT_TIME = Duration.ofSeconds(10);

            while (pollResponse == null
                    || pollResponse.getStatus() == LongRunningOperationStatus.NOT_STARTED
                    || pollResponse.getStatus() == LongRunningOperationStatus.IN_PROGRESS)
            {
                pollResponse = poller.poll();
                System.out.println("Email send poller status: " + pollResponse.getStatus());

                Thread.sleep(POLLER_WAIT_TIME.toMillis());
                timeElapsed = timeElapsed.plus(POLLER_WAIT_TIME);

                if (timeElapsed.compareTo(POLLER_WAIT_TIME.multipliedBy(18)) >= 0)
                {
                    throw new RuntimeException("Polling timed out.");
                }
            }

            if (poller.getFinalResult().getStatus() == EmailSendStatus.SUCCEEDED)
            {
                System.out.printf("Successfully sent the email (operation id: %s)", poller.getFinalResult().getId());
            }
            else
            {
                throw new RuntimeException(poller.getFinalResult().getError().getMessage());
            }
        } catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}
