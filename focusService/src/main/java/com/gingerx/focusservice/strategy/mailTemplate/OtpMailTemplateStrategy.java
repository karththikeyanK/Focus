package com.gingerx.focusservice.strategy.mailTemplate;

import org.springframework.stereotype.Service;

@Service
public class OtpMailTemplateStrategy implements MailTemplateStrategy{
    @Override
    public String getOtpMailTemplate(String otpCode) {
        String template = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Focus OTP Verification</title>\n" +
                "    <style>\n" +
                "        /* General Resets */\n" +
                "        body, table, td, a { \n" +
                "            -webkit-text-size-adjust: 100%; \n" +
                "            -ms-text-size-adjust: 100%; \n" +
                "        }\n" +
                "        table, td { \n" +
                "            mso-table-lspace: 0pt; \n" +
                "            mso-table-rspace: 0pt; \n" +
                "        }\n" +
                "        img { \n" +
                "            -ms-interpolation-mode: bicubic; \n" +
                "        }\n" +
                "        /* Responsive Styles */\n" +
                "        @media screen and (max-width: 600px) {\n" +
                "            .email-container {\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "        }\n" +
                "        /* Custom Styles */\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "        }\n" +
                "        .email-container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            padding-bottom: 20px;\n" +
                "        }\n" +
                "        .header img {\n" +
                "            max-width: 150px;\n" +
                "        }\n" +
                "        .content {\n" +
                "            font-size: 16px;\n" +
                "            color: #333333;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "        .otp-code {\n" +
                "            display: inline-block;\n" +
                "            background-color: #349ed4;\n" +
                "            color: #000000;\n" +
                "            font-size: 24px;\n" +
                "            padding: 10px 20px;\n" +
                "            margin: 20px 0;\n" +
                "            border-radius: 4px;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            font-size: 12px;\n" +
                "            color: #aaaaaa;\n" +
                "            margin-top: 30px;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color: #00466a;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"content\">\n" +
                "            <p>Dear User,</p>\n" +
                "            <p>Thank you for choosing <strong>Focus</strong> by <strong>GingerX</strong>. To complete your sign-up process, please use the following One-Time Password (OTP). This OTP is valid for the next 5 minutes:</p>\n" +
                "            <p><a href=\"#\" class=\"otp-code\">{{OTP_CODE}}</a></p>\n" +
                "            <p>If you did not initiate this request, please ignore this email or contact our support team immediately.</p>\n" +
                "            <p>Best regards,<br>The Focus Team</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2025 GingerX. All rights reserved.</p>\n" +
                "            <p><a href=\"https://www.gingerx.com\">www.gingerx.com</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";

        return  template.replace("{{OTP_CODE}}", otpCode);
    }
}
