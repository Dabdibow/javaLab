package ru.itis.javalab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.itis.javalab.dto.UserSignUpForm;
import ru.itis.javalab.models.ConfirmState;
import ru.itis.javalab.models.Role;
import ru.itis.javalab.models.State;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.util.EmailUtil;
import ru.itis.javalab.util.MailGenerator;

import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailGenerator mailGenerator;

    @Autowired
    private EmailUtil emailUtil;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${mail.username}")
    private String from;

    @Value("${mail.message.subject}")
    private String subject;


    @Override
    public void signUp(UserSignUpForm form) {
        if (usersRepository.findFirstByEmail(form.getEmail()) == null) {

            User user = User.builder()
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .email(form.getEmail())
                    .confirmCode(UUID.randomUUID().toString())
                    .confirmState(ConfirmState.NOT_CONFIRMED)
                    .state(State.ACTIVE)
                    .role(Role.USER)
                    .build();

            usersRepository.save(user);

            String confirmMail = mailGenerator.getMailForConfirm(serverUrl, user.getConfirmCode());
            emailUtil.sendMail(user.getEmail(), subject, from, confirmMail);
        }
    }

    @Override
    public void confirmUserWithCode(String code) {
        usersRepository.confirmUserWithCode(code);
        usersRepository.setUserRoleWithCode(code);
        usersRepository.setUserActivityWithCode(code);
    }
}
