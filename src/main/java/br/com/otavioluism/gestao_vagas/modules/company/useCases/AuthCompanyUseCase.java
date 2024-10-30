package br.com.otavioluism.gestao_vagas.modules.company.useCases;

import br.com.otavioluism.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.otavioluism.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;


@Service
public class AuthCompanyUseCase {

    // anotation responsável por pegar os dados do aplication.properties
    @Value("${secret.token.security}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        // verifique se existe uma empresa com este username, caso não existir estoure uma exceção
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Username/password not found");
                });

        // verifica o match da senha com caracteres normal e a do banco com encriptação
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        // se a senhas não forem iguais
        if(!passwordMatches) {
            throw new AuthenticationException();
        }

        // se as senhas forem iguais -> gerar token JWT
        Algorithm algorithm = Algorithm.HMAC256(secretKey); // SecretId faz com que criamos uma chave de assinatura para conseguir fazer o decode do JWT, sem a assinatura não conseguimos manipular o token
        var token = JWT.create().withIssuer("javavagas") // emissor do token
                .withSubject(company.getId().toString()) // o dono do token
                .sign(algorithm);
        return token;
    }

}
