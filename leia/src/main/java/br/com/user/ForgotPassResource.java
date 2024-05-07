package br.com.user;

import br.com.shared.mail.MailClient;
import br.com.shared.mail.MailDto;
import br.com.shared.mail.MessageMailDto;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Map;
import java.util.Random;

@Path("/v1/forget")
public class ForgotPassResource {

    @RestClient
    private MailClient mailClient;

    @ConfigProperty(name = "courier.key")
    String key;

    @Inject
    private UserRepository userRepository;

    @PATCH
    @Transactional
    public Response forget(@QueryParam("email") String email) {
        int code = new Random().nextInt(9000) + 1000;
        UserEntity user = userRepository.findByEmail(email);

        if (user == null)
            throw new RuntimeException("Usuario não encontrado");

        var toMail = Map.of("email", email);
        var dataVariable = Map.of("recipientName", user.getName(), "otpCode", code);
        var messageMailDto = new MessageMailDto(toMail, dataVariable);
        var mailDto = new MailDto(messageMailDto);
        mailClient.send(mailDto, "Bearer ".concat(key));
        user.setOtp(String.valueOf(code));
        userRepository.persist(user);

        return Response.ok(Map.of("code", code)).build();
    }

    @GET
    public Response validOtp(@QueryParam("email") String email, @QueryParam("otp") String otp) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null)
            throw new RuntimeException("Usuario não encontrado");
        boolean valid = otp.equalsIgnoreCase(user.getOtp());

        return Response.ok(Map.of("valid", valid)).build();
    }
}
